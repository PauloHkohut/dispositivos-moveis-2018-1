package br.grupointegrado.tads.usandointents

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*btn_enviar.setOnClickListener({
            val mensagem = et_mensagem.text.toString()
            Toast.makeText(this, mensagem, Toast.LENGTH_LONG).show()
        })*/

        btn_enviar.setOnClickListener({
            val activityDestino = SegundaActivity::class.java
            val context = this

            val intent = Intent(context, activityDestino)

            startActivity(intent)
        })
    }

}
