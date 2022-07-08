package com.adrian.bucayan.logintest.presentation.ui.home

import android.annotation.SuppressLint
import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.adrian.bucayan.logintest.databinding.UserItemsBinding
import com.adrian.bucayan.logintest.domain.model.User
import com.adrian.bucayan.logintest.presentation.util.adapter.BaseRecyclerViewAdapter
import com.adrian.bucayan.logintest.presentation.util.adapter.BaseViewHolder
import com.adrian.bucayan.logintest.presentation.util.adapter.ViewHolderInitializer
import kotlinx.coroutines.ExperimentalCoroutinesApi

class UserListAdapter : BaseRecyclerViewAdapter<User, UserItemsBinding>() ,
    ViewHolderInitializer<User, UserItemsBinding> {

    var toSelectUser: ((User, Int) -> Unit)? = null

    init { viewBindingInitializer = this }

    class TopSpacingDecoration(private val padding: Int): RecyclerView.ItemDecoration() {

        override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
            super.getItemOffsets(outRect, view, parent, state)

            if (parent.getChildAdapterPosition(view) > 0) {
                outRect.top = padding
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun generateViewHolder(parent: ViewGroup): BaseViewHolder<User, UserItemsBinding> {

        val itemBinding: UserItemsBinding = UserItemsBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)

        return UserListAdapterViewHolder(itemBinding, toSelectUser)

    }

}

@ExperimentalCoroutinesApi
class UserListAdapterViewHolder(
    viewBinding: UserItemsBinding,
    private val toSelectUser: ((User, Int) -> Unit)?
)  : BaseViewHolder<User, UserItemsBinding>(viewBinding) {

    private val holder : CardView = viewBinding.cvUserItemHolder
    private val name : TextView = viewBinding.tvUserName
    private val email : TextView = viewBinding.tvUserEmail
    private val phone : TextView = viewBinding.tvUserPhone
    private val company : TextView = viewBinding.tvUserCompany

    @SuppressLint("SetTextI18n", "ResourceType")
    override fun setViews(item: User) {
        super.setViews(item)

        name.text = item.name
        email.text = "Email : " + item.email
        phone.text = "Phone : " + item.phone
        company.text = "Company : " + (item.company?.name ?: "")
        
        holder.setOnClickListener{
            toSelectUser?.invoke(item, adapterPosition)
        }


    }



}

