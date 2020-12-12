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
import kotlinx.android.synthetic.main.activity_options.*
import kotlinx.android.synthetic.main.activity_start.*
import java.io.IOException


class ActivityStart : AppCompatActivity() {
    private var nbBtnOpt = 1
    private var difficulty: String = "Normal"
    private var isSwitchChecked = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)



        btn_start.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("difficulty", this.difficulty)
            intent.putExtra("nb_btn_opt", this.nbBtnOpt)
            intent.putExtra("animSwitch", this.isSwitchChecked)
            startActivity(intent)
        }

        btn_high_score.setOnClickListener{
            val intent = Intent(this, ScoreBoard::class.java)
            startActivity(intent)
        }

        btn_options.setOnClickListener{
            val intent = Intent(this, ActivityOptions::class.java)
            intent.putExtra("difficulty", this.difficulty)
            intent.putExtra("nb_btn_opt", this.nbBtnOpt)
            intent.putExtra("animSwitch", this.isSwitchChecked)
            startActivityForResult(intent, 111)
        }



    }

    @Override
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 111 && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                this.difficulty = data.getStringExtra("difficulty")
                this.nbBtnOpt = data.getIntExtra("nb_btn_opt", 1)
                this.isSwitchChecked = data.getBooleanExtra("animSwitch", this.isSwitchChecked)
            }
        }

        super.onActivityResult(requestCode, resultCode, data)
    }


}
