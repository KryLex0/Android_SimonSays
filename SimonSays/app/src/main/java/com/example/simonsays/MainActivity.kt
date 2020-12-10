package com.example.simonsays


import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

import kotlinx.android.synthetic.main.activity_main_test.*
import kotlinx.android.synthetic.main.data_party.*


class MainActivity : AppCompatActivity() {
    private var temp: Int = 0
    private val tabColorName = arrayOf("RED", "BLUE", "GREEN", "YELLOW", "BROWN", "PURPLE")
    private val tabColorHex = arrayOf("#FF0000", "#4169E1", "#008000", "#FFFF00", "#A73A03", "#D006BE")
    private val colorR = arrayOf(R.color.red, R.color.blue, R.color.green, R.color.yellow, R.color.brown, R.color.purple)
    private var listeBtnNextColor: MutableList<Button> = mutableListOf()
    private var gagne = true
    private var btnColor: MutableList<Button> = mutableListOf()

    private lateinit var difficulty: String
    private var nbBtnOpt: Int = 1
    private var animBtn = true

    private var tab: MutableList<Button> = mutableListOf()//tab = arrayOf<Button>()

    private var listColorParty: MutableList<Button> = mutableListOf() //= arrayOf<Button>()
    private var listColorPlayer: MutableList<String> = mutableListOf() //= arrayOf<String>()


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_test)

        this.listeBtnNextColor.addAll(arrayOf(btn_color1, btn_color2, btn_color3))

        this.nbBtnOpt = this.intent.getIntExtra("nb_btn_opt", 1)
        this.difficulty = this.intent.getStringExtra("difficulty")
        this.animBtn = this.intent.getBooleanExtra("animSwitch", this.animBtn)


        btnDataParty()
        setBtnDifficulte()

        nextLevel()

    }



    private fun setBtnDifficulte() {
        if(this.difficulty == "Facile"){
            this.tab.addAll(listOf(btn_1_T, btn_2_T))
        }
        else if(this.difficulty == "Normal"){
            this.tab.addAll(listOf(btn_1_T, btn_2_T, btn_3_T, btn_4_T))
        }
        else if(this.difficulty == "Difficile"){
            this.tab.addAll(listOf(btn_1_T, btn_2_T, btn_3_T, btn_4_T, btn_5_T, btn_6_T))
        }

        tab.forEach {
            it.visibility = View.GONE
        }

        tab.forEach {
            it.visibility = View.VISIBLE
            val btn = it.id
            Log.d("123321", it.toString())
            it.setOnClickListener {
                listColorPlayer.add(btn.toString())  //ajout du bouton clique par le joueur dans une liste
                if(listColorParty.size == listColorPlayer.size) {   //compare le nombre de boutons clique par le joueur au nombre de bouton a cliquer dans la partie actuelle
                    this.verifCouleur() //appel de la fonction qui compare les boutons du joueurs avec les boutons generes
                }
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
                if(index == btn.size-1){
                    tab.forEach {
                        it.isClickable = true
                    }

                }
            }
        })

    }




    private fun btnDataParty(){
        for(i in 1..nbBtnOpt) {
            this.listeBtnNextColor[i-1].visibility = View.VISIBLE
        }

    }


    private fun nextLevel() {
        for(j in 1..this.nbBtnOpt){
            var i=0
            if (this.difficulty == "Facile") {
                i = 1
            } else if (this.difficulty == "Normal") {
                i = 3
            } else if (this.difficulty == "Difficile") {
                i = 5
            }

            temp = (0..i).random()
            listColorParty.add(tab[temp])

            this.listeBtnNextColor[j-1].text = tabColorName[temp]
            this.listeBtnNextColor[j-1].setBackgroundColor(Color.parseColor(tabColorHex[temp]))
        }

        if(this.gagne && this.animBtn) {
            tab.forEach {
                it.isClickable = false
            }
            animButton(listColorParty, 0)
        }

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
            var scoreTemp = 0
            if(this.nbBtnOpt == 1){scoreTemp = 1}else if(this.nbBtnOpt == 2){scoreTemp = 2} else if(this.nbBtnOpt == 3){scoreTemp = 3}
            tv_val_score.text = "${tv_val_score.text.toString().toInt() + scoreTemp}"
            listColorPlayer.clear()
        }else{
            Toast.makeText(this, "Perdu", Toast.LENGTH_SHORT).show()
            this.popupNamePlayer()
        }
        this.nextLevel()
    }



/*
    private fun partieFinie(){
        val builder = AlertDialog.Builder(this)
        //val viewInflated: View = LayoutInflater.from(getContext()).inflate(R.layout.popup_name, getView() as ViewGroup?, false)
        //AppDatabase.get(application).playerDao().insertPlayer(Player("Adam", 100))

        builder.setTitle("Partie terminé")
        builder.setMessage("Votre score est de " + val_score.text)
        builder.setCancelable(false)
        //InsertDataPlayer(application).execute()

        builder.setPositiveButton("Ok") { _, _ ->
            finish()
            val intent = Intent(this, ActivityStart::class.java)
            startActivity(intent)
        }
        builder.show()
    }
*/

    private fun popupNamePlayer(){
        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val popupView: View = inflater.inflate(R.layout.popup_name, null)

        val width = LinearLayout.LayoutParams.WRAP_CONTENT
        val height = LinearLayout.LayoutParams.WRAP_CONTENT

        val popupWindow = PopupWindow(popupView, width, height, true)
        popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0)

        popupView.findViewById<Button>(R.id.but1).setOnClickListener {
            saveDataPlayer(popupView)
        }

    }

    private fun saveDataPlayer(popupView: View) {
        val scoreP = (tv_val_score.text.toString()).toLong()
        val nomJoueur = popupView.findViewById<EditText>(R.id.name_player).text.toString()

        Log.d("test123", nomJoueur)

        AppDatabase.get(application).playerDao().insertPlayer(Player(nomJoueur, scoreP))
        Log.d("test123", "$nomJoueur a bien ete ajoute avec un score de $scoreP")

        val intent = Intent(this, ActivityStart::class.java)
        startActivity(intent)
    }


}