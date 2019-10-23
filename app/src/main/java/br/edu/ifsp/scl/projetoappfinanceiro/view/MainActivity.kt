package br.edu.ifsp.scl.projetoappfinanceiro.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.scl.projetoappfinanceiro.R
import br.edu.ifsp.scl.projetoappfinanceiro.data.ContaSQLite
import br.edu.ifsp.scl.projetoappfinanceiro.model.Conta
import kotlinx.android.synthetic.main.toolbar.*

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var dao: ContaSQLite
    lateinit var contas: MutableList<Conta>
    private lateinit var total: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        dao = ContaSQLite(this)
        total = findViewById(R.id.saldoTotalTv)
    }

    override fun onResume() {
        super.onResume()
        // Atualiza main activity quando retorna a ela de qualquer outra activity
        contas = dao.readContas()
        total.setText("Saldo Total: " + contas.sumByDouble { it.saldo }.toString())
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.verContasbtn -> {
                // Botao Inicia activity de Conta
                startActivity(Intent(this, ContaActivity::class.java))
            }
            R.id.verTransacoesbtn -> {
                // Botao inicia activity de transacoes
                startActivity(Intent(this, TransacoesActivity::class.java))
            }
        }
    }

}