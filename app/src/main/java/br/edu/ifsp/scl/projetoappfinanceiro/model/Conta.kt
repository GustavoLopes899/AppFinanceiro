package br.edu.ifsp.scl.projetoappfinanceiro.model

data class Conta(
    var codigo: Int = 0,
    var nome: String = "",
    var descricao: String = "",
    var saldoInicial: Double = 0.0
)