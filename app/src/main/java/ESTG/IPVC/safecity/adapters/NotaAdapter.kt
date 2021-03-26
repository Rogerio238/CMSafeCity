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
    context: Context ,
    private val listener: OnItemClickListener

):RecyclerView.Adapter<NotaAdapter.NotaViewHolder>() {

    private val inflater:LayoutInflater= LayoutInflater.from(context)
    public var notas=emptyList<NotasPessoais>()

    inner class NotaViewHolder(itemView: View):RecyclerView.ViewHolder(itemView),View.OnClickListener{
        val notaViewHolder: TextView = itemView.findViewById(R.id.textView)
        val notaViewHolder2: TextView = itemView.findViewById(R.id.textView2)
        init{
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
         val position=adapterPosition
            if(position != RecyclerView.NO_POSITION){
                listener.onItemClick(position)
            }

        }
    }
    fun getpos (int: Int):NotasPessoais{
        return notas[int]
    }
    interface OnItemClickListener{
        fun onItemClick(position: Int)

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotaViewHolder{
        val itemView = inflater.inflate(R.layout.recycleview_item,parent,false)

        return NotaViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: NotaViewHolder, position: Int) {
        val current = notas[position]
        holder.notaViewHolder.text=current.titulo
        holder.notaViewHolder2.text=current.corpo
    }
    internal fun  setNotas(notas:List<NotasPessoais>){
       this.notas = notas
        notifyDataSetChanged()
    }
override fun getItemCount()=notas.size
}