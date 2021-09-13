package android.example.githubusers

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    val adapter = UserAdapter()
    val originalList = arrayListOf<User>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter.onItemClicked={
            val intent = Intent(this, UserActivity::class.java)
            intent.putExtra("ID", it)
            startActivity(intent)
        }
        rv_users.layoutManager = LinearLayoutManager(this)
        rv_users.adapter = adapter

        sv_searchUser.isSubmitButtonEnabled = true
        sv_searchUser.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    searchUser(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    searchUser(newText)
                }
                return true
            }
        })
        sv_searchUser.setOnClickListener {

            adapter.swapData(originalList)

        }

        GlobalScope.launch(Dispatchers.Main) {
            val response = withContext(Dispatchers.IO){
                Client.api.getUsers()
            }
            if(response.isSuccessful){
                response.body()?.let{
                    originalList.addAll(it)
                    adapter.swapData(it)
                }
            }
        }
    }

    private fun searchUser(query: String) {
        GlobalScope.launch(Dispatchers.Main) {
            val response = withContext(Dispatchers.IO){
                Client.api.searchUser(query)
            }
            if(response.isSuccessful){
                response.body()?.let {
                    it.items?.let {
                        adapter.swapData(it)
                    }
                }
            }
        }
    }
}