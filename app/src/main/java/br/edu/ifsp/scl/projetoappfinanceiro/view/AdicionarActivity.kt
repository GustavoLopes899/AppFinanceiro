package br.edu.ifsp.scl.projetoappfinanceiro.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.scl.projetoappfinanceiro.R
import br.edu.ifsp.scl.projetoappfinanceiro.data.ContaSQLite
import br.edu.ifsp.scl.projetoappfinanceiro.model.Conta
import kotlinx.android.synthetic.main.content_adicionar_conta.*
import kotlinx.android.synthetic.main.toolbar.*

class AdicionarActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adicionar)
        setSupportActionBar(toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.adicionar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.salvar -> {
                val nome: String = nome.text.toString()
                val descricao: String = descricao.text.toString()
                val saldoInicial: Double = saldoInicial.text.toString().toDouble()
                val c = Conta()
                c.nome = nome
                c.descricao = descricao
                c.saldoInicial = saldoInicial

                val dao = ContaSQLite(this)
                dao.createConta(c)

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


    //
}
