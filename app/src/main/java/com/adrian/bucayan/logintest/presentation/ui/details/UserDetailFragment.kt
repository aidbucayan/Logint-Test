package com.adrian.bucayan.logintest.presentation.ui.details

import android.Manifest
import android.os.Bundle
import android.view.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.adrian.bucayan.logintest.R
import com.adrian.bucayan.logintest.common.Constants
import com.adrian.bucayan.logintest.data.datasource.preference.AppPrefs
import com.adrian.bucayan.logintest.databinding.FragmentUserDetailBinding
import com.adrian.bucayan.logintest.domain.models.User
import com.adrian.bucayan.logintest.presentation.ui.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
@ExperimentalCoroutinesApi
class UserDetailFragment : Fragment() {

    private var _binding: FragmentUserDetailBinding? = null
    private val binding get() = _binding!!
    private val viewModel: UserDetailViewModel by viewModels()
    @Inject
    lateinit var appPrefs: AppPrefs
    private lateinit var user: User
    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
        permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
            val bundle = Bundle()
            bundle.putParcelable(Constants.SELECTED_USER, user)
            findNavController().navigate(R.id.action_userDetailFragment_to_geoLocationMapFragment, bundle)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        _binding = FragmentUserDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity?)?.displayToolbar(true)
        (activity as MainActivity?)?.toolBarTitle(getString(R.string.details))
        user = arguments?.getParcelable(Constants.SELECTED_USER)!!
        Timber.e("user = ${user.name}")
        populateViewWithUserData()
    }

    private fun populateViewWithUserData() {
        binding.tvName.text = user.name
        binding.tvUserName.text = "Username : "+ user.name
        binding.tvUserCompany.text = "Company : " + user.company!!.name
        binding.tvUserEmail.text = "Email : "+ user.email
        binding.tvUserPhone.text = "Phone : "+ user.phone
        binding.tvUserWebsite.text = "Website : "+ user.website
        binding.tvUserAddress.text = "Address : "+ user.address!!.street + ", " +
                user.address!!.suite + ", " + user.address!!.city + " \n Zip Code : " + user.address!!.zipcode
        binding.tvUserGeo.text = "Geolocation : Lat " + user.address!!.geo!!.lat + ", Long " +
                user.address!!.geo!!.lng
        binding.tvUserGeo.setOnClickListener {
            permissionLauncher.launch(arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
            ))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //get item id to handle item clicks
        val id = item.itemId
        //handle item clicks
        if (id == R.id.action_logout){
            Timber.e("action_logout")
            appPrefs.isLogin = false
            findNavController().navigate(R.id.action_userDetailFragment_to_loginFragment)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}