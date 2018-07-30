package br.grupointegrado.tads.listadetarefa

import android.content.ContentValues
import android.provider.BaseColumns
import br.grupointegrado.tads.listadetarefa.dados.BdFake
import java.util.*

object TarefaContrato {

    internal object listadetarefa : BaseColumns{

        const val TABELA = "listadetarefa"
        const val COLUNA_ID = "_id"
        const val COLUNA_TITULO = "titulo"
        const val COLUNA_DESCRICAO = "descricao"
        const val COLUNA_DATA_HORA = "data_hora"

    }

}