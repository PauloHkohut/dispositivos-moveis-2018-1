package br.grupointegrado.tads.ciclodevida

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d("Ciclo de vida", "onCreate")
    }

    override fun onStart() {
        super.onStart()
        Log.d("Ciclo de vida", "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("Ciclo de vida", "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("Ciclo de vida", "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("Ciclo de vida", "onStop")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("Ciclo de vida", "onRestart")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("Ciclo de vida", "onDestroy")
    }
}
