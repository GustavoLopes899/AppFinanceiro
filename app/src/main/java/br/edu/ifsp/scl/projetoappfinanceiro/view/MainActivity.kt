package br.edu.ifsp.scl.projetoappfinanceiro.view

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.scl.projetoappfinanceiro.R
import br.edu.ifsp.scl.projetoappfinanceiro.data.ContaSQLite
import br.edu.ifsp.scl.projetoappfinanceiro.model.Conta
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.DefaultValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import kotlinx.android.synthetic.main.toolbar.*

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var dao: ContaSQLite
    lateinit var contas: MutableList<Conta>
    private lateinit var total: TextView
    private lateinit var pieChart: PieChart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        dao = ContaSQLite(this)
        total = findViewById(R.id.saldoTotalTv)

        // Carrega dados do grafico
        pieChart = findViewById(R.id.saldosPieChart)
        // Pie Chart valores de configuracao
        pieChart.description.isEnabled = false
        pieChart.legend.isEnabled = false
        pieChart.setExtraOffsets(5f, 10f, 5f, 5f)
        pieChart.dragDecelerationFrictionCoef = 0.95f
        pieChart.isDrawHoleEnabled = true
        pieChart.setHoleColor(Color.WHITE)
        pieChart.transparentCircleRadius = 61f
        pieChart.centerText = "Saldos"
    }

    override fun onResume() {
        super.onResume()
        // Atualiza main activity quando retorna a ela de qualquer outra activity
        contas = dao.readContas()
        total.text = "Saldo Total: R$ %.2f".format(contas.sumByDouble { it.saldo })

        // Adiciona valores de saldo de contas
        val values: MutableList<PieEntry> = mutableListOf()
        contas.forEach {
            values.add(PieEntry(it.saldo.toFloat(), it.nome))
        }
        val pieDataSet: PieDataSet = PieDataSet(values, "Saldos")

        // Config pie data layout
        pieDataSet.sliceSpace = 3f
        pieDataSet.selectionShift = 5f
        pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS.toMutableList())

        // Add pie data
        val pieData: PieData = PieData(pieDataSet)
        pieData.setValueTextSize(10f)
        pieData.setValueFormatter(DefaultValueFormatter(2))
        // Add data to chart
        pieChart.data = pieData
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.verContasbtn -> {
                // Botao Inicia activity de Conta
                startActivity(Intent(this, ContaActivity::class.java))
            }
            R.id.verTransacoesbtn -> {
                // Botao inicia activity de transacoes
                startActivity(Intent(this, TransacaoActivity::class.java))
            }
            R.id.verRelatorios -> {
                // Botao inicia activity de Extratos
                startActivity(Intent(this, RelatorioActivity::class.java))
            }
        }
    }

}