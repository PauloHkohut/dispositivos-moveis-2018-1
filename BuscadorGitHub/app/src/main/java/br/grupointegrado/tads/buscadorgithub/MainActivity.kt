package br.grupointegrado.tads.buscadorgithub

import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import java.net.URL

class MainActivity : AppCompatActivity() {

    private val CONTEUDO_TEXTVIEW: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState != null){
            if (savedInstanceState.containsKey(CONTEUDO_TEXTVIEW)){
                val conteudoTextView = savedInstanceState.getString(CONTEUDO_TEXTVIEW)
                tv_github_resultado.text = conteudoTextView
            }
        }

        imprimir("onSaveInstanceState")

        // exercicioJson()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        imprimir("onSaveInstanceState")
        val conteudoTextView = tv_github_resultado.text.toString()
        outState?.putString(CONTEUDO_TEXTVIEW, conteudoTextView)
    }

    fun imprimir(mensagem: String){
        Log.d("BuscadorGitHub", mensagem)
        tv_github_resultado.append("$mensagem \n")
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.action_buscar) {
            buscarNoGithub()
        }
        return super.onOptionsItemSelected(item)
    }

    fun buscarNoGithub() {
        val busca = et_busca.text.toString()
        val urlBusca = NetworkUtils.construirUrl(busca)
        tv_url.text = urlBusca.toString()

        if (urlBusca != null) {
            val task = GithubBuscaTask()
            task.execute(urlBusca)
        }
    }

    fun exibirResultado() {
        tv_github_resultado.visibility = View.VISIBLE
        tv_mensagem_erro.visibility = View.INVISIBLE
        pb_aguarde.visibility = View.INVISIBLE
    }

    fun exibirMensagemErro() {
        tv_github_resultado.visibility = View.INVISIBLE
        tv_mensagem_erro.visibility = View.VISIBLE
        pb_aguarde.visibility = View.INVISIBLE
    }

    fun exibirProgressBar() {
        tv_github_resultado.visibility = View.INVISIBLE
        tv_mensagem_erro.visibility = View.INVISIBLE
        pb_aguarde.visibility = View.VISIBLE
    }

    //    fun exercicioJson() {
    //        var dadosJson = """
    //            {
    //                "temperatura": {
    //                    "maxima": 11.34,
    //                    "minima": 19.01
    //                },
    //                "clima": {
    //                    "id": 801,
    //                    "condicao": "Nuvens",
    //                    "descricao": "poucas nuvens"
    //                },
    //                "pressao": 1023.51,
    //                "umidade": 87
    //            }
    //            """
    //        val objetoPrevisao = JSONObject(dadosJson)
    //        val clima = objetoPrevisao.getJSONObject("clima")
    //        val condicao = clima.getString("condicao")
    //        val pressao = objetoPrevisao.getDouble("pressao")
    //
    //        Log.d("exercicioJson", "$condicao -> $pressao")
    //    }

    inner class GithubBuscaTask : AsyncTask<URL, Void, String>() {

        override fun onPreExecute() {
            exibirProgressBar()
        }

        override fun doInBackground(vararg params: URL?): String? {
            try {
                val url = params[0]
                val resultado =
                        NetworkUtils.obterRespostaDaUrlHtpp(url!!)
                return resultado
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
            return null
        }

        override fun onPostExecute(resultado: String?) {
            if (resultado != null) {
                /**
                 * Lendo o JSON para exibir apenas os nomes dos repositórios
                 */

                tv_github_resultado.text = ""

                val json = JSONObject(resultado)
                val items = json.getJSONArray("items")

                // percorra de Zero até o tamanho do array
                for (i in 0 until items.length()) {
                    val repository = items.getJSONObject(i)
                    val name = repository.getString("name")

                    tv_github_resultado.append("$name \n\n\n")
                }

                exibirResultado()
            } else {
                exibirMensagemErro()
            }
        }
    }

}
