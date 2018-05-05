package br.grupointegrado.tads.ciclodevida

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    private val CONTEUDO_TEXTVIEW: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState != null){
            if (savedInstanceState.containsKey(CONTEUDO_TEXTVIEW)){
              val conteudoTextView = savedInstanceState.getString(CONTEUDO_TEXTVIEW)
                tv_mensagem_log.text = conteudoTextView
            }
        }

        //Log.d("Ciclo de vida", "onCreate")
        imprimir("onCreate")
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        imprimir("onSaveInstanceState")
        val conteudoTextView = tv_mensagem_log.text.toString()
        outState?.putString(CONTEUDO_TEXTVIEW, conteudoTextView)
    }

    override fun onStart() {
        super.onStart()
        //Log.d("Ciclo de vida", "onStart")

        imprimir("onStart")
    }

    override fun onResume() {
        super.onResume()
        //Log.d("Ciclo de vida", "onResume")

        imprimir("onResume")
    }

    override fun onPause() {
        super.onPause()
        //Log.d("Ciclo de vida", "onPause")
        imprimir("onPause")
    }

    override fun onStop() {
        super.onStop()
        //Log.d("Ciclo de vida", "onStop")
        imprimir("onStop")
    }

    override fun onRestart() {
        super.onRestart()
        //Log.d("Ciclo de vida", "onRestart")
        imprimir("onRestart")
    }

    override fun onDestroy() {
        super.onDestroy()
        //Log.d("Ciclo de vida", "onDestroy")
        imprimir("onDestroy")
    }


    fun imprimir(mensagem: String){
        Log.d("Ciclo de vida", mensagem)
        tv_mensagem_log.append("$mensagem \n")
    }
}
