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
import bonch.dev.Post

class PostAdapter(val list: List<Post>,val context:Context):
    RecyclerView.Adapter<PostAdapter.ItemPostHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemPostHolder {
        return ItemPostHolder(
        LayoutInflater.from(context).inflate(R.layout.item_post,parent,false)
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
        private val titlePostTextView=itemView.findViewById<TextView>(R.id.item_title)
        private val bodyPostTextView=itemView.findViewById<TextView>(R.id.item_body)
        fun bind(post:Post){
            titlePostTextView.text=post.title
            bodyPostTextView.text=post.body
        }
    }
}