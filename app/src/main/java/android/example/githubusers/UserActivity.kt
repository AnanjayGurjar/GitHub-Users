package android.example.githubusers

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_user.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        val id = intent.getStringExtra("ID")

        GlobalScope.launch(Dispatchers.Main) {
            val response = withContext(Dispatchers.IO){
                id?.let { Client.api.getUserById(it) }
            }
            if (response != null) {
                if(response.isSuccessful){
                    response.body()?.let {
                        tv_userName.text = it.login
                        tv_name.text = it.name
                        Picasso.get().load(it.avatar_url).into(iv_profile)
                    }

                }
            }
        }
    }
}