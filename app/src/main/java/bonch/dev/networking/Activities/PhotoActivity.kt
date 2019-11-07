package bonch.dev.networking.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import bonch.dev.networking.Networking.RetrofitFactory
import bonch.dev.networking.Networking.adapters.PhotosAdapter
import bonch.dev.networking.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class PhotoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo)
        val rvPht=findViewById<RecyclerView>(R.id.rvPht)
        rvPht.layoutManager= LinearLayoutManager(this)
        val service= RetrofitFactory.makeRetrofitService()
        CoroutineScope(Dispatchers.IO).launch {


            val response = service.TransferToPhotosActivity()
            try {
                withContext(Dispatchers.Main){
                    if (response.isSuccessful){
                        rvPht.adapter= PhotosAdapter(response.body()!!,this@PhotoActivity)
                    }
                    else{
                        Toast.makeText(applicationContext,"${response.code()}", Toast.LENGTH_SHORT).show()
                    }
                }

            }catch (err: HttpException){
                Log.e("Retrofit","${err.printStackTrace()}")
            }
        }
    }
}
