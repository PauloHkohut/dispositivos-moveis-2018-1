package br.grupointegrado.tads.provaprimeirobimestre

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


    }

    fun enviarMensagem(view: View){
        val base = findViewById<EditText>(R.id.edit_base)
        val altura = findViewById<EditText>(R.id.edit_altura)
        val profundidade = findViewById<EditText>(R.id.edit_profundidade)

        Toast.makeText(this, base.text, Toast.LENGTH_LONG).show()
        Toast.makeText(this, altura.text, Toast.LENGTH_LONG).show()
        Toast.makeText(this, profundidade.text, Toast.LENGTH_LONG).show()
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.action_calcular) {
            calcular()
        }
        return super.onOptionsItemSelected(item)
    }

    fun exibirMensagemErro() {
        calc_mensagem_erro.visibility = View.VISIBLE
    }

    fun calcular() {
        val base = edit_base.text.toString().toInt()
        val altura = edit_altura.text.toString().toInt()
        val profundidade = edit_profundidade.text.toString().toInt()
        

            val calc_area = base * altura
            val calc_volume = base * altura * profundidade

            result_area.append("Área: $calc_area")
            result_volume.append("Volume: $calc_volume")



        }


}
