package com.example.homework4part2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.homework4part2.databinding.UserItemBinding
import com.example.homework4part2.model.User

class UsersAdapter(val itemClickCallback: (User) -> Unit): RecyclerView.Adapter<UserViewHolder>(), View.OnClickListener {

    var users = emptyList<User>()
        set(value) {
            val diffCallback = UserDiffCallback(field,value)
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            field = value
            diffResult.dispatchUpdatesTo(this)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = UserItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        binding.root.setOnClickListener(this)

        return UserViewHolder(binding)
    }

    override fun getItemCount(): Int = users.size

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = users[position]
        with(holder.binding) {
            nameTv.text = user.name
            surnameTv.text = user.surname
            numberTv.text = user.number
            root.tag = user
            Glide.with(avatarIv.context)
                .load(user.avatarUrl)
                .circleCrop()
                .placeholder(R.drawable.ic_user_avatar)
                .error(R.drawable.ic_avatar_error)
                .into(avatarIv)
        }
    }

    override fun onClick(v: View) {
        itemClickCallback(v.tag as User)
    }
}

class UserDiffCallback(private val oldList: List<User>, private val newList: List<User>) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

}

class UserViewHolder(val binding: UserItemBinding): RecyclerView.ViewHolder(binding.root)