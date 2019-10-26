package br.edu.ifsp.scl.projetoappfinanceiro.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.scl.projetoappfinanceiro.R
import br.edu.ifsp.scl.projetoappfinanceiro.data.ContaSQLite
import br.edu.ifsp.scl.projetoappfinanceiro.data.TransacaoAdapter.Companion.transacoes
import br.edu.ifsp.scl.projetoappfinanceiro.data.TransacaoSQLite
import br.edu.ifsp.scl.projetoappfinanceiro.model.Conta
import br.edu.ifsp.scl.projetoappfinanceiro.model.Transacao
import kotlinx.android.synthetic.main.content_cadastro_transacao.*
import kotlinx.android.synthetic.main.toolbar.*
import kotlin.collections.ArrayList

class AtualizarTransacaoActivity : AppCompatActivity() {

    private lateinit var contas: ArrayList<Conta>
    private lateinit var dao: TransacaoSQLite
    private lateinit var transacao: Transacao
    private lateinit var valor: EditText
    private lateinit var descricao: EditText
    private lateinit var natureza: Spinner
    private lateinit var tipo: Spinner
    private lateinit var conta: Spinner
    private lateinit var data: EditText
    private lateinit var periodo: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_atualizar_transacao)
        setSupportActionBar(toolbar)
        dao = TransacaoSQLite(this)

        val daoConta = ContaSQLite(this)
        contas = daoConta.readContas()
        val spinner = spinnerConta
        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, contas)
        spinner.adapter = arrayAdapter

        transacao = intent.extras?.get("transacao") as Transacao
        valor = this.findViewById(R.id.valorTransacaoET)
        descricao = this.findViewById(R.id.descricaoTransacaoET)
        natureza = this.findViewById(R.id.spinnerNatureza)
        tipo = this.findViewById(R.id.spinnerTipo)
        conta = this.findViewById(R.id.spinnerConta)
        data = this.findViewById(R.id.dataTransacaoET)
        periodo = this.findViewById(R.id.periodoTransacaoET)

        valor.setText(transacao.valor.toString())
        descricao.setText(transacao.descricao)
        natureza.setSelection(resources.getStringArray(R.array.natureza).indexOf(transacao.natureza))
        tipo.setSelection(resources.getStringArray(R.array.tipo).indexOf(transacao.tipo))
        conta.setSelection(arrayAdapter.getPosition(daoConta.readConta(transacao.conta)))
        data.setText(transacao.data)
        periodo.setText(transacao.periodos)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.atualizar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.salvar -> {
                // TODO -> Verificar possiveis alterações nos valores //
                val valor: Double = valorTransacaoET.text.toString().toDouble()
                val descricao: String = descricaoTransacaoET.text.toString()
                val natureza: String = spinnerNatureza.selectedItem.toString()
                val tipo: String = spinnerTipo.selectedItem.toString()
                val nomeConta: String = spinnerConta.selectedItem.toString()
                val data: String = dataTransacaoET.text.toString()
                val periodo: String = periodoTransacaoET.text.toString()

                val t = Transacao()
                t.codigo = transacao.codigo
                t.valor = valor
                t.descricao = descricao
                t.natureza = natureza
                t.tipo = tipo
                for (c in contas) {
                    if (c.nome == nomeConta) {
                        t.conta = c.codigo
                        break
                    }
                }
                t.data = data
                t.periodos = periodo

                dao.updateTransacao(t)
                transacoes[transacoes.indexOf(t)] = t
                TransacaoActivity.adapter.notifyAdapter()
                Toast.makeText(this, "Transação atualizada com sucesso!", Toast.LENGTH_SHORT).show()
                finish()
            }
            R.id.deletar -> {
                // TODO -> precisa atualizar o valor da transação na conta //
                dao.deleteTransacao(transacao.codigo)
                transacoes.remove(transacao)
                TransacaoActivity.adapter.notifyAdapter()
                Toast.makeText(this, "Transação deletada com sucesso!", Toast.LENGTH_SHORT).show()
                finish()
            }
            R.id.voltar -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
