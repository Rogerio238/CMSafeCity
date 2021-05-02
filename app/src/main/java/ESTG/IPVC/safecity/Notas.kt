package ESTG.IPVC.safecity

import ESTG.IPVC.safecity.AddNota.Companion.EXTRA_REPLY_CORPO
import ESTG.IPVC.safecity.AddNota.Companion.EXTRA_REPLY_TITULO
import ESTG.IPVC.safecity.adapters.NotaAdapter
import ESTG.IPVC.safecity.entities.NotasPessoais
import ESTG.IPVC.safecity.viewModel.NotasViewModel
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.get
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_notas.*
import kotlinx.android.synthetic.main.activity_notas_e_e.*
import kotlinx.android.synthetic.main.recycleview_item.*

class Notas : AppCompatActivity(),NotaAdapter.OnItemClickListener {

private lateinit var notasViewModel:NotasViewModel
    private lateinit var adapter: NotaAdapter
    private val newNotaActivityRequestCode = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notas)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        adapter = NotaAdapter(this,this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(applicationContext,2)

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
    override fun onItemClick(position: Int) {

    val intent=Intent(this,NotasEE::class.java)
        intent.putExtra("Id",adapter.getpos(position).id)
        intent.putExtra("Titulo",adapter.getpos(position).titulo)
        intent.putExtra("Corpo",adapter.getpos(position).corpo)
        startActivityForResult(intent,newNotaActivityRequestCode)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == newNotaActivityRequestCode && resultCode == Activity.RESULT_OK){

            val ptitulo  =data?.getStringExtra(AddNota.EXTRA_REPLY_TITULO)
            val pcorpo = data?.getStringExtra(AddNota.EXTRA_REPLY_CORPO)



            if (ptitulo!= null && pcorpo != null) {
                val nota= NotasPessoais(titulo = ptitulo, corpo = pcorpo)
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


