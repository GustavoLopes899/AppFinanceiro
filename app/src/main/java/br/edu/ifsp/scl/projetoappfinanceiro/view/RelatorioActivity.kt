package br.edu.ifsp.scl.projetoappfinanceiro.view

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.scl.projetoappfinanceiro.R
import br.edu.ifsp.scl.projetoappfinanceiro.data.ContaSQLite
import br.edu.ifsp.scl.projetoappfinanceiro.model.Conta
import br.edu.ifsp.scl.projetoappfinanceiro.utils.Utils.Companion.openDialog
import kotlinx.android.synthetic.main.content_relatorio.*
import kotlinx.android.synthetic.main.toolbar.*
import java.text.SimpleDateFormat
import java.util.*

class RelatorioActivity : AppCompatActivity() {

    private lateinit var contas: ArrayList<Conta>
    private var dataInicio: Date? = Date()
    private var dataFim: Date? = Date()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_relatorio)
        setSupportActionBar(toolbar)

        val daoConta = ContaSQLite(this)
        contas = daoConta.readContas()

        if (contas.size == 0) {
            Toast.makeText(this, getString(R.string.nenhuma_conta), Toast.LENGTH_SHORT).show()
            finish()
        }

        val spinner = spinnerPorConta
        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, contas)
        spinner.adapter = arrayAdapter

        porContaRB.setOnClickListener {
            porContaRB.isChecked = true
            porNaturezaRB.isChecked = false
            porTipoRB.isChecked = false
        }
        porNaturezaRB.setOnClickListener {
            porContaRB.isChecked = false
            porNaturezaRB.isChecked = true
            porTipoRB.isChecked = false
            spinnerPorNatureza.isClickable = true
        }
        porTipoRB.setOnClickListener {
            porContaRB.isChecked = false
            porNaturezaRB.isChecked = false
            porTipoRB.isChecked = true
        }
    }

    fun verificarDataPorPeriodo(view: View) {
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
                if (editText == comecoPeriodoET) {
                    dataInicio = SimpleDateFormat("dd/MM/yyyy", Locale.US).parse(data)
                } else {
                    dataFim = SimpleDateFormat("dd/MM/yyyy", Locale.US).parse(data)
                }
                if (dataInicio != null && dataFim != null && (dataInicio!! > dataFim)) {
                    dataInicio = null
                    comecoPeriodoET.setText("")
                    dataFim = null
                    fimPeriodoET.setText("")
                    openDialog(this, getString(R.string.periodo_invalido))
                    return@OnDateSetListener
                }
                editText.setText(data)
            },
            ano,
            mes,
            dia
        )
        dataPicker.show()
    }

    fun gerarRelatorio(view: View) {
        var where = ""
        var comeco = ""
        var fim = ""
        val relatorioIntent: Intent
        when {
            porContaRB.isChecked -> {
                Toast.makeText(this, "Por Conta", Toast.LENGTH_SHORT).show()
                val daoConta = ContaSQLite(this)
                var codConta = 0
                contas = daoConta.readContas()
                for (c in contas) {
                    if (c.nome == spinnerPorConta.selectedItem.toString()) {
                        codConta = c.codigo
                        break
                    }
                }
                where = "codigo_conta= $codConta "
                comeco = comecoPeriodoET.text.toString()
                fim = fimPeriodoET.text.toString()
            }
            porNaturezaRB.isChecked -> {
                Toast.makeText(this, "Por Natureza", Toast.LENGTH_SHORT).show()
                where = "natureza='" + spinnerPorNatureza.selectedItem.toString() + "'"

            }
            porTipoRB.isChecked -> {
                Toast.makeText(this, "Por Tipo", Toast.LENGTH_SHORT).show()
                where = "tipo='" + spinnerPorTipo.selectedItem.toString() + "'"
            }
        }
        relatorioIntent =
            Intent(this, MostrarRelatorioActivity::class.java).apply {
                putExtra("where", where)
                putExtra("comeco", comeco)
                putExtra("fim", fim)
            }
        startActivity(relatorioIntent)
    }
}