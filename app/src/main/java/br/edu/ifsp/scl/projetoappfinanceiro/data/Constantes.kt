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

// Constantes de transacoes
object ConstantesTransacoes {
    const val TABELA_TRANSACAO = "transacao"
    const val ATRIBUTO_CODIGO = "codigo"
    const val ATRIBUTO_VALOR = "valor"
    const val ATRIBUTO_DESCRICAO = "descricao"
    const val ATRIBUTO_NATUREZA = "natureza"
    const val ATRIBUTO_TIPO = "tipo"
    const val ATRIBUTO_CODIGO_CONTA = "codigo_conta"
    const val ATRIBUTO_DATA = "data"
    const val ATRIBUTO_PERIODOS = "periodos"
    internal const val CREATE_TABLE_TRANSACAO = "CREATE TABLE IF NOT EXISTS $TABELA_TRANSACAO(" +
            "$ATRIBUTO_CODIGO INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
            "$ATRIBUTO_VALOR REAL NOT NULL, " +
            "$ATRIBUTO_DESCRICAO TEXT, " +
            "$ATRIBUTO_NATUREZA TEXT NOT NULL, " +
            "$ATRIBUTO_TIPO TEXT NOT NULL, " +
            "$ATRIBUTO_CODIGO_CONTA INTEGER NOT NULL, " +
            "$ATRIBUTO_DATA TEXT NOT NULL, " +
            "$ATRIBUTO_PERIODOS TEXT, " +
            "FOREIGN KEY($ATRIBUTO_CODIGO_CONTA) REFERENCES ${ConstantesConta.TABELA_CONTA}(${ConstantesConta.ATRIBUTO_CODIGO}));"

}