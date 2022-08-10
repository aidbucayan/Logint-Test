package com.adrian.bucayan.logintest.presentation.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adrian.bucayan.logintest.common.Resource
import com.adrian.bucayan.logintest.domain.models.User
import com.adrian.bucayan.logintest.domain.use_case.GetUserByUsernameAndPasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val getUserByUsernameAndPasswordUseCase: GetUserByUsernameAndPasswordUseCase
): ViewModel() {

    private val _dataStateGetUser: MutableLiveData<Resource<User>> = MutableLiveData()

    val dataStateGetUser: LiveData<Resource<User>> = _dataStateGetUser

    fun onGetUserByUserNamePasswordEvent(event: GetUserByUsernamePasswordEvent,
                                         userName: String, password: String) {
        viewModelScope.launch {
            when(event) {
                is GetUserByUsernamePasswordEvent.GetUserByUsernamePassword -> {
                    getUserByUsernameAndPasswordUseCase(userName, password)
                        .onEach { dataStateGetUser ->
                            _dataStateGetUser.value = dataStateGetUser
                        }
                        .launchIn(viewModelScope)
                }
            }
        }

    }

}

sealed class GetUserByUsernamePasswordEvent{
    object GetUserByUsernamePassword: GetUserByUsernamePasswordEvent()
}