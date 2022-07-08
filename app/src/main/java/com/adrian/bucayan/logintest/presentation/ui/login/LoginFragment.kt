package com.adrian.bucayan.logintest.presentation.ui.login

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.adrian.bucayan.logintest.R
import com.adrian.bucayan.logintest.common.Resource
import com.adrian.bucayan.logintest.data.datasource.preference.AppPrefs
import com.adrian.bucayan.logintest.databinding.FragmentLoginBinding
import com.adrian.bucayan.logintest.domain.model.User
import com.adrian.bucayan.logintest.presentation.ui.MainActivity
import com.adrian.bucayan.logintest.presentation.util.Utils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import timber.log.Timber
import javax.inject.Inject

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val viewModel: LoginViewModel by viewModels()
    @Inject
    lateinit var appPrefs: AppPrefs

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity?)?.displayToolbar(false)

        binding.btnLogin.setOnClickListener {
            when {
                !isValidUsername() -> {
                    binding.etLoginUsername.error =   getString(R.string.username_invalid)
                }
                !isValidPassword() -> {
                    binding.etLoginPassword.error =  getString(R.string.password_invalid)
                }
                else -> {
                    viewModel.onGetUserByUserNamePasswordEvent(
                        GetUserByUsernamePasswordEvent.GetUserByUsernamePassword,
                        binding.etLoginUsername.text.toString(),
                        binding.etLoginPassword.text.toString())
                }
            }
        }

        binding.tvLoginRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        observers()
    }

    private fun observers() {
        viewModel.dataStateGetUser.observe(viewLifecycleOwner) { dataStateGetUser ->
            when (dataStateGetUser) {
                is Resource.Success<User> -> {
                    Timber.e("dataStateSaveUser SUCCESS")
                    if (dataStateGetUser.data != null) {
                        appPrefs.isLogin = true
                        findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                    }
                }
                is Resource.Error -> {
                    Timber.e("dataStateSaveUser ERROR %s", dataStateGetUser.message)
                    if (dataStateGetUser.message.toString().equals("Incorrect", true)) {
                        requireActivity().applicationContext.toast(
                            resources.getString(R.string.incorrect_username_or_password),
                            true
                        )
                    }
                }
                is Resource.Loading -> {
                    Timber.e("dataStateSaveUser LOADING")
                }
            }
        }
    }

    private fun isValidUsername(): Boolean {
        return Utils.isUserNameValid(binding.etLoginUsername.text.toString())
    }

    private fun isValidPassword(): Boolean {
        return Utils.isPasswordValid(binding.etLoginPassword.text.toString())
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