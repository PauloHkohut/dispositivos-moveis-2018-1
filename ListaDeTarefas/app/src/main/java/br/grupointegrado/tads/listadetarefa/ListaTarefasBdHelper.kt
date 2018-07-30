package br.grupointegrado.tads.listadetarefa

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns

class ListaTarefasBdHelper : SQLiteOpenHelper{

    companion object {
        val BD_NOME = "listadetarefa.db"
        val BD_VERSAO = 1
    }

    constructor(context: Context) : super(context, BD_NOME, null, BD_VERSAO){

    }

    override fun onCreate(bd: SQLiteDatabase) {
        val CREATE_TABLE_LISTADETAREFA = """
            CREATE TABLE ${TarefaContrato.listadetarefa.TABELA}(
                         ${TarefaContrato.listadetarefa.COLUNA_ID} INTEGER PRIMARY KEY AUTOINCREMENT,
                         ${TarefaContrato.listadetarefa.COLUNA_TITULO} TEXT NOT NULL,
                         ${TarefaContrato.listadetarefa.COLUNA_DESCRICAO} TEXT NOT NULL,
                         ${TarefaContrato.listadetarefa.COLUNA_DATA_HORA} TIMESTAMP DEFAULT CURRENT_TIMESTAMP
            );
            """
        bd.execSQL(CREATE_TABLE_LISTADETAREFA)
    }

    override fun onUpgrade(bd: SQLiteDatabase, versaoAnterior: Int, novaVersao: Int) {
        bd.execSQL("DROP TABLE IF EXISTS ${TarefaContrato.listadetarefa};")
        onCreate(bd)
    }


}