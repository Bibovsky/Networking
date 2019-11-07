package bonch.dev.networking.Networking.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import bonch.dev.networking.Photos
import bonch.dev.networking.R
import com.bumptech.glide.Glide

class PhotosAdapter(val list: List<Photos>, val context: Context):
    RecyclerView.Adapter<PhotosAdapter.PhotosHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotosHolder {
        return PhotosHolder(LayoutInflater.from(context).inflate(R.layout.item_photo,parent,false))
    }
    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: PhotosHolder, position: Int) {
        val photo=list[position]
        holder.bind(photo,position)
    }

    inner class PhotosHolder(itemview:View):RecyclerView.ViewHolder(itemview){
        private val imageView=itemView.findViewById<ImageView>(R.id.itemp_photo)
        private val PhotosTextView=itemView.findViewById<TextView>(R.id.itemp_id)
        fun bind(photos: Photos,position: Int){
            PhotosTextView.text="image #${position+1}"
            Glide.with(context).load(photos.url).into(imageView)
        }
    }
}