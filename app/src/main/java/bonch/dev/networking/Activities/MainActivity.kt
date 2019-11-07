package bonch.dev.networking.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import bonch.dev.networking.Networking.RetrofitFactory
import bonch.dev.networking.Networking.adapters.PostAdapter
import bonch.dev.Post
import bonch.dev.networking.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import retrofit2.HttpException

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val rv = findViewById<RecyclerView>(R.id.rv)
        rv.layoutManager = LinearLayoutManager(this)

        val service = RetrofitFactory.makeRetrofitService()

        CoroutineScope(Dispatchers.IO).launch {
            val response = service.getPosts()

            try {
                withContext(Dispatchers.Main){
                    if(response.isSuccessful){
                        //initRecyclerView(response.body()!!)
                        rv.adapter=PostAdapter(response.body()!!,this@MainActivity)
                    }else{
                        Toast.makeText(this@MainActivity,"${response.code()}",Toast.LENGTH_SHORT)
                    }
                }

            }catch (err:HttpException){

            }
        }
        /*val job:Job = GlobalScope.launch (Dispatchers.IO){
            delay(2000)
            println("Hi")
        }
        job.onJoin

        GlobalScope.launch(Dispatchers.Main) {
            val working = async (Dispatchers.IO){
                someData(10)
            }
            val data = working.await()
            println(data)
        }
        print("second")

        val newThread = NewThread(applicationContext)
        newThread.start()



        thread (start=true){
            println("dadada")
        }*/
    }
    private fun initRecyclerView(list:List<Post>){
        rv.adapter=PostAdapter(list,this)
    }
}

/*suspend fun someData(a:Int):Int{
    delay(1000)
    return a+10
}

class NewThread(val cont: Context):Thread(){
    override fun run() {
        Toast.makeText(cont,"Slova",Toast.LENGTH_SHORT)
    }
}*/