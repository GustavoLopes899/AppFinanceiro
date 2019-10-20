package br.edu.ifsp.scl.projetoappfinanceiro.data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import br.edu.ifsp.scl.projetoappfinanceiro.R
import br.edu.ifsp.scl.projetoappfinanceiro.model.Conta

class ContaAdapter(val contas: MutableList<Conta>) :
    RecyclerView.Adapter<ContaAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.conta_celula, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bindItems(contas[position])
    }

    override fun getItemCount(): Int {
        return contas.size
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(conta: Conta) {
            val nome = itemView.findViewById(R.id.nome_conta) as TextView
            val saldo = itemView.findViewById(R.id.saldo_conta) as TextView

            nome.text = conta.nome
            saldo.text = "R$" + String.format("%.2f", conta.saldoInicial)
            nome.setOnClickListener {
                Toast.makeText(itemView.context, "clicked on " + nome.text, Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}