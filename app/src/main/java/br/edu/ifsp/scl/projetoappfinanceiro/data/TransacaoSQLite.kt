package br.edu.ifsp.scl.projetoappfinanceiro.data

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import br.edu.ifsp.scl.projetoappfinanceiro.R
import br.edu.ifsp.scl.projetoappfinanceiro.data.ConstantesTransacoes.ATRIBUTO_CODIGO
import br.edu.ifsp.scl.projetoappfinanceiro.data.ConstantesTransacoes.ATRIBUTO_CODIGO_CONTA
import br.edu.ifsp.scl.projetoappfinanceiro.data.ConstantesTransacoes.ATRIBUTO_DATA
import br.edu.ifsp.scl.projetoappfinanceiro.data.ConstantesTransacoes.ATRIBUTO_DESCRICAO
import br.edu.ifsp.scl.projetoappfinanceiro.data.ConstantesTransacoes.ATRIBUTO_NATUREZA
import br.edu.ifsp.scl.projetoappfinanceiro.data.ConstantesTransacoes.ATRIBUTO_PERIODOS
import br.edu.ifsp.scl.projetoappfinanceiro.data.ConstantesTransacoes.ATRIBUTO_TIPO
import br.edu.ifsp.scl.projetoappfinanceiro.data.ConstantesTransacoes.ATRIBUTO_VALOR
import br.edu.ifsp.scl.projetoappfinanceiro.data.ConstantesTransacoes.TABELA_TRANSACAO
import br.edu.ifsp.scl.projetoappfinanceiro.model.Transacao

class TransacaoSQLite(contexto: Context): TransacaoDao {
    // Referência para o Banco de Dados do aplicativo
    private val sqlDb: SQLiteDatabase = contexto.openOrCreateDatabase(Constantes.APP_DB, Context.MODE_PRIVATE, null)

    init {
        try {
            sqlDb.execSQL(ConstantesTransacoes.CREATE_TABLE_TRANSACAO)
        } catch (e: SQLException) {
            Log.e(
                contexto.getString(R.string.app_name),
                "Erro na criação da tabela de transacao!"
            )
        }
    }

    override fun createTransacao(transacao: Transacao) {
        val atributos = ContentValues()
        // Adicionando atributos de transacoes
        atributos.put(ATRIBUTO_VALOR, transacao.valor)
        atributos.put(ATRIBUTO_DESCRICAO, transacao.descricao)
        atributos.put(ATRIBUTO_NATUREZA, transacao.natureza)
        atributos.put(ATRIBUTO_TIPO, transacao.tipo)
        atributos.put(ATRIBUTO_CODIGO_CONTA, transacao.conta)
        atributos.put(ATRIBUTO_DATA, transacao.data)
        atributos.put(ATRIBUTO_PERIODOS, transacao.periodos)

        // Executando insert de transacao
        sqlDb.insert(TABELA_TRANSACAO, null, atributos)
    }

    // Retorna transacao de um codigo
    override fun readTransacao(codigo: Int): Transacao {
        // Consulta usando a função query
        val transacaoCursor = sqlDb.query(
            true,
            TABELA_TRANSACAO,
            null,
            "$ATRIBUTO_CODIGO = ?",
            arrayOf("$codigo"),
            null, null,
            null,
            null
        )

        // Retorna a transacao encontrada ou uma transacao vazia
        return if (transacaoCursor.moveToFirst())
            linhaCursorParaTransacao(transacaoCursor)
        else
            Transacao()
    }

    // Converte uma linha do Cursor para uma objeto de Transacao
    private fun linhaCursorParaTransacao(cursor: Cursor): Transacao {
        return Transacao(
            cursor.getInt(cursor.getColumnIndex(ATRIBUTO_CODIGO)),
            cursor.getDouble(cursor.getColumnIndex(ATRIBUTO_VALOR)),
            cursor.getString(cursor.getColumnIndex(ATRIBUTO_DESCRICAO)),
            cursor.getString(cursor.getColumnIndex(ATRIBUTO_NATUREZA)),
            cursor.getString(cursor.getColumnIndex(ATRIBUTO_TIPO)),
            cursor.getInt(cursor.getColumnIndex(ATRIBUTO_CODIGO_CONTA)),
            cursor.getString(cursor.getColumnIndex(ATRIBUTO_DATA)),
            cursor.getString(cursor.getColumnIndex(ATRIBUTO_PERIODOS))
        )
    }

    // Retorna todas transacoes
    override fun readTransacoes(where: String): ArrayList<Transacao> {
        val listaTransacao = arrayListOf<Transacao>()
        // Consulta usando função rawQuery
        val transacaoStm: String = if (where == "") {
            "SELECT * FROM $TABELA_TRANSACAO;"
        } else {
            "SELECT * FROM $TABELA_TRANSACAO WHERE $where"
        }
        val transacaoCursor = sqlDb.rawQuery(transacaoStm, null)
        while (transacaoCursor.moveToNext()) {
            listaTransacao.add(linhaCursorParaTransacao(transacaoCursor))
        }
        return listaTransacao
    }

    // Atualiza dados de uma transacao
    override fun updateTransacao(transacao: Transacao) {
        val atributos = ContentValues()
        // Adicionando atributos de transacoes
        atributos.put(ATRIBUTO_VALOR, transacao.valor)
        atributos.put(ATRIBUTO_DESCRICAO, transacao.descricao)
        atributos.put(ATRIBUTO_NATUREZA, transacao.natureza)
        atributos.put(ATRIBUTO_TIPO, transacao.tipo)
        atributos.put(ATRIBUTO_CODIGO_CONTA, transacao.conta)
        atributos.put(ATRIBUTO_DATA, transacao.data)
        atributos.put(ATRIBUTO_PERIODOS, transacao.periodos)

        // Executando update
        sqlDb.update( TABELA_TRANSACAO, atributos, "$ATRIBUTO_CODIGO = ?", arrayOf(transacao.codigo.toString()))
    }

    // Deleta uma Transacaopelo codigo dado
    override fun deleteTransacao(codigo: Int) {
        sqlDb.delete(TABELA_TRANSACAO, "$ATRIBUTO_CODIGO = ?", arrayOf(codigo.toString()))
    }
}