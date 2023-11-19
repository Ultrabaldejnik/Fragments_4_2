package com.example.fragments_4_2

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.fragments_4_2.data.User
import com.example.fragments_4_2.databinding.FragmentListUserBinding
import com.example.fragments_4_2.databinding.ItemUserBinding
import com.example.fragments_4_2.fragments.ListUserFragment


class UserAdapter(private val context: Context) :
    ListAdapter<User, UserAdapter.UserViewHolder>(UserDiffUtilCallBack()) {


    private var selectedContact = mutableListOf<Int>()
    var mode = true
    var onUserClicked: ((Int) -> Unit)? = null


    inner class UserViewHolder(val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(
            ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = getItem(position)

        with(holder.binding) {
            tvFirstName.text = user.name
            tvLastName.text = user.lastName
            tvPhoneNumber.text = user.phoneNumber
            holder.binding.checked.isClickable = false
            Glide.with(context)
                .load(user.imagePerson)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .fitCenter()
                .into(imageUser)

            if (mode) {
                checked.visibility = View.GONE
                selectedContact.clear()
                Log.d("Selected",selectedContact.toString())
                holder.itemView.setOnClickListener {
                    onUserClicked?.invoke(user.id)
                }
            } else {
                checked.visibility = View.VISIBLE
                checked.isChecked = false
                holder.itemView.setOnClickListener {
                    holder.binding.checked.isChecked = !holder.binding.checked.isChecked
                    updateListForDelete(user.id)
                    Log.d("Selected",selectedContact.toString())
                }
            }
        }
    }
    fun getListForDelete(): List<Int> = selectedContact

    private fun updateListForDelete(id: Int) {
        if (id in selectedContact) {
            selectedContact.remove(id)
        } else {
            selectedContact.add(id)
        }
    }
}

open class UserDiffUtilCallBack : DiffUtil.ItemCallback<User>() {
    override fun areItemsTheSame(oldItem: User, newItem: User): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: User, newItem: User): Boolean = oldItem == newItem
}