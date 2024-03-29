package br.edu.ifsp.scl.projetoappfinanceiro.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.scl.projetoappfinanceiro.R
import br.edu.ifsp.scl.projetoappfinanceiro.data.ContaAdapter
import br.edu.ifsp.scl.projetoappfinanceiro.data.ContaSQLite
import br.edu.ifsp.scl.projetoappfinanceiro.model.Conta
import kotlinx.android.synthetic.main.content_cadastro_conta.*
import kotlinx.android.synthetic.main.toolbar.*

class NovaContaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nova_conta)
        setSupportActionBar(toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.adicionar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.salvar -> {
                val nome: String = nomeET.text.toString()
                if (nome.trim() == "") {
                    Toast.makeText(this, "O nome da conta não pode estar vazio", Toast.LENGTH_SHORT).show()
                    return false
                }
                val descricao: String = descricaoET.text.toString()
                if (saldoET.text.toString() == "") {
                    Toast.makeText(this, "O saldo inicial da conta não pode estar vazio", Toast.LENGTH_SHORT).show()
                    return false
                }
                val saldoInicial: Double = saldoET.text.toString().toDouble()
                val c = Conta()
                c.nome = nome
                c.descricao = descricao
                c.saldo = saldoInicial

                val dao = ContaSQLite(this)
                dao.createConta(c)
                ContaAdapter.contas.add(c)
                ContaActivity.adapter.notifyAdapter()

                Toast.makeText(this, "Conta salva com sucesso!", Toast.LENGTH_SHORT).show()
                setResult(RESULT_OK)
                finish()
            }
            R.id.voltar -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
