package bonch.dev.networking.Activities

import android.content.Context
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import bonch.dev.networking.Networking.RetrofitFactory
import bonch.dev.networking.Networking.adapters.PhotosAdapter
import bonch.dev.networking.Photos
import bonch.dev.networking.R
import io.realm.Realm
import io.realm.RealmConfiguration
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class PhotoActivity : AppCompatActivity() {
    lateinit var realm: Realm
    lateinit var list: List<Photos>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo)
        val rvPht = findViewById<RecyclerView>(R.id.rvPht)
        rvPht.layoutManager = LinearLayoutManager(this)
        val service = RetrofitFactory.makeRetrofitService()

        Realm.init(applicationContext)
        val config = RealmConfiguration.Builder()
            .name("PhotosDB.realm")
            .build()
        realm = Realm.getInstance(config)
        if (isOnline(applicationContext)) {
            val service = RetrofitFactory.makeRetrofitService()
            CoroutineScope(Dispatchers.IO).launch {
                val response = service.TransferToPhotosActivity()
                try {
                    withContext(Dispatchers.Main) {
                        if (response.isSuccessful) {
                            list = response.body()!!
                            saveData(list)
                            rvPht.adapter = PhotosAdapter(list, this@PhotoActivity)
                        } else {
                            Toast.makeText(applicationContext, "${response.code()}", Toast.LENGTH_SHORT).show()
                        }
                    }

                } catch (err: HttpException) {
                    Log.e("Retrofit", "${err.printStackTrace()}")
                }
            }
        } else {
            list = getData()
            rvPht.adapter=PhotosAdapter(list,this@PhotoActivity)
        }
    }

    fun getData(): List<Photos> {
        var listd: ArrayList<Photos> = arrayListOf()

        var realmResult = realm.where(Photos::class.java).findAll()
        if (realmResult != null) {
            for (i in realmResult.indices) {
                val temptData = Photos(realmResult[i]!!.id, realmResult[i]!!.albumId!!, realmResult[i]!!.url!!)
                listd.add(temptData)
            }
        } else {
            Toast.makeText(applicationContext, "You have no Internet connection", Toast.LENGTH_SHORT).show()
        }
        return listd
    }

    fun saveData(list: List<Photos>) {
        val arrList: ArrayList<Photos> = arrayListOf()
        if (list.isNotEmpty()) {
            list.forEach {
                val photo = Photos()
                photo.id = it.id
                photo.albumId = it.albumId
                photo.url = it.url
                //photo.img = Glide.with(this).load(it.url)
                arrList.add(photo)
            }
//одним траншем пишем в базу
            realm.executeTransactionAsync({ bgRealm ->
                bgRealm.insertOrUpdate(arrList) //сохраняем первый раз или обновляем уже имеющееся
            }, {
                Toast.makeText(applicationContext, "Success write", Toast.LENGTH_SHORT).show()
            }, {
                Toast.makeText(applicationContext, "Fail write", Toast.LENGTH_SHORT).show()
            })
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }

    @Suppress("DEPRECATION")
    fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
}
