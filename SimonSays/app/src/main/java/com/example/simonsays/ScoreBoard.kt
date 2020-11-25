package com.example.simonsays


import android.app.Application
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class ActivityStart : AppCompatActivity() {

    private lateinit var btn_start: Button
    private lateinit var table_score: TableLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity1)

        btn_start = findViewById(R.id.btn_start)

        btn_start.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        table_score = findViewById(R.id.table_score)
        DisplayDataPlayers(application, table_score).execute()
    }

    fun displayData(application: Application, table_score: TableLayout){
        val x = AppDatabase.get(application).playerDao().getAll().forEach{
            val tr1 = TableRow(application)
            tr1.layoutParams = TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT)
            val textview = TextView(application)
            textview.text = "${it.name}"
            tr1.addView(textview)
            table_score.addView(tr1, TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT))
        }
    }

    class DisplayDataPlayers(val application: Application, val table_score:TableLayout) : AsyncTask<Void,Void,Void>(){
        override fun onPreExecute() {
            super.onPreExecute()

        }

        override fun doInBackground(vararg params: Void?): Void? {
            val x = AppDatabase.get(application).playerDao().getAll().forEach{
                val tr1 = TableRow(application)
                tr1.layoutParams = TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT)
                val textview = TextView(application)
                textview.text = "${it.name}"
                tr1.addView(textview)
                table_score.addView(tr1, TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT))
            }
            return null
        }
    }

  /*
    class InsertDataPlayer(val application: Application) : AsyncTask<Void,Void,Void>(){
        override fun doInBackground(vararg params: Void?): Void? {
            AppDatabase.get(application).playerDao().insertPlayer(Player("Adam1", 10))
            AppDatabase.get(application).playerDao().insertPlayer(Player("Adam2", 0))
            AppDatabase.get(application).playerDao().insertPlayer(Player("Adam3", 100))
            AppDatabase.get(application).playerDao().insertPlayer(Player("Adam4", 1))
            val x = AppDatabase.get(application).playerDao().getAll().forEach{
                Log.d("test123", "id: ${it.id} et name: ${it.name} et score: ${it.score}")
            }

            return null
        }
    }
*/
}