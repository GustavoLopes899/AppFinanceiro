package br.edu.ifsp.scl.projetoappfinanceiro.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Transacao(
    var codigo: Int = 0,
    var valor: Double = 0.0,
    var descricao: String = "",
    var natureza: String = "",
    var tipo: String = "",
    var conta: Int = 0,
    var data: String = "",
    var periodos: String = ""
) : Parcelable {
    override fun equals(other: Any?): Boolean {
        if (other == null || other !is Transacao)
            return false
        return codigo == other.codigo
    }

    override fun hashCode(): Int {
        var result = codigo
        result = 31 * result + valor.hashCode()
        result = 31 * result + descricao.hashCode()
        result = 31 * result + natureza.hashCode()
        result = 31 * result + tipo.hashCode()
        result = 31 * result + conta.hashCode()
        result = 31 * result + data.hashCode()
        result = 31 * result + periodos.hashCode()
        return result
    }
}