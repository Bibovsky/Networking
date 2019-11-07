package bonch.dev.networking.Activities

/*import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import bonch.dev.networking.Networking.RetrofitFactory
import bonch.dev.networking.R
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import bonch.dev.Post
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.lang.Exception*/

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import bonch.dev.Post
import bonch.dev.networking.Networking.RetrofitFactory
import bonch.dev.networking.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.lang.Exception

class PostCreateDialogFragment(cont: Context) : DialogFragment() {
    val act=cont
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        val inflater = activity?.layoutInflater?.inflate(R.layout.fragment_dialog, null)
        val TitleTextView = view?.findViewById<TextView>(R.id.title_et)
        val BodyTextView = view?.findViewById<TextView>(R.id.body_et)


        builder.setPositiveButton("Send") { dialog, which ->

            val a = TitleTextView?.text.toString()
            val b = BodyTextView?.text.toString()
            val service = RetrofitFactory.makeRetrofitService()
            CoroutineScope(Dispatchers.IO).launch {
                val response = service.TransferToFragment(a, b)
                try {
                    withContext(Dispatchers.Main) {

                        //try {
                            if (response.isSuccessful) {
                                Log.e("PostRequest", "Success : ${response.code()}")
                                Toast.makeText(act, "Success : ${response.code()}",Toast.LENGTH_SHORT).show()

                            } else {
                                Log.e("PostRequest", "Fail : ${response.code()}")
                            }

//                        } catch (err: Exception) {
//                            Log.e("PostRequest", "Something went wrong : ${err.printStackTrace()}")
//
//                        }
                    }
                } catch (err: HttpException) {
                    Log.e("Retrofit", "${err.printStackTrace()}")
                }
            }
        }
        builder.setView(inflater)
        return builder.create()
    }
}