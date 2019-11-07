package bonch.dev.networking.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import bonch.dev.networking.Album
import bonch.dev.networking.Networking.RetrofitFactory
import bonch.dev.networking.Networking.adapters.AlbumAdapter
import bonch.dev.networking.Networking.adapters.UserAdapter
import bonch.dev.networking.R
import bonch.dev.networking.User
import kotlinx.android.synthetic.main.activity_albums.*
import kotlinx.android.synthetic.main.activity_users.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response

class AlbumsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_albums)
        val rvAlb = findViewById<RecyclerView>(R.id.rvAlb)
        rvAlb.layoutManager = LinearLayoutManager(this@AlbumsActivity)

        val service = RetrofitFactory.makeRetrofitService()



        CoroutineScope(Dispatchers.IO).launch {
            val response = service.getAlbum()

            try {
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        //initRecyclerView(response.body()!!)
                        var adapter: AlbumAdapter

                        rvAlb.adapter = AlbumAdapter(response.body()!!, this@AlbumsActivity)
                        {
                            //Toast.makeText(this@AlbumsActivity,it.toString(),Toast.LENGTH_SHORT).show()
                            //CoroutineScope(Dispatchers.IO).launch {
                            //Toast.makeText(this@AlbumsActivity,it.toString(),Toast.LENGTH_SHORT).show()
                            //var idText = rvAlb.findViewById<TextView>(R.id.itema_id)
                            //var id =(rvAlb.adapter as AlbumAdapter).getId(response.body()!![it])
                            rvAlb.adapter!!.notifyDataSetChanged()
                            var id = rvAlb.getChildAt(it).findViewById<TextView>(R.id.itema_id)
                                .text.toString().toInt()
                            //var id = idText.text.toString().toInt()
                            CoroutineScope(Dispatchers.Default).launch {
                                val responseDel = service.delAlbum(id)

                                try {
                                    withContext(Dispatchers.Main) {
                                        if (responseDel.isSuccessful) {
                                            Toast.makeText(
                                                this@AlbumsActivity,
                                                "post #$id on pos $it is deleted",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                            Log.e("retrofit", "call is successful")
                                            //response!!.body()

                                            (rvAlb.adapter as AlbumAdapter).removeItem(it)
                                            rvAlb.adapter!!.notifyDataSetChanged()
                                        } else {
                                            Toast.makeText(
                                                this@AlbumsActivity,
                                                "${responseDel.code()}",
                                                Toast.LENGTH_SHORT
                                            )
                                        }
                                    }
                                } catch (err: HttpException) {

                                }
                            }


                            /*response.enqueue(object : Callback<ResponseBody>{
                                override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                                    Log.e("retrofit", "call failed")
                                }

                                override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
                                    Toast.makeText(this@AlbumsActivity,"post #$id on pos $it is deleted",Toast.LENGTH_SHORT).show()
                                    Log.e("retrofit", "call is successful")
                                    //response!!.body()

                                    (rvAlb.adapter as AlbumAdapter).removeItem(it)
                                    rvAlb.adapter!!.notifyDataSetChanged()
                                }
                            })*/
                            //}
                        }

                    } else {
                        Toast.makeText(
                            this@AlbumsActivity,
                            "${response.code()}",
                            Toast.LENGTH_SHORT
                        )
                    }
                }

            } catch (err: HttpException) {

            }
        }

    }

    /*private fun initRecyclerView(list: List<Album>) {

        rvAlb.adapter = AlbumAdapter(list, this)
    }*/
}
