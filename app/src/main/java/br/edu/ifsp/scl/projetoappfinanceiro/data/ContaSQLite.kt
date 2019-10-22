package br.edu.ifsp.scl.projetoappfinanceiro.data

import android.content.ContentValues
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import br.edu.ifsp.scl.projetoappfinanceiro.R
import br.edu.ifsp.scl.projetoappfinanceiro.data.ConstantesConta.ATRIBUTO_CODIGO
import br.edu.ifsp.scl.projetoappfinanceiro.data.ConstantesConta.ATRIBUTO_DESCRICAO
import br.edu.ifsp.scl.projetoappfinanceiro.data.ConstantesConta.ATRIBUTO_NOME
import br.edu.ifsp.scl.projetoappfinanceiro.data.ConstantesConta.ATRIBUTO_SALDO
import br.edu.ifsp.scl.projetoappfinanceiro.data.ConstantesConta.TABELA_CONTA
import br.edu.ifsp.scl.projetoappfinanceiro.model.Conta

class ContaSQLite(contexto: Context) : ContaDao {
    // Referência para o Banco de Dados do aplicativo
    private val sqlDb: SQLiteDatabase =
        contexto.openOrCreateDatabase(Constantes.APP_DB, MODE_PRIVATE, null)

    init {
        try {
            sqlDb.execSQL(ConstantesConta.CREATE_TABLE_CONTA)
        } catch (e: SQLException) {
            Log.e(
                contexto.getString(R.string.app_name),
                "Erro na criação da tabela de conta!"
            )
        }
    }

    override fun createConta(conta: Conta) {
        val atributos = ContentValues()
        atributos.put(ATRIBUTO_NOME, conta.nome)
        atributos.put(ATRIBUTO_DESCRICAO, conta.descricao)
        atributos.put(ATRIBUTO_SALDO, conta.saldo)

        // Executando insert
        sqlDb.insert(TABELA_CONTA, null, atributos)
    }

    override fun readConta(codigo: Int): Conta {
        // Consulta usando a função query
        val contaCursor = sqlDb.query(
            true,
            TABELA_CONTA,
            null,
            "$ATRIBUTO_CODIGO = ?",
            arrayOf("$codigo"),
            null, null,
            null,
            null
        )

        // Retorna a disciplina encontrada ou uma disciplina vazia
        return if (contaCursor.moveToFirst())
            linhaCursorParaConta(contaCursor)
        else
            Conta()
    }

    // Converte uma linha do Cursor para uma objeto de Conta
    private fun linhaCursorParaConta(cursor: Cursor): Conta {
        return Conta(
            cursor.getInt(
                cursor.getColumnIndex(ATRIBUTO_CODIGO)
            ),
            cursor.getString(cursor.getColumnIndex(ATRIBUTO_NOME)),
            cursor.getString(cursor.getColumnIndex(ATRIBUTO_DESCRICAO)),
            cursor.getDouble(cursor.getColumnIndex(ATRIBUTO_SALDO))
        )
    }

    override fun readContas(): ArrayList<Conta> {
        val listaContas = arrayListOf<Conta>()
        // Consulta usando função rawQuery
        val disciplinasStm = "SELECT * FROM $TABELA_CONTA;"
        val disciplinasCursor = sqlDb.rawQuery(disciplinasStm, null)
        while (disciplinasCursor.moveToNext()) {
            listaContas.add(linhaCursorParaConta(disciplinasCursor))
        }
        return listaContas
    }

    override fun updateConta(conta: Conta) {
        val atributos = ContentValues()
        atributos.put(ATRIBUTO_NOME, conta.nome)
        atributos.put(ATRIBUTO_DESCRICAO, conta.descricao)
        atributos.put(ATRIBUTO_SALDO, conta.saldo)
        // Executando update
        sqlDb.update(
            TABELA_CONTA,                       // Tabela
            atributos,                          // Mapeamento atributo-valor
            "$ATRIBUTO_CODIGO = ?",             // Predicado WHERE
            arrayOf(conta.codigo.toString())    // Valor do ? no predicado WHERE
        )
    }

    override fun deleteConta(codigo: Int) {
        sqlDb.delete(TABELA_CONTA, "$ATRIBUTO_CODIGO = ?", arrayOf(codigo.toString()))
    }
}
