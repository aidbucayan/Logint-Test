package com.adrian.bucayan.logintest.presentation.ui.register

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.adrian.bucayan.logintest.R
import com.adrian.bucayan.logintest.common.Resource
import com.adrian.bucayan.logintest.databinding.FragmentRegisterBinding
import com.adrian.bucayan.logintest.domain.model.User
import com.adrian.bucayan.logintest.domain.request.UserRequest
import com.adrian.bucayan.logintest.presentation.ui.MainActivity
import com.adrian.bucayan.logintest.presentation.util.Utils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import timber.log.Timber
import javax.inject.Inject


@ExperimentalCoroutinesApi
@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private val viewModel: RegisterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity?)?.displayToolbar(true)
        (activity as MainActivity?)?.toolBarTitle(getString(R.string.register))
        (activity as MainActivity?)?.hideMenu(true)
        setHasOptionsMenu(true)

        observers()

        binding.btnRegister.setOnClickListener {
            when {
                !isValidUsername() -> {
                    binding.etRegisterUsername.error =  getString(R.string.username_invalid)
                }
                !isValidEmail() -> {
                    binding.etRegisterEmail.error =  getString(R.string.email_invalid)
                }
                !isValidPassword() -> {
                    binding.etRegisterPassword.error =  getString(R.string.password_invalid)
                }
                else -> {
                    viewModel.onGetUserNameEvent(
                        GetUserByUserNameEvent.GetUserByUsername, binding.etRegisterUsername.text.toString())
                }
            }
        }

    }

    private fun observers() {
        viewModel.dataStateGetUser.observe(viewLifecycleOwner) { dataStateGetUser ->
            when (dataStateGetUser) {
                is Resource.Success<User> -> {
                    Timber.e("dataStateSaveUser SUCCESS")
                    if (dataStateGetUser.data != null) {
                        requireActivity().applicationContext.toast(resources.getString(R.string.username_already_taken), true)
                    } else {
                        Timber.e("username available")
                    }

                }
                is Resource.Error -> {
                    Timber.e("dataStateSaveUser ERROR %s", dataStateGetUser.message)
                    if (dataStateGetUser.message.toString().equals("Null_User", true)) {
                        callOnRegisterEvent()
                    }
                }
                is Resource.Loading -> {
                    Timber.e("dataStateSaveUser LOADING")
                }
            }
        }

        viewModel.dataStateRegisterUser.observe(viewLifecycleOwner) { dataStateSaveUser ->
            when (dataStateSaveUser) {
                is Resource.Success<Unit> -> {
                    Timber.e("dataStateSaveUser SUCCESS")
                    requireActivity().applicationContext.toast(resources.getString(R.string.registration_success), true)
                    findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                }
                is Resource.Error -> {
                    Timber.e("dataStateSaveUser ERROR %s", dataStateSaveUser.message)
                    requireActivity().applicationContext.toast(dataStateSaveUser.message.toString(), false)
                }
                is Resource.Loading -> {
                    Timber.e("dataStateSaveUser LOADING")
                }
            }
        }
    }

    private fun callOnRegisterEvent() {
        if (binding.etRegisterName.text.isNotBlank() && binding.etRegisterPassword.text.isNotBlank()) {
            viewModel.onRegisterEvent(
                RegisterUserEvent.SaveUser,
                UserRequest(0,
                    binding.etRegisterEmail.text.toString(),
                    binding.etRegisterPassword.text.toString(),
                    binding.etRegisterName.text.toString(),
                    binding.etRegisterPhone.text.toString(),
                    binding.etRegisterUsername.text.toString(),
                    binding.etRegisterWebsite.text.toString()
                )
            )
        }
    }

    private fun isValidUsername(): Boolean {
        return Utils.isUserNameValid(binding.etRegisterUsername.text.toString())
    }

    private fun isValidEmail(): Boolean {
        return Utils.isEmailValid(binding.etRegisterEmail.text.toString())
    }

    private fun isValidPassword(): Boolean {
        return Utils.isPasswordValid(binding.etRegisterPassword.text.toString())
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.findItem(R.id.action_logout)?.isVisible = false
    }

    private fun Context.toast(message: CharSequence, isLengthLong: Boolean = true) =
        Toast.makeText(
            this, message, if (isLengthLong) {
                Toast.LENGTH_LONG
            } else {
                Toast.LENGTH_SHORT
            }
        ).show()

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}