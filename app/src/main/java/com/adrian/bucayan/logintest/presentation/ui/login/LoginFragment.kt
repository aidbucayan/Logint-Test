package com.adrian.bucayan.logintest.presentation.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.adrian.bucayan.logintest.R
import com.adrian.bucayan.logintest.databinding.FragmentLoginBinding
import com.adrian.bucayan.logintest.presentation.ui.MainActivity
import com.adrian.bucayan.logintest.presentation.util.Utils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    @Inject
    lateinit var utils: Utils

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
                !isValidEmail() -> {
                    binding.etLoginEmail.error =   getString(R.string.email_invalid)
                }
                !isValidPassword() -> {
                    binding.etLoginPassword.error =  getString(R.string.password_invalid)
                }
                else -> {
                    if (binding.etLoginEmail.text.isNotBlank() && binding.etLoginPassword.text.isNotBlank()) {
                        //TODO check database
                    }
                }
            }
        }

        binding.tvLoginRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

    }

    private fun isValidEmail(): Boolean {
        return utils.isEmailValid(binding.etLoginEmail.text)
    }

    private fun isValidPassword(): Boolean {
        return utils.isPasswordValid(binding.etLoginPassword.text)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}