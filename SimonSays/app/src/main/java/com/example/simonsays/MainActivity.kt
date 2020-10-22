package com.example.simonsays

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.postDelayed


class MainActivity : AppCompatActivity() {
    var temp: Int = 0
    val tabColorName = arrayOf("RED", "BLUE", "GREEN", "YELLOW")
    val tabColor = arrayOf("#FF0000", "#4169E1", "#008000", "#FFFF00")
    var gagne = true
    private lateinit var val_score: TextView
    private lateinit var tv_color_name: TextView
    private lateinit var tv_score : TextView
    private lateinit var tv_val_score : TextView

    private lateinit var btn_1: Button
    private lateinit var btn_2: Button
    private lateinit var btn_3: Button
    private lateinit var btn_4: Button
    private lateinit var btn_start: Button
    private lateinit var btn_color: Button

    private var tab: MutableList<Button> = mutableListOf()//tab = arrayOf<Button>()

    private var listColorParty: MutableList<Button> = mutableListOf() //= arrayOf<Button>()
    private var listColorPlayer: MutableList<String> = mutableListOf() //= arrayOf<String>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val_score = findViewById(R.id.tv_val_score)
        tv_color_name = findViewById(R.id.tv_color_name)
        btn_1 = findViewById(R.id.btn_1)
        btn_2 = findViewById(R.id.btn_2)
        btn_3 = findViewById(R.id.btn_3)
        btn_4 = findViewById(R.id.btn_4)
        btn_start = findViewById(R.id.btn_start)
        btn_color = findViewById(R.id.btn_color)

        tv_score = findViewById(R.id.tv_score)
        tv_val_score = findViewById(R.id.tv_val_score)

        tab.addAll(listOf(btn_1, btn_2, btn_3, btn_4))

        btn_start.setOnClickListener{
            this.nextLevel()
            btn_start.setVisibility(View.INVISIBLE)
            btn_1.setVisibility(View.VISIBLE)
            btn_2.setVisibility(View.VISIBLE)
            btn_3.setVisibility(View.VISIBLE)
            btn_4.setVisibility(View.VISIBLE)
            tv_color_name.setVisibility(View.VISIBLE)
            btn_color.setVisibility(View.VISIBLE)
            tv_score.setVisibility(View.VISIBLE)
            tv_val_score.setVisibility(View.VISIBLE)
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

    private fun animButton(btn: MutableList<Button>, index: Int){
        val anim: Animation = AlphaAnimation(1.0f, 0.2f)
        anim.duration = 250
        anim.startOffset = 500
        anim.interpolator = LinearInterpolator()
        anim.repeatMode = Animation.REVERSE

        btn[index].startAnimation(anim)

            anim.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationRepeat(animation: Animation?) {}
                override fun onAnimationStart(animation: Animation?) {}

                override fun onAnimationEnd(animation: Animation?) {
                    val index1 = index + 1
                    if(index < btn.size - 1) {
                        animButton(btn, index1)
                    }
                }
            })
        }

    private fun nextLevel(){
        temp = (0..3).random()
        val index = 0
        //tv_color.text = tabColor[temp]
        btn_color.text = tabColorName[temp]
        btn_color.setBackgroundColor(Color.parseColor(tabColor[temp]))
        listColorParty.add(tab[temp])

        tab.forEach {
            it.isClickable = false
        }

        if(this.gagne) {
            animButton(listColorParty, index)
        }

        Handler().postDelayed({
            tab.forEach {
                it.isClickable = true
            }
        }, (500 * (listColorParty.size+1)).toLong())
    }


    @SuppressLint("SetTextI18n")
    private fun verifCouleur(){
        var i = 0
        while(i<listColorParty.size){

            if(listColorParty[i].id.toString() == listColorPlayer[i]){
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
        builder.setCancelable(false)

        builder.setPositiveButton(
            "Ok"
        ) { _, _ ->
            val intent = intent
            finish()
            startActivity(intent)
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