package br.edu.ifsp.scl.projetoappfinanceiro.data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import br.edu.ifsp.scl.projetoappfinanceiro.R
import br.edu.ifsp.scl.projetoappfinanceiro.model.Transacao
import kotlinx.android.synthetic.main.transacao_celula.view.*

class TransacaoAdapter(val transacoes: MutableList<Transacao>): RecyclerView.Adapter<TransacaoAdapter.ItemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.transacao_celula, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bindItems(transacoes[position])
    }

    override fun getItemCount(): Int {
        return transacoes.size
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(transacao: Transacao) {
            val tipo = itemView.findViewById(R.id.tipoTv) as TextView
            val valor = itemView.findViewById(R.id.valorTv) as TextView

            tipo.text = transacao.tipo
            valor.text = "R$" + String.format("%.2f", transacao.valor)
            valor.setOnClickListener {
                Toast.makeText(itemView.context, "clicked on " + valor.text, Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}