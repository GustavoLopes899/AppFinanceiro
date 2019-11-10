package br.edu.ifsp.scl.projetoappfinanceiro.view

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
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
import java.text.SimpleDateFormat
import java.util.*

class NovaTransacaoActivity : AppCompatActivity() {

    private lateinit var contas: ArrayList<Conta>
    private lateinit var dataTransacao: Date
    private lateinit var daoConta: ContaSQLite

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nova_transacao)
        setSupportActionBar(toolbar)

        daoConta = ContaSQLite(this)
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
                if (valorTransacaoET.text.toString() == "") {
                    Toast.makeText(this, "O valor da transação não pode estar vazio", Toast.LENGTH_SHORT).show()
                    return false
                }
                val valor: Double = valorTransacaoET.text.toString().toDouble()
                val descricao: String = descricaoTransacaoET.text.toString()
                val natureza: String = spinnerNatureza.selectedItem.toString()
                val tipo: String = spinnerTipo.selectedItem.toString()
                val nomeConta: String = spinnerConta.selectedItem.toString()
                val data: String = dataTransacaoET.text.toString()
                if (data == "") {
                    Toast.makeText(this, "A data da transação não pode estar vazio", Toast.LENGTH_SHORT).show()
                    return false
                }
                val repete: Boolean = repeteCB.isChecked
                val vezes: Int = vezesTransacaoET.text.toString().toInt()
                val periodo: Int = periodoTransacaoET.text.toString().toInt()
                if (repete && periodo <= 0) {
                    Toast.makeText(this, "O período de repetição da transação é inválido", Toast.LENGTH_SHORT).show()
                    return false
                }
                var conta = 0

                for (c in contas) {
                    if (c.nome == nomeConta) {
                        conta = c.codigo
                        when (tipo) {
                            "Débito" -> c.saldo -= valor
                            "Crédito" -> c.saldo += valor
                        }
                        daoConta.updateConta(c)
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

                if (repete) {
                    val calendario: Calendar = Calendar.getInstance()
                    var dataNova: Date? = SimpleDateFormat("dd/MM/yyyy", Locale.US).parse(data)
                    for (i in 1..vezes) {
                        val novaTransacao = t.copy()
                        calendario.time = dataNova
                        calendario.add(Calendar.DATE, periodo)
                        dataNova = calendario.time
                        val formataData = SimpleDateFormat("dd/MM/yyy")
                        novaTransacao.data = formataData.format(dataNova)
                        dao.createTransacao(novaTransacao)
                        TransacaoAdapter.transacoes.add(novaTransacao)
                    }
                    TransacaoActivity.adapter.notifyAdapter()
                }

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

    fun verificarDataTransacao(view: View) {
        val editText: EditText = (view as EditText)
        val calendar = Calendar.getInstance()
        var data: String
        val dia: Int = calendar.get((Calendar.DAY_OF_MONTH))
        val mes: Int = calendar.get(Calendar.MONTH)
        val ano: Int = calendar.get(Calendar.YEAR)

        val dataPicker = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                data = if (dayOfMonth < 9) {
                    String.format("0%d/", dayOfMonth)
                } else {
                    String.format("%d/", dayOfMonth)
                }
                data += if (monthOfYear < 9) {
                    (String.format("0%d/", monthOfYear + 1))
                } else {
                    (String.format("%d/", monthOfYear + 1))
                }
                data += year
                dataTransacao = SimpleDateFormat("dd/MM/yyyy", Locale.US).parse(data)

                editText.setText(data)
            },
            ano,
            mes,
            dia
        )
        dataPicker.show()
    }
}
