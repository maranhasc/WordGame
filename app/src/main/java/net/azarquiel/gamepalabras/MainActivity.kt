package net.azarquiel.gamepalabras

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random


class MainActivity : AppCompatActivity() {
    private lateinit var llletras: LinearLayout
    private lateinit var linearlup: LinearLayout
    private lateinit var linearldown: LinearLayout
    private lateinit var palabras: Array<String>
    private lateinit var random: Random
    private lateinit var palabraAzar: String
    private var palabraGame = Array<LetraPos>(5){ LetraPos() }
    private var aciertos = 0




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        llletras = findViewById<LinearLayout>(R.id.linearLayout)
        linearlup = findViewById<LinearLayout>(R.id.linearlup)
        linearldown = findViewById<LinearLayout>(R.id.linearldown)
        random = Random(System.currentTimeMillis())
        palabras = resources.getStringArray(R.array.palabras)
        ponListener()
        newGame()

    }

    private fun ponListener() {
        for (i in 0 until linearlup.childCount){
            val ivup = linearlup.getChildAt(i) as ImageView
            ivup.setOnClickListener{v -> onclickUp(v)}
        }
        for (i in 0 until linearldown.childCount){
            val ivdown = linearldown.getChildAt(i) as ImageView
            ivdown.setOnClickListener{v -> onclickUp(v)}
        }
    }

    private fun onclickUp(v: View) {
        val i = (v.tag as String).toInt()
        var posNext = (palabraGame[i].pos)+1
        if (posNext==5) posNext = 0
        var letraNext = palabraAzar[posNext]
        var iv = llletras.getChildAt(i) as ImageView

        val color = if(letraNext==palabraAzar[i]) "v" else "r"
        val id = resources.getIdentifier("${letraNext.lowercase()}$color", "drawable", packageName)
        iv.setImageResource(id)

        palabraGame[i].letra = letraNext
        palabraGame[i].pos = posNext

        if(color == "v"){
            aciertos ++
            checkGamesOver()
        }
        
    }

    private fun newGame() {
        palabras.shuffle()
        palabraAzar = palabras[0]
       // Log.d("Carmen", palabraAzar)
        inventaPalabra()
        showPalabra()


    }

    private fun showPalabra() {
        for (i in 0 until 5){
            val ivletra = llletras.getChildAt(i) as ImageView
            val letra = palabraGame[i].letra
            val color = if(letra==palabraAzar[i]) "v" else "r"
            val id = resources.getIdentifier("${letra.lowercase()}$color", "drawable", packageName)
            ivletra.setImageResource(id)
        }
    }

    private fun inventaPalabra() {
        for (i in 0 until 5){
            val n = (0 until 5).random(random)
            palabraGame[i].letra = palabraAzar[n]
            palabraGame[i].pos = n
        }

    }

    private fun checkGamesOver() {
        if (aciertos == 5){
            AlertDialog.Builder(this)
                .setCancelable(false)
                .setTitle("Felicidades.")
                .setMessage("Lo conseguistes ")
                .setCancelable(false)
                .setPositiveButton("New Game") { dialog, which ->
                    newGame()
                }
                .setNegativeButton("Fin") { dialog, which ->
                    finish()
                }
                .show()


        }
    }


}