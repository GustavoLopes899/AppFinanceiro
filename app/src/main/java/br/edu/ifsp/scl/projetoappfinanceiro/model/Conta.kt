package br.edu.ifsp.scl.projetoappfinanceiro.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Conta(
    var codigo: Int = 0,
    var nome: String = "",
    var descricao: String = "",
    var saldo: Double = 0.0
) : Parcelable {
    override fun equals(other: Any?): Boolean {
        if (other == null || other !is Conta)
            return false
        return codigo == other.codigo
    }

    override fun hashCode(): Int {
        var result = codigo
        result = 31 * result + nome.hashCode()
        result = 31 * result + descricao.hashCode()
        result = 31 * result + saldo.hashCode()
        return result
    }

    override fun toString(): String {
        return nome
    }
}