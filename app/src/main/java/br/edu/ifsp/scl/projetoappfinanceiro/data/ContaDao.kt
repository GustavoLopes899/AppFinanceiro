package br.edu.ifsp.scl.projetoappfinanceiro.data

import br.edu.ifsp.scl.projetoappfinanceiro.model.Conta

interface ContaDao {
    fun createConta(conta: Conta)
    fun readConta(codigo: Int): Conta
    fun readContas(): MutableList<Conta>
    fun updateConta(conta: Conta)
    fun deleteConta(codigo: Int)
}