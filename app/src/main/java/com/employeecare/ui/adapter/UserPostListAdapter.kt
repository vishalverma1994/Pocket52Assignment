package com.employeecare.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.employeecare.R
import com.employeecare.data.model.UserPosts
import com.employeecare.databinding.AdapterUserPostListBinding

class UserPostListAdapter(private val onUserPostClickListener: (Int) -> Unit) : RecyclerView.Adapter<UserPostListAdapter.ViewHolder>() {

    private var userPostListMap = emptyMap<Int, MutableList<UserPosts>>()

    fun setUserList(userPostListMap: HashMap<Int, MutableList<UserPosts>>) {
        this.userPostListMap = userPostListMap
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.adapter_user_post_list,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindViews(
            userPostListMap.keys.toIntArray()[position],
            userPostListMap.get(userPostListMap.keys.toIntArray()[position])?.size ?: 0
        )
    }

    override fun getItemCount(): Int {
        return userPostListMap.size
    }

    inner class ViewHolder(private val binding: AdapterUserPostListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.clRoot.setOnClickListener {
                onUserPostClickListener(adapterPosition)
            }
        }

        fun bindViews(userId: Int, postCount: Int) {
            binding.userId = userId.toString()
            binding.postCount = postCount.toString()
        }
    }
}