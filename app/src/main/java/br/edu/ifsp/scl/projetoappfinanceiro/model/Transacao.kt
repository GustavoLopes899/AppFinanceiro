package br.edu.ifsp.scl.projetoappfinanceiro.model

data class Transacao(
    var codigo: Int = 0,
    var valor: Double = 0.0,
    var descricao: String = "",
    var natureza: String = "",
    var tipo: String = "",
    var conta: Conta = Conta(),
    var data: String = "",
    var periodos: String = ""
)