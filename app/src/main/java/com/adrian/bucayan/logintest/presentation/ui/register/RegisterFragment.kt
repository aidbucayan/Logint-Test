package com.adrian.bucayan.logintest.presentation.ui.register

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.adrian.bucayan.logintest.R
import com.adrian.bucayan.logintest.common.Resource
import com.adrian.bucayan.logintest.databinding.FragmentRegisterBinding
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
    @Inject
    lateinit var utils: Utils

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
                !isValidEmail() -> {
                    binding.etRegisterEmail.error =   getString(R.string.email_invalid)
                }
                !isValidPassword() -> {
                    binding.etRegisterPassword.error =  getString(R.string.password_invalid)
                }
                else -> {
                    if (binding.etRegisterEmail.text.isNotBlank() && binding.etRegisterPassword.text.isNotBlank()) {
                        viewModel.onAddEvent(
                            AddUserEvent.SaveUser,
                            UserRequest(null,
                                binding.etRegisterEmail.text.toString(),
                                binding.etRegisterEmail.text.toString(),
                                binding.etRegisterName.text.toString(),
                                binding.etRegisterPhone.text.toString(),
                                binding.etRegisterUsername.text.toString(),
                                binding.etRegisterWebsite.text.toString()
                            )
                        )
                    }
                }
            }
        }

        //findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
    }

    private fun observers() {
        viewModel.dataStateRegisterUser.observe(viewLifecycleOwner) { dataStateSaveUser ->
            when (dataStateSaveUser) {

                is Resource.Success<Unit> -> {
                    Timber.e("dataStateSaveUser SUCCESS")
                }

                is Resource.Error -> {
                    Timber.e("dataStateGetRecipe ERROR %s", dataStateSaveUser.message)
                }

                is Resource.Loading -> {
                    Timber.e("dataStateGetRecipe LOADING")
                }
            }
        }
    }

    private fun isValidEmail(): Boolean {
        return utils.isEmailValid(binding.etRegisterEmail.text)
    }

    private fun isValidPassword(): Boolean {
        return utils.isPasswordValid(binding.etRegisterPassword.text)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.findItem(R.id.action_logout)?.isVisible = false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}