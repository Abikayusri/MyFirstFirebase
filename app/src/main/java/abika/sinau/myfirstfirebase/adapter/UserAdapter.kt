package abika.sinau.myfirstfirebase.adapter

import abika.sinau.myfirstfirebase.R
import abika.sinau.myfirstfirebase.model.User
import abika.sinau.myfirstfirebase.update.UpdateUserActivity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_user.view.*

/**
 * Created by Abika Chairul Yusri on 08/01/2021
 * Bismillahirrahmanirrahim
 */
class UserAdapter (val data: ArrayList<User>, val itemClick: onItemDelete) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false))
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val data = data[position]
        holder.bind(data)
        holder.itemView.btnDelete.setOnClickListener {
            itemClick.user(data)
        }
    }

    override fun getItemCount(): Int = data.size ?: 0

    class UserViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(get: User) {
            itemView.tvUserName.text = "Nama: ${get.name}"
            itemView.tvUserPekerjaan.text = "Pekerjaan: ${get.pekerjaan}"

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, UpdateUserActivity::class.java)
                intent.putExtra("data", get)
                itemView.context.startActivity(intent)
            }
        }
    }

    interface onItemDelete {
        fun user(user: User)
    }
}