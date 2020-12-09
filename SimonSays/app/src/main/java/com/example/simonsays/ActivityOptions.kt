package com.example.simonsays


import android.app.Activity
import android.app.Application
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.os.AsyncTask
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_start.*
import java.io.IOException


class ActivityStart : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)



        btn_start.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        btn_high_score.setOnClickListener{
            val intent = Intent(this, ScoreBoard::class.java)
            startActivity(intent)
        }



    }

    @Override
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 222 && resultCode == Activity.RESULT_OK) {
            val difficulte = data!!.getIntExtra("taille", paintView.getStrokeWidth())
            paintView.setStrokeWidth(xSize)
            val xColor = data.getIntExtra("couleur", paintView.getColor())
            paintView.setColor(xColor)
        }

        super.onActivityResult(requestCode, resultCode, data)
    }


}