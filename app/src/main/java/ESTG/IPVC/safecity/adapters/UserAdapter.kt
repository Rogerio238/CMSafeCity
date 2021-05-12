package ESTG.IPVC.safecity.adapters

import ESTG.IPVC.safecity.R
import ESTG.IPVC.safecity.api.User
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class UserAdapter (val users:List<User>): RecyclerView.Adapter<UserViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType:Int):UserViewHolder{
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recyclerline,parent,false)
        return UserViewHolder(view)
    }
    override fun getItemCount():Int{
        return users.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        return holder.bind(users[position])
    }
}

    class UserViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){

        private val name: TextView =itemView.findViewById(R.id.name)
        private val email: TextView =itemView.findViewById(R.id.email)
        private val city: TextView =itemView.findViewById(R.id.city)
        fun bind(user:User){
            name.text = user.user
            city.text=user.titulo
            email.text=user.descricao
    }
}




