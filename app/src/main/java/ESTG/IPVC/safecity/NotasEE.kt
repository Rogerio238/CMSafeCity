package ESTG.IPVC.safecity

import ESTG.IPVC.safecity.viewModel.NotasViewModel
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_notas_e_e.*

class NotasEE : AppCompatActivity() {
    private lateinit var notasViewModel: NotasViewModel
    private lateinit var tituloView: EditText
    private lateinit var corpoView: EditText

    var id:Int=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notas_e_e)


        tituloView = findViewById(R.id.notatitulo)
        corpoView = findViewById(R.id.notatitulo2)
        val delete = findViewById<Button>(R.id.eliminar)
        val update = findViewById<Button>(R.id.update)
        var titulo = "Nota não selecionada"
        var corpo = "Nota não selecionada"
        var extras = getIntent().getExtras();

        notasViewModel = ViewModelProvider(this).get(NotasViewModel::class.java)
        if (extras != null) {
            id=extras.getInt("Id")
            titulo = extras.getString("Titulo").toString()
            corpo = extras.getString("Corpo").toString()
             tituloView.setText(titulo)
            corpoView.setText(corpo)

        }
        fun onClickEditar(){
            if(TextUtils.isEmpty(tituloView.text)&&TextUtils.isEmpty(corpoView.text)){

                val intent = Intent(this,Notas::class.java)
                startActivity(intent)
            }else{

                notasViewModel.update(titulo = tituloView.text.toString(),corpo = corpoView.text.toString(),id = id)
              finish()
            }
        }
        delete.setOnClickListener{

            notasViewModel.deleteNotaById(id)
            val intent = Intent(this, Notas::class.java)
            startActivity(intent)
        }
        update.setOnClickListener{
           onClickEditar()
        }
    }
}