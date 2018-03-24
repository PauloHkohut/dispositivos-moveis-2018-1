package br.grupointegrado.tads.buscadorgithub

import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception
import java.net.URL

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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


    inner class GithubBuscaTask: AsyncTask<URL, Void, String>() {

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

        override fun onPostExecute(result: String?){
            tv_github_resultado.text = result
        }
    }
}
