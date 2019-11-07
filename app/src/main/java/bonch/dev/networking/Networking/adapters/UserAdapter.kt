package bonch.dev.networking.Networking.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView
import androidx.recyclerview.widget.RecyclerView

import bonch.dev.networking.R
import bonch.dev.networking.User

class UserAdapter(val list: List<User>, val context:Context):
    RecyclerView.Adapter<UserAdapter.ItemPostHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemPostHolder {
        return ItemPostHolder(
        LayoutInflater.from(context).inflate(R.layout.item_user,parent,false)
        )

    }


    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ItemPostHolder, position: Int) {
        val post=list[position]
        holder.bind(post)
    }
    class ItemPostHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        private val namePostTextView=itemView.findViewById<TextView>(R.id.item_name)
        private val websitePostTextView=itemView.findViewById<TextView>(R.id.item_website)
        fun bind(post:User){
            namePostTextView.text=post.name
            websitePostTextView.text=post.website
        }
    }
}