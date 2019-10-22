package br.edu.ifsp.scl.projetoappfinanceiro.data

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.edu.ifsp.scl.projetoappfinanceiro.R
import br.edu.ifsp.scl.projetoappfinanceiro.model.Conta
import br.edu.ifsp.scl.projetoappfinanceiro.view.AtualizarContaActivity

class ContaAdapter :
    RecyclerView.Adapter<ContaAdapter.ItemViewHolder>() {

    companion object {
        var contas: ArrayList<Conta> = ArrayList()
    }

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

    fun notifyAdapter() {
        contas.sortBy { it.nome }
        this.notifyDataSetChanged()
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(conta: Conta) {
            val nome = itemView.findViewById(R.id.nome_conta) as TextView
            val descricao = itemView.findViewById(R.id.descricao_conta) as TextView
            val saldo = itemView.findViewById(R.id.saldo_conta) as TextView
            nome.text = conta.nome
            descricao.text = conta.descricao
            saldo.text = "R$" + String.format("%.2f", conta.saldo)

            itemView.setOnClickListener {
                //Toast.makeText(itemView.context, "Conta: " + nome.text + "\nDescrição: " + descricao.text + "\nSaldo: R$" + saldo.text, Toast.LENGTH_SHORT).show()
                var activity = AtualizarContaActivity()
                val atualizarIntent =
                    Intent(itemView.context, AtualizarContaActivity::class.java).apply {
                        putExtra("conta", conta)
                    }
                //activity.startActivityForResult(atualizarIntent, 1, Bundle())
                itemView.context.startActivity(atualizarIntent)
            }
        }
    }
}