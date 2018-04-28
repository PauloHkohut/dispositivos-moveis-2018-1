package br.grupointegrado.tads.clima

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class PrevisaoAdapter : RecyclerView.Adapter<PrevisaoAdapter.PrevisaoViewHolder>{
    // colocando como private automaticamente retira o setDadosClima padrao(ele ja existe)
    //fazendo assim voce mesmo que cria o metodo.
    private var dadosClima: Array<String?>?

    constructor(dadosClima: Array<String?>?){
        this.dadosClima = dadosClima
    }

    inner class PrevisaoViewHolder : RecyclerView.ViewHolder{

        val tvDadosPrevisao: TextView

        constructor(itemView: View) : super(itemView){
            tvDadosPrevisao = itemView.findViewById(R.id.tv_dados_previsao)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PrevisaoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.previsao_lista_item,
                parent, false)

        val viewViewHolder = PrevisaoViewHolder(view)

        return viewViewHolder
    }

    override fun onBindViewHolder(holder: PrevisaoViewHolder, position: Int) {
        val previsao = dadosClima?.get(position)
        holder.tvDadosPrevisao.text = previsao.toString()
    }

    override fun getItemCount() : Int{
        val dados = dadosClima

        if (dados != null){
            return dados.size
        }else{
            return 0
        }
    }

    fun setDadosClima(dadosClima: Array<String?>?){
        this.dadosClima = dadosClima
        notifyDataSetChanged()
    }

}