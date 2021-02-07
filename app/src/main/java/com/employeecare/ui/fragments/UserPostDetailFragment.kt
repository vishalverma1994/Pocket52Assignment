package com.employeecare.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.employeecare.data.model.UserPosts
import com.employeecare.databinding.FragmentUserPostDetailBinding
import com.employeecare.ui.adapter.UserPostAdapter
import com.employeecare.ui.base.BaseFragment
import com.employeecare.ui.viewmodel.MainViewModel
import com.employeecare.utils.ARG_1
import com.employeecare.utils.JsonUtils
import com.employeecare.utils.Status
import com.employeecare.utils.Utils
import com.google.gson.reflect.TypeToken
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserPostDetailFragment : BaseFragment() {

    private lateinit var binding: FragmentUserPostDetailBinding
    private lateinit var userPostAdapter: UserPostAdapter
    private val mainViewModel: MainViewModel by viewModel()
    private var userPostList = emptyList<UserPosts>()

    companion object {
        private val TAG = UserPostDetailFragment::class.simpleName
        fun newInstance(args: Bundle?) =
            UserPostDetailFragment().apply {
                arguments = args
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.clear()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentUserPostDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        extractData()
        initComponents()
    }

    private fun extractData() {
        requireArguments().let { args ->
            userPostList = JsonUtils.arrayObjectify<List<UserPosts>>(
                args.getString(ARG_1).orEmpty(),
                object : TypeToken<List<UserPosts>>() {}.type
            )
                    as List<UserPosts>
        }
    }

    private fun initComponents() {
        setUserPostAdapter()
        requestUserDetailsAPI()
        observeUserDetails()
    }

    private fun setUserPostAdapter() {
        binding.rvUserPost.layoutManager = LinearLayoutManager(requireContext())
        userPostAdapter = UserPostAdapter()
        binding.rvUserPost.adapter = userPostAdapter
        binding.rvUserPost.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                DividerItemDecoration.VERTICAL
            )
        )
        userPostAdapter.setUserPostList(userPostList)
    }

    private fun requestUserDetailsAPI() {
        if (!userPostList.isNullOrEmpty())
            mainViewModel.requestUserDetailsAPI(userPostList[0].userId)
    }

    private fun observeUserDetails() {
        mainViewModel._userDetailResponse.observe(viewLifecycleOwner, {
            when (it.status) {
                Status.SUCCESS -> {
                    binding.isProgressBarVisible = false
                    it.data?.let { userDetail ->
                        binding.userDataModel = userDetail
                    }
                }
                Status.LOADING -> {
                    binding.isProgressBarVisible = true
                }
                Status.ERROR -> {
                    binding.isProgressBarVisible = false
                    Utils.showToast(requireContext(), it.message)
                }
            }
        })
    }
}

