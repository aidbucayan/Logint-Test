package com.adrian.bucayan.logintest.presentation.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adrian.bucayan.logintest.common.Resource
import com.adrian.bucayan.logintest.domain.model.User
import com.adrian.bucayan.logintest.domain.use_case.GetUserListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserDetailViewModel @Inject constructor(
    private val getUserListUseCase: GetUserListUseCase
): ViewModel() {

    private val _dataStateGetUserList: MutableLiveData<Resource<List<User>>> = MutableLiveData()

    val dataStateGetUserList: LiveData<Resource<List<User>>> = _dataStateGetUserList

    fun onGetUserListEvent(event: GetUserListEvent) {
        viewModelScope.launch {
            when(event) {
                is GetUserListEvent.GetUserList -> {
                    getUserListUseCase()
                        .onEach { dataStateGetUserList ->
                            _dataStateGetUserList.value = dataStateGetUserList
                        }
                        .launchIn(viewModelScope)
                }
            }
        }

    }

}

sealed class GetUserListEvent{
    object GetUserList: GetUserListEvent()
}