package com.adrian.bucayan.logintest.presentation.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.adrian.bucayan.logintest.databinding.UserItemsBinding
import com.adrian.bucayan.logintest.domain.models.User

class HomeAdapter : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: UserItemsBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: User) = binding.apply {
            tvUserName.text = item.name
            tvUserEmail.text = "Email : " + item.email
            tvUserPhone.text = "Phone : " + item.phone
            tvUserCompany.text = "Company : " + (item.company?.name ?: "")
            // root layout of this item // Adding clickListener to
            root.setOnClickListener {
                onItemClickListener?.let { it(item) }
            }
        }
    }

    private val differCallback = object : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = UserItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onItemClickListener: ((User) -> Unit)? = null

    fun setOnItemClickListener(listener: (User) -> Unit) {
        onItemClickListener = listener
    }

}













