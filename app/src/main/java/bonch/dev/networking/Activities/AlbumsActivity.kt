package bonch.dev.networking.Activities

import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Adapter
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import bonch.dev.networking.Album
import bonch.dev.networking.Networking.RetrofitFactory
import bonch.dev.networking.Networking.adapters.AlbumAdapter
import bonch.dev.networking.Networking.adapters.UserAdapter
import bonch.dev.networking.R
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.*


import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response

class AlbumsActivity : AppCompatActivity() {
    lateinit var editor: SharedPreferences.Editor
    private val APP_PREFERENCES_ALBUMS = "Album Info"
    lateinit var pref: SharedPreferences
    lateinit var gson: Gson
    lateinit var list: List<Album>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_albums)
        val rvAlb = findViewById<RecyclerView>(R.id.rvAlb)
        rvAlb.layoutManager = LinearLayoutManager(this@AlbumsActivity)
        //var adapter = AlbumAdapter(list,applicationContext)
        //rvAlb.adapter = adapter


        pref = getPreferences(MODE_PRIVATE)
        gson = Gson()

        if (isOnline(applicationContext)) {
            val service = RetrofitFactory.makeRetrofitService()
            CoroutineScope(Dispatchers.IO).launch {
                val response = service.getAlbum()

                try {
                    withContext(Dispatchers.Main) {
                        if (response.isSuccessful) {
                            //initRecyclerView(response.body()!!)
                            var adapter: AlbumAdapter
                            list = response.body()!!
                            rvAlb.adapter = AlbumAdapter(list, this@AlbumsActivity)
                            /*{
                                //Toast.makeText(this@AlbumsActivity,it.toString(),Toast.LENGTH_SHORT).show()
                                //CoroutineScope(Dispatchers.IO).launch {
                                //Toast.makeText(this@AlbumsActivity,it.toString(),Toast.LENGTH_SHORT).show()
                                //var idText = rvAlb.findViewById<TextView>(R.id.itema_id)
                                //var id =(rvAlb.adapter as AlbumAdapter).getId(response.body()!![it])
                                rvAlb.adapter!!.notifyDataSetChanged()

                                //var id = rvAlb.findViewHolderForLayoutPosition(it)!!.itemView.findViewById<TextView>(R.id.itema_id).text.toString().toInt()

                                //var id = idText.text.toString().toInt()
                                *//*CoroutineScope(Dispatchers.Default).launch {
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
                            }*//*


                            *//*response.enqueue(object : Callback<ResponseBody>{
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
                            })*//*
                            //}
                        }*/

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
        } else {
            if (pref.contains(APP_PREFERENCES_ALBUMS)) {
                var json: String? = pref.getString(APP_PREFERENCES_ALBUMS, "")
                val caseType = object : TypeToken<List<Album>>() {}.type
                list = gson.fromJson(json, caseType)
                rvAlb.adapter = AlbumAdapter(list, this@AlbumsActivity)
            }
        }

    }

    override fun onPause() {

        super.onPause()
        editor = pref.edit()
        editor.putString(APP_PREFERENCES_ALBUMS, gson.toJson(list))
        editor.apply()
    }

    @Suppress("DEPRECATION")
    fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
    /*private fun initRecyclerView(list: List<Album>) {

        rvAlb.adapter = AlbumAdapter(list, this)
    }*/
}
