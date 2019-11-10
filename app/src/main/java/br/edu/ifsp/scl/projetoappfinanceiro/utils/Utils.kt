package br.edu.ifsp.scl.projetoappfinanceiro.utils

import android.app.Dialog
import android.content.Context
import android.widget.TextView
import br.edu.ifsp.scl.projetoappfinanceiro.R
import java.util.*

class Utils {

    companion object {
        fun openDialog(context: Context, mensagem: String) {
            val dialog = Dialog(context)
            dialog.setContentView(R.layout.dialog_info)
            val et: TextView = dialog.findViewById(R.id.dialog_message)
            et.text = mensagem
            dialog.setTitle(R.string.app_name)
            dialog.show()
        }

        fun formataDouble(valor: Double): String {
            return String.format(Locale.ROOT, "%.2f", valor)
        }

    }
}