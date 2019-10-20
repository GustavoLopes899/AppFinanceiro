package br.edu.ifsp.scl.projetoappfinanceiro.data

object Constantes {
    const val APP_DB = "app_financeiro"
    const val REQUEST_CODE_ADICIONAR_CONTA = 0
}

object ConstantesConta {
    const val TABELA_CONTA = "conta"
    const val ATRIBUTO_CODIGO = "codigo"
    const val ATRIBUTO_NOME = "nome"
    const val ATRIBUTO_DESCRICAO = "descricao"
    const val ATRIBUTO_SALDO_INICIAL = "saldo_inicial"
    internal const val CREATE_TABLE_CONTA = "CREATE TABLE IF NOT EXISTS $TABELA_CONTA(" +
            "$ATRIBUTO_CODIGO INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
            "$ATRIBUTO_NOME TEXT NOT NULL, " +
            "$ATRIBUTO_DESCRICAO TEXT," +
            "$ATRIBUTO_SALDO_INICIAL REAL NOT NULL);"
}