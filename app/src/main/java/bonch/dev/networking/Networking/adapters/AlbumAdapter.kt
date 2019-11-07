package bonch.dev.networking.Networking.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import bonch.dev.networking.Album

import bonch.dev.networking.R

class AlbumAdapter(var list: List<Album>, val context:Context,val listener: (Int) -> Unit):
    RecyclerView.Adapter<AlbumAdapter.ItemPostHolder>() {
    var arList:MutableList<Album> =list.toMutableList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemPostHolder {
        return ItemPostHolder(
            LayoutInflater.from(context).inflate(R.layout.item_album,parent,false)
        )

    }
    fun removeItem(position: Int) {
        arList.removeAt(position)
        notifyDataSetChanged()
    }
    override fun getItemCount(): Int {
        return arList.size
    }

    override fun onBindViewHolder(holder: ItemPostHolder, position: Int) {
        val post=arList[position]
        holder.bind(post,position,listener)
    }

    fun getId(post: Album):Int{
        return post.id
    }
    class ItemPostHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        private val idPostTextView=itemView.findViewById<TextView>(R.id.itema_id)
        private val titlePostTextView=itemView.findViewById<TextView>(R.id.itema_title)
        fun bind(post: Album,pos:Int, listener: (Int) -> Unit)= with(itemView){
            idPostTextView.text=post.id.toString()
            titlePostTextView.text=post.title
            this.setOnClickListener(){
                listener(pos)

            }
        }

    }
}