package br.grupointegrado.tads.clima


import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import br.grupointegrado.tads.clima.util.NetworkUtils
import br.grupointegrado.tads.clima.dados.ClimaPreferencias
import br.grupointegrado.tads.clima.util.JsonUtils
import kotlinx.android.synthetic.main.activity_main.*
import android.content.Intent
import android.net.Uri
import android.util.Log

class MainActivity : AppCompatActivity(), PrevisaoAdapter.PrevisaoItemClickListener {


    var previsaoAdapter: PrevisaoAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        previsaoAdapter = PrevisaoAdapter(null, this)
        val linearLayout = LinearLayoutManager(this)

        rv_clima.layoutManager = linearLayout
        rv_clima.adapter = previsaoAdapter


        carregarDadosClima()
    }

    override fun onItemClick(index: Int) {
        val previsao = previsaoAdapter!!.getDadosClima()!!.get(index)

        val intentDetalhes = Intent(this, DetalhesActivity::class.java)
        intentDetalhes.putExtra(DetalhesActivity.DADOS_PREVISAO, previsao)

        startActivity(intentDetalhes)
    }


    fun abrirMapa() {
        val addressString = "Campo Mourão, Paraná, Brasil"
        val uriGeo = Uri.parse("geo:0,0?q=$addressString")

        val intentMapa = Intent(Intent.ACTION_VIEW)
        intentMapa.data = uriGeo

        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intentMapa)
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.clima, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId === R.id.acao_atualizar) {
            carregarDadosClima()
            return true
        }
        if (item?.itemId === R.id.acao_mapa) {
            abrirMapa()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    fun exibirResultado() {
        rv_clima.visibility = View.VISIBLE
        tv_mensagem_erro.visibility = View.INVISIBLE
        pb_aguarde.visibility = View.INVISIBLE
    }


    fun exibirMensagemErro() {
        rv_clima.visibility = View.INVISIBLE
        tv_mensagem_erro.visibility = View.VISIBLE
        pb_aguarde.visibility = View.INVISIBLE
    }

    fun exibirProgressBar() {
        rv_clima.visibility = View.INVISIBLE
        tv_mensagem_erro.visibility = View.INVISIBLE
        pb_aguarde.visibility = View.VISIBLE
    }

    fun carregarDadosClima() {
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
                //tv_dados_clima.text = ""


                //No getSimplesStringsDeClimaDoJson pode usar (applicationContext) no lugar de this@MainActivity
                val conversao = JsonUtils.getSimplesStringsDeClimaDoJson(this@MainActivity, resultado)
                previsaoAdapter?.setDadosClima(conversao)

                exibirResultado()
            } else {
                exibirMensagemErro()
            }
        }
    }


    /*val json = JSONObject(resultado)
    val listaPrevisoes = json.getJSONArray("list")

    for (i in 0 until listaPrevisoes.length()) {
        val previsao = listaPrevisoes.getJSONObject(i)

        val dataLong = previsao.getLong("dt")
        val data = Date(dataLong * 1000)
        val dataFormatada = DateFormat.format("dd/MM/yyyy HH:mm", data)

        val principal = previsao.getJSONObject("main")
        val temperatura = principal.getDouble("temp")
        val umidade = principal.getDouble("humidity")

        val listaClimas = previsao.getJSONArray("weather")
        val clima = listaClimas.getJSONObject(0)
        val descricao = clima.getString("description")

        tv_dados_clima.append("Data: $dataFormatada \n")
        tv_dados_clima.append("Temperatura: $temperatura ºC \n")
        tv_dados_clima.append("Umidade: $umidade % \n")
        tv_dados_clima.append("Clima: $descricao \n")
        tv_dados_clima.append("\n\n")
    }

    exibirResultado()
} else {
    exibirMensagemErro()
}*/
}
