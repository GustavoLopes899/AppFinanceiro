package br.edu.ifsp.scl.projetoappfinanceiro.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.scl.projetoappfinanceiro.R

class MainActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    override fun onClick(view: View) {
        if (view.id == R.id.verContasbtn) {
            val i = Intent(this, ContaActivity::class.java)
//            i.putExtra("user", "username")
            startActivity(i)
        }
    }

}