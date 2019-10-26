package br.edu.ifsp.scl.projetoappfinanceiro.data

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.edu.ifsp.scl.projetoappfinanceiro.R
import br.edu.ifsp.scl.projetoappfinanceiro.model.Transacao
import br.edu.ifsp.scl.projetoappfinanceiro.view.AtualizarTransacaoActivity

class TransacaoAdapter : RecyclerView.Adapter<TransacaoAdapter.ItemViewHolder>() {

    companion object {
        var transacoes: ArrayList<Transacao> = ArrayList()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.transacao_celula, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bindItems(transacoes[position])
    }

    override fun getItemCount(): Int {
        return transacoes.size
    }

    fun notifyAdapter() {
        transacoes.sortBy { it.data }
        this.notifyDataSetChanged()
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(transacao: Transacao) {
            val conta = itemView.findViewById(R.id.contaTransacaoCelulaTV) as TextView
            val data = itemView.findViewById(R.id.dataTransacaoCelulaTV) as TextView
            val valor = itemView.findViewById(R.id.valorTransacaoCelulaTV) as TextView
            val tipo = itemView.findViewById(R.id.tipoTransacaoCelulaTV) as TextView
            val natureza = itemView.findViewById(R.id.naturezaTransacaoCelulaTV) as TextView

            val dao = ContaSQLite(itemView.context)
            val contaNome = dao.readConta(transacao.conta).nome

            conta.text = "Conta: $contaNome"
            data.text = transacao.data
            valor.text = "R$" + String.format("%.2f", transacao.valor)
            tipo.text = transacao.tipo
            natureza.text = transacao.natureza
            itemView.setOnClickListener {
                val atualizarIntent =
                    Intent(itemView.context, AtualizarTransacaoActivity::class.java).apply {
                        putExtra("transacao", transacao)
                    }
                itemView.context.startActivity(atualizarIntent)
            }
        }
    }
}