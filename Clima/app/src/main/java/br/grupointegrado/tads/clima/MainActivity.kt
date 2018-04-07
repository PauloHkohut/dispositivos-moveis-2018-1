package br.grupointegrado.tads.clima

import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.format.DateFormat
import android.view.Menu
import android.view.MenuItem
import android.view.View
import br.grupointegrado.tads.clima.util.NetworkUtils
import br.grupointegrado.tads.clima.dados.ClimaPreferencias
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        carregarDadosDoClima()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.clima, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.acao_atualizar) {
            tv_dados_clima.text = ""
            carregarDadosDoClima()

            return true
        }
        return super.onOptionsItemSelected(item)
    }

    fun exibirResultado() {
        tv_dados_clima.visibility = View.VISIBLE
        tv_mensagem_erro.visibility = View.INVISIBLE
        pb_aguarde.visibility = View.INVISIBLE
    }


    fun exibirMensagemErro() {
        tv_dados_clima.visibility = View.INVISIBLE
        tv_mensagem_erro.visibility = View.VISIBLE
        pb_aguarde.visibility = View.INVISIBLE
    }

    fun exibirProgressBar() {
        tv_dados_clima.visibility = View.INVISIBLE
        tv_mensagem_erro.visibility = View.INVISIBLE
        pb_aguarde.visibility = View.VISIBLE
    }

    fun carregarDadosDoClima() {
        val localizacao = ClimaPreferencias.getLocalizacaoSalva(this)
        buscarClimaTask().execute(localizacao)
    }

    inner class buscarClimaTask : AsyncTask<String, Void, String>() {

        override fun onPreExecute() {
            exibirProgressBar()
        }

        override fun doInBackground(vararg params: String): String? {

            try {
                val localizacao = params[0]
                val url = NetworkUtils.construirUrl(localizacao)

                if (url != null) {
                    val resultado = NetworkUtils.obterRespostaDaUrlHttp(url)
                    return resultado
                }

            } catch (ex: Exception) {
                ex.printStackTrace()
            }
            return null
        }


        override fun onPostExecute(resultado: String?) {
            if (resultado != null) {
                tv_dados_clima.text = ""


                val json = JSONObject(resultado)
                val items = json.getJSONArray("list")

                for (i in 0 until items.length()) {

                    val obj = items.getJSONObject(i)
                    val result = obj.getString("dt")
                    val dataHoraMilissegundos: Long = (java.lang.Long.valueOf(result)) * 1000
                    val dataHora = Date(dataHoraMilissegundos)
                    val dataFormatada = DateFormat.format("dd/MM/yyyy ", dataHora)
                    val main = obj.getJSONObject("main")
                    val temp = main.getString("temp")
                    val umidade = main.getString("humidity")

                    val opclima = obj.getJSONArray("weather")

                    val clima = opclima.getJSONObject(0)
                    val descricao = clima.getString("description")

                    tv_dados_clima.append(" Data: $dataFormatada \n"
                            + " Temperatura: $temp ºC\n"
                            + " Umidade: $umidade %\n" +
                            " Clima: $descricao \n\n\n")
                }

                exibirResultado()

            } else {
                exibirMensagemErro()
            }
        }
    }
}
