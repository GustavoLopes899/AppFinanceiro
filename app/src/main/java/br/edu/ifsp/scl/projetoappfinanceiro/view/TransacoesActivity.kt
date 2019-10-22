package br.edu.ifsp.scl.projetoappfinanceiro.view

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.edu.ifsp.scl.projetoappfinanceiro.R
import br.edu.ifsp.scl.projetoappfinanceiro.data.TransacaoAdapter
import br.edu.ifsp.scl.projetoappfinanceiro.data.TransacaoSQLite
import kotlinx.android.synthetic.main.toolbar.*

class TransacoesActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TransacaoAdapter
    private lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_conta)
        setSupportActionBar(toolbar)

        // Pega os dados de transacoes
        val database = TransacaoSQLite(this)
        val transacoes = database.readTransacoes()

        // Inicializa recycler view com os dados
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        adapter = TransacaoAdapter(transacoes)
        recyclerView.adapter = adapter
        // Adiciona onClickListener para cada celula
        recyclerView.addOnItemClickListener(object : OnItemClickListener {
            override fun onItemClicked(position: Int, view: View) {
                Toast.makeText(context, "clicked on " + transacoes[position].valor, Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

    // Interface e funcao do recycler view para adicionar onClickListener
    interface OnItemClickListener {
        fun onItemClicked(position: Int, view: View)
    }

    private fun RecyclerView.addOnItemClickListener(onClickListener: OnItemClickListener) {
        // Adiciona onClickListener ara celulas filhas do recycler view
        this.addOnChildAttachStateChangeListener(object :
            RecyclerView.OnChildAttachStateChangeListener {
            override fun onChildViewDetachedFromWindow(view: View) {
                view.setOnClickListener(null)
            }

            override fun onChildViewAttachedToWindow(view: View) {
                view.setOnClickListener {
                    val holder = getChildViewHolder(view)
                    onClickListener.onItemClicked(holder.adapterPosition, view)
                }
            }
        })
    }


    override fun onClick(p0: View?) {
        // Mostrar dados para atualizar transacao
    }
}
