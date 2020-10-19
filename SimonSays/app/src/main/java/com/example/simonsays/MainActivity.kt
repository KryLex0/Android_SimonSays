package com.example.simonsays

import android.R.attr.button
import android.R.attr.delay
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    var temp: Int = 0
    val tabColor = arrayOf("rouge", "blue", "vert", "jaune")
    var gagne = true
    private lateinit var val_score: TextView
    private lateinit var tv_color: TextView

    private lateinit var btn_1: Button
    private lateinit var btn_2: Button
    private lateinit var btn_3: Button
    private lateinit var btn_4: Button
    private lateinit var btn_start: Button
    private var tab: MutableList<Button> = mutableListOf()//tab = arrayOf<Button>()

    private var listColorParty: MutableList<Button> = mutableListOf() //= arrayOf<Button>()
    private var listColorPlayer: MutableList<String> = mutableListOf() //= arrayOf<String>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val_score = findViewById(R.id.tv_val_score)
        tv_color = findViewById(R.id.tv_color)
        btn_1 = findViewById(R.id.btn_1)
        btn_2 = findViewById(R.id.btn_2)
        btn_3 = findViewById(R.id.btn_3)
        btn_4 = findViewById(R.id.btn_4)
        btn_start = findViewById(R.id.btn_start)

        tab.addAll(listOf(btn_1, btn_2, btn_3, btn_4))

        btn_start.setOnClickListener{
            this.nextLevel()
            btn_start.setVisibility(View.INVISIBLE)
            btn_1.setVisibility(View.VISIBLE)
            btn_2.setVisibility(View.VISIBLE)
            btn_3.setVisibility(View.VISIBLE)
            btn_4.setVisibility(View.VISIBLE)
        }

        btn_1.setOnClickListener{
            listColorPlayer.add(R.id.btn_1.toString())  //ajout du bouton clique par le joueur dans une liste
            if(listColorParty.size == listColorPlayer.size) {   //compare le nombre de boutons clique par le joueur au nombre de bouton a cliquer dans la partie actuelle
                this.verifCouleur() //appel de la fonction qui compare les boutons du joueurs avec les boutons generes
            }
        }

        btn_2.setOnClickListener{
            listColorPlayer.add(R.id.btn_2.toString())
            if(listColorParty.size == listColorPlayer.size) {
                this.verifCouleur()
            }
        }

        btn_3.setOnClickListener{
            listColorPlayer.add(R.id.btn_3.toString())
            if(listColorParty.size == listColorPlayer.size) {
                this.verifCouleur()
            }
        }

        btn_4.setOnClickListener{
            listColorPlayer.add(R.id.btn_4.toString())
            if(listColorParty.size == listColorPlayer.size) {
                this.verifCouleur()
            }
        }

    }

    private fun animButton(btn: Button){
        val anim: Animation = AlphaAnimation(1.0f, 0.0f)
        anim.duration = 300
        anim.startOffset = 200
        anim.repeatMode = Animation.REVERSE

        //btn.forEach{
        btn.startAnimation(anim)
        //}

    }

    fun nextLevel(){
        temp = (0..3).random()
        tv_color.text = tabColor[temp]
        //Log.d("test", tab[temp].id.toString())
        listColorParty.add(tab[temp])
        if(this.gagne) {
            Handler().postDelayed({
                listColorParty.forEach() {
                    animButton(it)
                }
            }, 1000)
        }
    }


    @SuppressLint("SetTextI18n")
    private fun verifCouleur(){//listColorPlayer : MutableList<String>){
        //for(item in listColorParty) {
        var i = 0
//        Log.d("test", listColorParty.size.toString())
//        Log.d("test", listColorParty[0].id.toString())
//        Log.d("test", listColorPlayer.size.toString())
//        Log.d("test", listColorPlayer[0])

        while(i<listColorParty.size){

            if(listColorParty[i].id.toString() == listColorPlayer[i]){//if(it.id.toString() == idBtnClick) {//if(numBtn == idBtnClick){
                Log.d("test", "gagné")
                gagne = true
            } else {
                Log.d("test", "perdu")
                gagne = false
                break
            }
            i += 1
        }
        if(gagne){
            Toast.makeText(this, "Gagné", Toast.LENGTH_SHORT).show()
            val_score.text = "${val_score.text.toString().toInt() + 1}"
            listColorPlayer.clear()
        }else{
            Toast.makeText(this, "Perdu", Toast.LENGTH_SHORT).show()
            this.partieFinie()
        }
        this.nextLevel()
    }

    private fun partieFinie(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Partie terminé")
        builder.setMessage("Votre score est de " + val_score.text)

        builder.setPositiveButton(
            "Ok"
        ) { _, _ ->
            val intent = intent
            finish()
            startActivity(intent)
            //Toast.makeText(this@MainActivity, "Alert dialog closed.", Toast.LENGTH_LONG).show()
        }
        builder.show()
    }

/*
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putInt("score", this.getScore())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        savedInstanceState.getInt("score")
    }
*/



}