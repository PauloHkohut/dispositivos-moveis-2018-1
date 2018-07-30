package br.grupointegrado.tads.listadetarefa

import android.app.Activity
import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import br.grupointegrado.tads.listadetarefa.dados.BdFake
import kotlinx.android.synthetic.main.activity_tarefa.*

class TarefaActivity : AppCompatActivity() {

    private var database: SQLiteDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tarefa)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.tarefa, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_salvar) {
            salvar()
            return true
        }
        return super.onOptionsItemSelected(item)
    }





    fun salvar() {
        val titulo = ed_titulo.text.toString()
        val descricao = ed_descricao.text.toString()

        if (titulo.isEmpty()) {
            exibirMensagem("Preencha o título da tarefa.")
            return
        }

        if (descricao.isEmpty()) {
            exibirMensagem("Preencha a descrição da tarefa.")
            return
        }

        adicionarNovaTarefa(titulo, descricao)

        setResult(Activity.RESULT_OK)
        finish()
    }




    fun exibirMensagem(mensagem: String) {
        Toast.makeText(this, mensagem, Toast.LENGTH_LONG).show()
    }

    private fun adicionarNovaTarefa(titulo: String, descricao: String): Long {
        val tarefa = ContentValues()
        tarefa.put(TarefaContrato.listadetarefa.COLUNA_TITULO, titulo)
        tarefa.put(TarefaContrato.listadetarefa.COLUNA_DESCRICAO, descricao)
        return database!!.insert(TarefaContrato.listadetarefa.TABELA, null, tarefa)
    }
}
