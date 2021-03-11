package ESTG.IPVC.safecity

import ESTG.IPVC.safecity.adapters.NotaAdapter
import ESTG.IPVC.safecity.entities.NotasPessoais
import ESTG.IPVC.safecity.viewModel.NotasViewModel
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class Notas : AppCompatActivity() {
private lateinit var notasViewModel:NotasViewModel
    private val newNotaActivityRequestCode = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notas)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = NotaAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

         notasViewModel = ViewModelProvider(this).get(NotasViewModel::class.java)
        notasViewModel.allNotas.observe(this, Observer { notas ->
            notas?.let {adapter.setNotas(it)}
        })
        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener{
            val intent = Intent(this, AddNota::class.java)
            startActivityForResult(intent,newNotaActivityRequestCode)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == newNotaActivityRequestCode && resultCode == Activity.RESULT_OK){
            data?.getStringExtra(AddNota.EXTRA_REPLY)?.let {
                val nota = NotasPessoais(titulo = it, corpo = "Portugal")
                notasViewModel.insert(nota)
            }
            }
            else{
                Toast.makeText(
                    applicationContext,
                    "Nota  vazia_não inserida",
                    Toast.LENGTH_LONG).show()


            }
        }
    }


