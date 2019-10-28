package br.edu.ifsp.scl.projetoappfinanceiro.view

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.edu.ifsp.scl.projetoappfinanceiro.R
import br.edu.ifsp.scl.projetoappfinanceiro.data.TransacaoAdapter
import br.edu.ifsp.scl.projetoappfinanceiro.data.TransacaoSQLite
import br.edu.ifsp.scl.projetoappfinanceiro.model.Transacao
import kotlinx.android.synthetic.main.toolbar.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MostrarRelatorioActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mostrar_relatorio)
        setSupportActionBar(toolbar)
        context = this

        // Pega os dados de transacoes
        val database = TransacaoSQLite(this)
        val where: String = intent.extras?.get("where").toString()
        TransacaoAdapter.transacoes = database.readTransacoes(where)

        val comeco = intent.extras?.get("comeco").toString()
        val fim = intent.extras?.get("fim").toString()
        if (comeco != "" || fim != "") {
            var dataTransacao: Date?
            var dataInicio: Date? = Date()
            var dataFim: Date? = Date()
            if (comeco != "") {
                dataInicio = SimpleDateFormat("dd/MM/yyyy", Locale.US).parse(comeco)
            }
            if (fim != "") {
                dataFim = SimpleDateFormat("dd/MM/yyyy", Locale.US).parse(fim)
            }
            val transacoesTemp: ArrayList<Transacao> = ArrayList()
            for (t in TransacaoAdapter.transacoes) {
                dataTransacao = SimpleDateFormat("dd/MM/yyyy", Locale.US).parse(t.data)
                when {
                    comeco != "" && fim == "" -> {
                        if (dataTransacao >= dataInicio) {
                            transacoesTemp.add(t)
                        }
                    }
                    comeco == "" && fim != "" -> {
                        if (dataTransacao <= dataFim) {
                            transacoesTemp.add(t)
                        }
                    }
                    comeco != "" && fim != "" -> {
                        if (dataTransacao >= dataInicio && dataTransacao <= dataFim) {
                            transacoesTemp.add(t)
                        }
                    }
                }
            }
            TransacaoAdapter.transacoes = transacoesTemp
        }

        // Inicializa recycler view com os dados
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        TransacaoActivity.adapter = TransacaoAdapter()
        TransacaoActivity.adapter.notifyAdapter()
        recyclerView.adapter = TransacaoActivity.adapter
    }
}
