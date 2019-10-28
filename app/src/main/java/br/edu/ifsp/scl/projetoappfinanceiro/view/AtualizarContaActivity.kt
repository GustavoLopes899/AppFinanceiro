package br.edu.ifsp.scl.projetoappfinanceiro.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.scl.projetoappfinanceiro.R
import br.edu.ifsp.scl.projetoappfinanceiro.data.ContaAdapter.Companion.contas
import br.edu.ifsp.scl.projetoappfinanceiro.data.ContaSQLite
import br.edu.ifsp.scl.projetoappfinanceiro.model.Conta
import kotlinx.android.synthetic.main.content_cadastro_conta.*
import kotlinx.android.synthetic.main.toolbar.*

class AtualizarContaActivity : AppCompatActivity() {

    private lateinit var dao: ContaSQLite
    private lateinit var conta: Conta
    private lateinit var nome: EditText
    private lateinit var descricao: EditText
    private lateinit var saldo: EditText
    private var codigo: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_atualizar_conta)
        setSupportActionBar(toolbar)
        dao = ContaSQLite(this)

        conta = intent.extras?.get("conta") as Conta
        nome = this.findViewById(R.id.nomeET)
        descricao = this.findViewById(R.id.descricaoET)
        saldo = this.findViewById(R.id.saldoET)

        nome.setText(conta.nome)
        codigo = conta.codigo
        descricao.setText(conta.descricao)
        saldo.setText(conta.saldo.toString())
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.atualizar_menu, menu)
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
                c.codigo = codigo
                c.nome = nome
                c.descricao = descricao
                c.saldo = saldoInicial

                dao.updateConta(c)
                contas[contas.indexOf(c)] = c
                ContaActivity.adapter.notifyAdapter()

                Toast.makeText(this, "Conta salva com sucesso!", Toast.LENGTH_SHORT).show()
                finish()
            }
            R.id.deletar -> {
                dao.deleteConta(conta.codigo)
                contas.remove(conta)
                ContaActivity.adapter.notifyAdapter()
                Toast.makeText(this, "Conta deletada com sucesso!", Toast.LENGTH_SHORT).show()
                finish()
            }
            R.id.voltar -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
