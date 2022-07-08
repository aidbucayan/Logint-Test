package com.adrian.bucayan.logintest.presentation.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adrian.bucayan.logintest.common.Resource
import com.adrian.bucayan.logintest.domain.model.User
import com.adrian.bucayan.logintest.domain.request.UserRequest
import com.adrian.bucayan.logintest.domain.use_case.GetUserByUsernameUseCase
import com.adrian.bucayan.logintest.domain.use_case.RegisterUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUserUseCase: RegisterUserUseCase,
    private val getUserByUserNameUseCase : GetUserByUsernameUseCase
): ViewModel() {

    private val _dataStateRegisterUser: MutableLiveData<Resource<Unit>> = MutableLiveData()

    val dataStateRegisterUser: LiveData<Resource<Unit>> = _dataStateRegisterUser

    fun onRegisterEvent(event: RegisterUserEvent, request: UserRequest) {
        viewModelScope.launch {
            when(event) {
                is RegisterUserEvent.SaveUser -> {
                    registerUserUseCase(request)
                        .onEach { dataStateRegisterUser ->
                            _dataStateRegisterUser.value = dataStateRegisterUser
                        }
                        .launchIn(viewModelScope)
                }
            }
        }

    }

    private val _dataStateGetUser: MutableLiveData<Resource<User>> = MutableLiveData()

    val dataStateGetUser: LiveData<Resource<User>> = _dataStateGetUser

    fun onGetUserNameEvent(event: GetUserByUserNameEvent, userName: String) {
        viewModelScope.launch {
            when(event) {
                is GetUserByUserNameEvent.GetUserByUsername -> {
                    getUserByUserNameUseCase(userName)
                        .onEach { dataStateGetUser ->
                            _dataStateGetUser.value = dataStateGetUser
                        }
                        .launchIn(viewModelScope)
                }
            }
        }

    }

}

sealed class RegisterUserEvent{
    object SaveUser: RegisterUserEvent()
}

sealed class GetUserByUserNameEvent{
    object GetUserByUsername: GetUserByUserNameEvent()
}