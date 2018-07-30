package br.grupointegrado.tads.listadetarefa

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.BaseColumns
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.Menu
import android.view.MenuItem
import br.grupointegrado.tads.listadetarefa.dados.BdFake
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(){

    companion object {
        val CADASTRAR_TAREFA = 1001
    }

    private var tarefasAdapter: TarefasAdapter? = null
    private var database: SQLiteDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rv_clientes.layoutManager = LinearLayoutManager(this)

        val dbTarefa = ListaTarefasBdHelper(this)
        database = dbTarefa.writableDatabase

        val tarefas = getTodasTarefas();

        tarefasAdapter = TarefasAdapter(tarefas)
        rv_clientes.adapter = tarefasAdapter

        val itemTouch = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
                val id = viewHolder.itemView.tag as Long
                removerTarefa(id)
                tarefasAdapter!!.atualizar(getTodasTarefas())
            }

        })

        itemTouch.attachToRecyclerView(rv_clientes)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_adicionar) {
            adicionar()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    fun getTodasTarefas(): Cursor {
        return database!!.query(TarefaContrato.listadetarefa.TABELA,
                null, null, null, null, null,
                TarefaContrato.listadetarefa.COLUNA_DATA_HORA)
    }

    fun adicionar() {
        val intent = Intent(this, TarefaActivity::class.java)
        startActivityForResult(intent, CADASTRAR_TAREFA)
    }

    private fun removerTarefa(id: Long): Boolean {
        val nomeTabela = TarefaContrato.listadetarefa.TABELA
        val where = "${BaseColumns._ID} = ?"
        val argumentos = arrayOf(id.toString())

        val removidos = database!!.delete(nomeTabela, where, argumentos)

        return removidos > 0
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (CADASTRAR_TAREFA == requestCode && resultCode == Activity.RESULT_OK) {
            tarefasAdapter!!.atualizar(getTodasTarefas())
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

}
