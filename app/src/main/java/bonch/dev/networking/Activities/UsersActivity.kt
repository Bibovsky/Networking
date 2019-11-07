package bonch.dev.networking.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import bonch.dev.networking.Networking.RetrofitFactory
import bonch.dev.networking.Networking.adapters.PostAdapter
import bonch.dev.networking.Networking.adapters.UserAdapter
import bonch.dev.Post
import bonch.dev.networking.R
import bonch.dev.networking.User
import kotlinx.android.synthetic.main.activity_users.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class UsersActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users)
        val rvUsr = findViewById<RecyclerView>(R.id.rvUsr)
        rvUsr.layoutManager = LinearLayoutManager(this@UsersActivity)

        val service = RetrofitFactory.makeRetrofitService()

        CoroutineScope(Dispatchers.IO).launch {
            val response = service.getUsers()

            try {
                withContext(Dispatchers.Main){
                    if(response.isSuccessful){
                        initRecyclerView(response.body()!!)
                        rvUsr.adapter= UserAdapter(response.body()!!,this@UsersActivity)
                    }else{

                        Toast.makeText(this@UsersActivity,"${response.code()}", Toast.LENGTH_SHORT)
                    }
                }

            }catch (err: HttpException){

            }
        }

    }
    private fun initRecyclerView(list:List<User>){
        rvUsr.adapter=UserAdapter(list,this)
    }
}
