package com.adrian.bucayan.logintest.presentation.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adrian.bucayan.logintest.common.Resource
import com.adrian.bucayan.logintest.domain.request.UserRequest
import com.adrian.bucayan.logintest.domain.use_case.RegisterUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUserUseCase: RegisterUserUseCase): ViewModel() {

    private val _dataStateRegisterUser: MutableLiveData<Resource<Unit>> = MutableLiveData()

    val dataStateRegisterUser: LiveData<Resource<Unit>> = _dataStateRegisterUser

    fun onAddEvent(event: AddUserEvent, request: UserRequest) {
        viewModelScope.launch {
            when(event) {
                is AddUserEvent.SaveUser -> {
                    registerUserUseCase(request)
                        .onEach { dataStateRegisterUser ->
                            _dataStateRegisterUser.value = dataStateRegisterUser
                        }
                        .launchIn(viewModelScope)
                }
            }
        }

    }

}

sealed class AddUserEvent{
    object SaveUser: AddUserEvent()
}
