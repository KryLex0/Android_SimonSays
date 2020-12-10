package com.example.simonsays


import android.app.Application
import android.graphics.drawable.Drawable
import android.graphics.drawable.Icon
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.setPadding
import kotlinx.android.synthetic.main.activity_score_board.*
import kotlinx.android.synthetic.main.popup_name.view.*


class ScoreBoard : AppCompatActivity() {
    //private lateinit var btn_remove_all: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score_board)
        displayPlayerHighscore()

        //btn_remove_all = findViewById(R.id.btn_remove_all)
        btn_remove_all.setOnClickListener {
            removeAll()
        }

    }

    private fun removeAll(){
        AppDatabase.get(application).playerDao().deleteAll()
        finish()
        startActivity(intent)
    }

    private fun displayPlayerHighscore(){
        var rang = 1
        //val parent_linear = findViewById<LinearLayout>(R.id.parent_linear)
        AppDatabase.get(application).playerDao().getTenLast().forEach { it ->

            val parent = LinearLayout(this)

            parent.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            parent.orientation = LinearLayout.HORIZONTAL


            val tv1 = TextView(this)
            tv1.text = rang.toString()
            tv1.layoutParams =
                LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f)
            tv1.textAlignment = View.TEXT_ALIGNMENT_CENTER
            tv1.setBackgroundResource(R.drawable.row_borders)
            tv1.setPadding(15)
            parent.addView(tv1)

            val tv2 = TextView(this)
            tv2.text = it.name
            tv2.layoutParams =
                LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f)
            tv2.textAlignment = View.TEXT_ALIGNMENT_CENTER
            tv2.setBackgroundResource(R.drawable.row_borders)
            tv2.setPadding(15)
            parent.addView(tv2)

            val tv3 = TextView(this)
            tv3.text = it.score.toString()
            tv3.layoutParams =
                LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f)
            tv3.textAlignment = View.TEXT_ALIGNMENT_CENTER
            tv3.setBackgroundResource(R.drawable.row_borders)
            tv3.setPadding(15)
            parent.addView(tv3)

            val tv4 = TextView(this)
            tv4.text = "X"
            tv4.layoutParams =
                LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f)
            tv4.textAlignment = View.TEXT_ALIGNMENT_CENTER
            tv4.setBackgroundResource(R.drawable.row_borders)
            tv4.setPadding(15)

            val playerId = it.id
            tv4.setOnClickListener {
                AppDatabase.get(application).playerDao().deletePlayerFromId(playerId)
                Toast.makeText(this, "Le score selectionné a bien été supprimé", Toast.LENGTH_SHORT).show()
                finish()
                startActivity(intent)
            }

            parent.addView(tv4)


            parent_linear.addView(parent)
            rang += 1
        }
    }

}

private operator fun Drawable.invoke(s: String) {

}
