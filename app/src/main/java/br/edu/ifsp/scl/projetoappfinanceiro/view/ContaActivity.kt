package br.edu.ifsp.scl.projetoappfinanceiro.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.edu.ifsp.scl.projetoappfinanceiro.R
import br.edu.ifsp.scl.projetoappfinanceiro.data.Constantes.REQUEST_CODE_ADICIONAR_CONTA
import br.edu.ifsp.scl.projetoappfinanceiro.data.ContaAdapter
import br.edu.ifsp.scl.projetoappfinanceiro.data.ContaSQLite
import kotlinx.android.synthetic.main.toolbar.*

class ContaActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ContaAdapter
    private lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_conta)
        setSupportActionBar(toolbar)

        val database = ContaSQLite(this)
        val contas = database.readContas()

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        adapter = ContaAdapter(contas)
        recyclerView.adapter = adapter
        recyclerView.addOnItemClickListener(object : OnItemClickListener {
            override fun onItemClicked(position: Int, view: View) {
                Toast.makeText(context, "clicked on " + contas[position].nome, Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

    interface OnItemClickListener {
        fun onItemClicked(position: Int, view: View)
    }

    private fun RecyclerView.addOnItemClickListener(onClickListener: OnItemClickListener) {
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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.contas_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onClick(v: View?) {
        // Fazer aqui
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.adicionar -> {
                val adicionarIntent = Intent(this, AdicionarActivity::class.java)
                startActivityForResult(adicionarIntent, REQUEST_CODE_ADICIONAR_CONTA)
            }
            R.id.voltaContas -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

}
