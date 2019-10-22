package br.edu.ifsp.scl.projetoappfinanceiro.view

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
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
    //private lateinit var adapter: ContaAdapter
    private lateinit var context: Context

    companion object {
        lateinit var adapter: ContaAdapter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_conta)
        setSupportActionBar(toolbar)
        context = this

        val database = ContaSQLite(this)
        ContaAdapter.contas = database.readContas()

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        adapter = ContaAdapter()
        adapter.notifyAdapter()
        recyclerView.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.contas_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onClick(v: View?) {
        // TODO
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.adicionar -> {
                val adicionarIntent = Intent(this, NovaContaActivity::class.java)
                startActivityForResult(adicionarIntent, REQUEST_CODE_ADICIONAR_CONTA)
            }
            R.id.voltaContas -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_ADICIONAR_CONTA) {
            if (resultCode == Activity.RESULT_OK) {
                adapter.notifyAdapter()
            }
        }
    }

}
