package bonch.dev.networking.Networking.adapters

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import bonch.dev.networking.Album
import bonch.dev.networking.Networking.RetrofitFactory

import bonch.dev.networking.R
import kotlinx.android.synthetic.main.activity_albums.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

import retrofit2.HttpException

class AlbumAdapter(var list: List<Album>, val context: Context /*, val listener: (Int) -> Unit*/) :
    RecyclerView.Adapter<AlbumAdapter.ItemPostHolder>() {
    var arList: MutableList<Album> = list.toMutableList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemPostHolder {
        return ItemPostHolder(
            LayoutInflater.from(context).inflate(R.layout.item_album, parent, false)
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
        val post = arList[position]
        holder.bind(post, position/*, listener*/)
    }

    fun getId(post: Album): Int {
        return post.id
    }


    inner class ItemPostHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val idPostTextView = itemView.findViewById<TextView>(R.id.itema_id)
        private val titlePostTextView = itemView.findViewById<TextView>(R.id.itema_title)
        fun bind(post: Album, pos: Int/*, listener: (Int) -> Unit*/) = with(itemView) {
            idPostTextView.text = post.id.toString()
            titlePostTextView.text = post.title
            /*this.setOnClickListener(){
                listener(pos)
            }*/

            itemView.setOnClickListener {
                deleteItem(pos, post)
            }
        }

    }

    @Suppress("DEPRECATION")
    fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }

    fun deleteItem(id: Int, post: Album) {
        if (isOnline(context)) {
            val service = RetrofitFactory.makeRetrofitService()
            CoroutineScope(Dispatchers.Main).launch {
                val responseDel = service.delAlbum(post.id)
                notifyDataSetChanged()
                try {
                    withContext(Dispatchers.Main) {
                        if (responseDel.isSuccessful) {
                            Toast.makeText(
                                context,
                                "post #${post.id} on pos $id is deleted",
                                Toast.LENGTH_SHORT
                            ).show()
                            Log.e("retrofit", "call is successful")
                            //response!!.body()

                            //(rvAlb.adapter as AlbumAdapter).removeItem(pos)
                            arList.removeAt(id)
                            notifyDataSetChanged()
                        } else {
                            Toast.makeText(
                                context,
                                "${responseDel.code()}",
                                Toast.LENGTH_SHORT
                            )
                        }
                    }
                } catch (err: HttpException) {

                }
            }
        } else {
            Toast.makeText(
                context,
                "You cant delete an item, unless you are connected to the Internet",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}