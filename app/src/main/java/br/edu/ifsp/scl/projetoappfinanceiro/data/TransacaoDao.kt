package br.edu.ifsp.scl.projetoappfinanceiro.data

import br.edu.ifsp.scl.projetoappfinanceiro.model.Transacao

interface TransacaoDao {
    fun createTransacao(transacao: Transacao)
    fun readTransacao(codigo: Int): Transacao
    fun readTransacoes(): MutableList<Transacao>
    fun updateTransacao(transacao: Transacao)
    fun deleteTransacao(codigo: Int)
}