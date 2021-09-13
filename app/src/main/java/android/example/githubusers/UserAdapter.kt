package android.example.githubusers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_user.view.*

class UserAdapter: RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
    var data: List<User> = ArrayList()
    var onItemClicked:((id:String) -> Unit)?= null

    inner class UserViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(user: User){
            with(itemView){
                tv_userName.text = user.login
                tv_name.text = user.name
                Picasso.get().load(user.avatar_url).into(iv_profile)
                setOnClickListener {
                    onItemClicked?.let { it1 -> it1(user.login) }
                }
            }
        }

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserAdapter.UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserAdapter.UserViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun swapData(data: List<User>){
        this.data = data
        notifyDataSetChanged()
    }
}