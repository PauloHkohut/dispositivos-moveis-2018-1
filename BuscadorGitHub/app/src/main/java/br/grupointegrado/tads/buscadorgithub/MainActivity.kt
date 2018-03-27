package br.grupointegrado.tads.buscadorgithub

import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import java.lang.Exception
import java.net.URL

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        exercicioJson()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.action_buscar){
            buscarNoGithub()
            //Toast.makeText(this, "Clicou!", Toast.LENGTH_SHORT ).show()
    }
     return super.onOptionsItemSelected(item)}


    fun buscarNoGithub(){
        val buscaGithub = et_busca.text.toString()
        val urlBuscaGithub = NetworkUtils.construirUrl(buscaGithub)
        tv_url.text = urlBuscaGithub.toString()

        //if(urlBuscaGithub != null){
            //val resultado = NetworkUtils.obterRespostaDaUrlHtpp(urlBuscaGithub)
            //tv_github_resultado.text = resultado
        val buscaTask = GithubBuscaTask()
        buscaTask.execute(urlBuscaGithub)
        }

    fun exibirResultado(){
        tv_github_resultado.visibility = View.VISIBLE
        tv_mensagem_erro.visibility = View.INVISIBLE
        pb_aguarde.visibility = View.INVISIBLE
    }

    fun exibirMensagemErro(){
        tv_github_resultado.visibility = View.INVISIBLE
        tv_mensagem_erro.visibility = View.VISIBLE
        pb_aguarde.visibility = View.INVISIBLE
    }

    fun exibirProgressBar(){
        tv_github_resultado.visibility = View.INVISIBLE
        tv_mensagem_erro.visibility = View.INVISIBLE
        pb_aguarde.visibility = View.VISIBLE
    }



    fun exercicioJson() {
        var dadosJson = """{
            "temperatura": {
            "minima": 11.34,
            "maxima": 19.01
            },
           "clima":{
           "id": 801,
           "condicao": "Nuvens",
           "descricao": "Poucas nuvens"
           },
           "pressao":1023.51,
           "umidade": 87
           }

        """

        val objetoPrevisao = JSONObject(dadosJson)
        val clima = objetoPrevisao.getJSONObject("clima")
        val condicao = clima.getString("condicao")
        val pressao = objetoPrevisao.getDouble("pressao")

        Log.d("exercicioJson", "$condicao -> $pressao")
    }





    inner class GithubBuscaTask: AsyncTask<URL, Void, String>() {

        override fun onPreExecute() {
            exibirProgressBar()
        }

        override fun doInBackground(vararg params: URL): String? {
            try {
                val url = params[0]
                val resultado = NetworkUtils.obterRespostaDaUrlHtpp(url)
                return resultado
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
            return null
        }

        override fun onPostExecute(resultado: String?){
            if (resultado != null) {
                tv_github_resultado.text = resultado
                exibirResultado()
            }else {
                exibirMensagemErro()
            }
        }
    }


}
