package com.example.simonsays


import android.app.Application
import android.graphics.drawable.Drawable
import android.graphics.drawable.Icon
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.setPadding
import kotlinx.android.synthetic.main.activity_score_board.*
import kotlinx.android.synthetic.main.data_party.*
import kotlinx.android.synthetic.main.popup_name.view.*


class ScoreBoard : AppCompatActivity() {
    //private lateinit var btn_remove_all: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score_board)
        displayPlayerHighscore()

        btn_remove_all.setOnClickListener {
            removeAll()
        }

    }

    private fun removeAll(){    //remove tout le contenue de la bdd
        AppDatabase.get(application).playerDao().deleteAll()
        finish()
        startActivity(intent)
    }

    private fun displayPlayerHighscore(){   //affiche les 10 derniers meilleurs score (ajout dynamiquement)
        var rang = 1
        AppDatabase.get(application).playerDao().getTenLast().forEach { it ->

            //creation d'un linear layout qui contiendra les infos
            val parent = LinearLayout(this)
            parent.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            parent.orientation = LinearLayout.HORIZONTAL

            //textview qui contient le rang
            val tv1 = TextView(this)
            tv1.text = rang.toString()
            tv1.layoutParams =
                LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f)
            tv1.textAlignment = View.TEXT_ALIGNMENT_CENTER
            tv1.setBackgroundResource(R.drawable.row_borders)
            tv1.setPadding(15)
            parent.addView(tv1)

            //textview qui contient le nom
            val tv2 = TextView(this)
            tv2.text = it.name
            tv2.layoutParams =
                LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f)
            tv2.textAlignment = View.TEXT_ALIGNMENT_CENTER
            tv2.setBackgroundResource(R.drawable.row_borders)
            tv2.setPadding(15)
            parent.addView(tv2)

            //textview qui contient le score
            val tv3 = TextView(this)
            tv3.text = it.score.toString()
            tv3.layoutParams =
                LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f)
            tv3.textAlignment = View.TEXT_ALIGNMENT_CENTER
            tv3.setBackgroundResource(R.drawable.row_borders)
            tv3.setPadding(15)
            parent.addView(tv3)

            //textview qui permet de supprimer la ligne de la bdd
            val tv4 = TextView(this)
            tv4.text = "X"
            tv4.layoutParams =
                LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f)
            tv4.textAlignment = View.TEXT_ALIGNMENT_CENTER
            tv4.setBackgroundResource(R.drawable.row_borders)
            tv4.setPadding(15)

            val playerId = it.id
            tv4.setOnClickListener {
                val builder = AlertDialog.Builder(this)

                builder.setTitle("Supprimer?")
                builder.setMessage("Rang: " + tv1.text + "\nNom: " + tv2.text + "\nScore: " + tv3.text)
                builder.setCancelable(false)

                builder.setPositiveButton("Oui") { _, _ ->
                    AppDatabase.get(application).playerDao().deletePlayerFromId(playerId)
                    Toast.makeText(this, "Le score selectionné a bien été supprimé", Toast.LENGTH_SHORT).show()
                    finish()
                    startActivity(intent)
                }
                builder.setNegativeButton("Non") { _, _ -> }
                builder.show()
            }

            parent.addView(tv4)


            parent_linear.addView(parent)
            rang += 1
        }
    }

}