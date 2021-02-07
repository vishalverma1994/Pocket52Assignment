package com.employeecare.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.employeecare.R
import com.employeecare.data.model.UserPosts
import com.employeecare.databinding.AdapterUserPostBinding

class UserPostAdapter : RecyclerView.Adapter<UserPostAdapter.ViewHolder>() {

    private var userPostList = emptyList<UserPosts>()

    fun setUserPostList(userPostList: List<UserPosts>) {
        this.userPostList = userPostList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.adapter_user_post,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindViews(userPostList[position])
    }

    override fun getItemCount(): Int {
        return userPostList.size
    }

    inner class ViewHolder(private val binding: AdapterUserPostBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindViews(userPosts: UserPosts) {
            binding.userPostData = userPosts
        }
    }
}