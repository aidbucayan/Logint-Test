package com.adrian.bucayan.logintest.presentation.ui.home

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.adrian.bucayan.logintest.R
import com.adrian.bucayan.logintest.common.Constants
import com.adrian.bucayan.logintest.common.Resource
import com.adrian.bucayan.logintest.databinding.FragmentHomeBinding
import com.adrian.bucayan.logintest.domain.models.User
import com.adrian.bucayan.logintest.presentation.ui.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import timber.log.Timber

@AndroidEntryPoint
@ExperimentalCoroutinesApi
class HomeFragment : Fragment(R.layout.fragment_home) {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var homeAdapter : HomeAdapter
    private var userList : List<User>? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity?)?.displayToolbar(false)
        setupRecyclerView()
        subscribeObservers()

        viewModel.onGetUserListEvent(GetUserListEvent.GetUserList)

        homeAdapter.setOnItemClickListener {
            Timber.e("User = " + it.name)
        }
    }

    /*private fun initRecyclerview() {
        binding.devListRecyclerview.apply {
            layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)

            val itemDecoration: UserListAdapter.TopSpacingDecoration =
                UserListAdapter.TopSpacingDecoration(5)
            addItemDecoration(itemDecoration)

            userListAdapter = UserListAdapter()
            userListAdapter!!.toSelectUser = this@HomeFragment::toSelectedUser
            adapter = userListAdapter
        }
    }*/

    private fun setupRecyclerView() {
        homeAdapter = HomeAdapter()
        binding.devListRecyclerview.apply {
            adapter = homeAdapter
            layoutManager = LinearLayoutManager(activity)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }

        homeAdapter.setOnItemClickListener {
            toSelectedUser(it)
        }
    }

    private fun subscribeObservers() {
        viewModel.dataStateGetUserList.observe(viewLifecycleOwner) { dataStateUserList ->
            when (dataStateUserList) {

                is Resource.Success<List<User>> -> {
                    Timber.e("dataStateUserList SUCCESS")
                    displayLoading(false)
                    binding.devListRecyclerview.visibility = View.VISIBLE
                    binding.devListTvEmptyMsg.visibility = View.GONE

                    userList = dataStateUserList.data
                    if (!userList.isNullOrEmpty()) {
                        homeAdapter.differ.submitList(userList)
                    } else {
                        binding.devListTvEmptyMsg.visibility = View.VISIBLE
                    }

                }

                is Resource.Error -> {
                    Timber.e("dataStateUserList ERROR %s", dataStateUserList.message)
                    requireContext().toast(dataStateUserList.message.toString(), true)
                    displayLoading(false)
                }

                is Resource.Loading -> {
                    Timber.e("dataStateUserList LOADING")
                    displayLoading(true)
                }
            }
        }

    }

    private fun Context.toast(message: CharSequence, isLengthLong: Boolean = true) =
        Toast.makeText(
            this, message, if (isLengthLong) {
                Toast.LENGTH_LONG
            } else {
                Toast.LENGTH_SHORT
            }
        ).show()

    private fun displayLoading(isDisplayed: Boolean) {
        if (isDisplayed) {
            binding.devListLinearProgressBarLoadMore.visibility = View.VISIBLE
        } else {
            binding.devListLinearProgressBarLoadMore.visibility = View.GONE
        }
    }

    private fun toSelectedUser(user: User) {
        val bundle = Bundle()
        bundle.putParcelable(Constants.SELECTED_USER, user)
        findNavController().navigate(R.id.action_homeFragment_to_userDetailFragment, bundle)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}