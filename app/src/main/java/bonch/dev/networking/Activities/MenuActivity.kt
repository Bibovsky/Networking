package bonch.dev.networking.Activities

import android.app.Application
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.widget.Button
import android.widget.Toast
import bonch.dev.networking.R
import bonch.dev.networking.Activities.PhotoActivity

class MenuActivity : AppCompatActivity() {
    private lateinit var instance:Context
    private lateinit var btnUsers: Button
    private lateinit var btnMain: Button
    private lateinit var btnPhoto: Button
    private lateinit var btnAlbums: Button
    private lateinit var btnPostCreateDialog: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        initializeViews()
        setListeners()

    }

    fun initializeViews(){
        instance=this
        btnMain=findViewById(R.id.btnMain)
        btnUsers=findViewById(R.id.btnUsers)
        btnPhoto=findViewById(R.id.btnPhoto)
        btnAlbums=findViewById(R.id.btnAlbums)
        btnPostCreateDialog=findViewById(R.id.btnPostCreateDialog)
    }
    fun setListeners(){
        btnUsers.setOnClickListener {
            val intent = Intent(this,UsersActivity::class.java)
            startActivity(intent)
        }

        btnPhoto.setOnClickListener {
            val intent = Intent(this, PhotoActivity::class.java)
            startActivity(intent)
        }
        btnAlbums.setOnClickListener {
            val intent = Intent(this,AlbumsActivity::class.java)
            startActivity(intent)
        }
        btnPostCreateDialog.setOnClickListener {
            val a:Int=0
        }
        btnMain.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
        btnPostCreateDialog.setOnClickListener(){
            val dialog = PostCreateDialogFragment(applicationContext)

            dialog.show(supportFragmentManager,"dialog")
        }
    }
}
