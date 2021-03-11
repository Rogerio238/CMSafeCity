package ESTG.IPVC.safecity.adapters

import ESTG.IPVC.safecity.R
import ESTG.IPVC.safecity.entities.NotasPessoais
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_notas.view.*
import kotlinx.android.synthetic.main.recycleview_item.view.*

class NotaAdapter internal constructor(
    context: Context
):RecyclerView.Adapter<NotaAdapter.NotaViewHolder>() {

    private val inflater:LayoutInflater= LayoutInflater.from(context)
    private var notas=emptyList<NotasPessoais>()

    class NotaViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val notaViewHolder: TextView = itemView.findViewById(R.id.textView)

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotaViewHolder{
        val itemView = inflater.inflate(R.layout.recycleview_item,parent,false)
        return NotaViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: NotaViewHolder, position: Int) {
        val current = notas[position]
        holder.notaViewHolder.text=current.titulo

    }
    internal fun  setNotas(notas:List<NotasPessoais>){
       this.notas = notas
        notifyDataSetChanged()
    }
override fun getItemCount()=notas.size
}