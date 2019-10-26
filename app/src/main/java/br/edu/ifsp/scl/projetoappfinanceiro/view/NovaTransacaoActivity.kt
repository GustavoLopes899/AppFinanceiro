package br.edu.ifsp.scl.projetoappfinanceiro.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.scl.projetoappfinanceiro.R
import br.edu.ifsp.scl.projetoappfinanceiro.data.ContaSQLite
import br.edu.ifsp.scl.projetoappfinanceiro.data.TransacaoAdapter
import br.edu.ifsp.scl.projetoappfinanceiro.data.TransacaoSQLite
import br.edu.ifsp.scl.projetoappfinanceiro.model.Conta
import br.edu.ifsp.scl.projetoappfinanceiro.model.Transacao
import kotlinx.android.synthetic.main.content_cadastro_transacao.*
import kotlinx.android.synthetic.main.toolbar.*

class NovaTransacaoActivity : AppCompatActivity() {

    private lateinit var contas: ArrayList<Conta>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nova_transacao)
        setSupportActionBar(toolbar)

        val daoConta = ContaSQLite(this)
        contas = daoConta.readContas()

        val spinner = spinnerConta
        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, contas)
        spinner.adapter = arrayAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.adicionar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.salvar -> {
                val valor: Double = valorTransacaoET.text.toString().toDouble()
                val descricao: String = descricaoTransacaoET.text.toString()
                val natureza: String = spinnerNatureza.selectedItem.toString()
                val tipo: String = spinnerTipo.selectedItem.toString()
                val nomeConta: String = spinnerConta.selectedItem.toString()
                val data: String = dataTransacaoET.text.toString()

                var conta = 0

                for (c in contas) {
                    if (c.nome == nomeConta) {
                        conta = c.codigo
                        break
                    }
                }

                val t = Transacao()
                t.valor = valor
                t.descricao = descricao
                t.natureza = natureza
                t.tipo = tipo
                t.conta = conta
                t.data = data

                val dao = TransacaoSQLite(this)
                dao.createTransacao(t)
                TransacaoAdapter.transacoes.add(t)
                TransacaoActivity.adapter.notifyAdapter()

                Toast.makeText(this, "Transação salva com sucesso!", Toast.LENGTH_SHORT).show()
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
