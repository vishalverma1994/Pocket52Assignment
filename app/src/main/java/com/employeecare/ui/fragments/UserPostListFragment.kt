package com.employeecare.ui.fragments

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.employeecare.R
import com.employeecare.data.model.UserPosts
import com.employeecare.databinding.FragmentUserPostListBinding
import com.employeecare.listeners.RetryListener
import com.employeecare.ui.activity.MainActivity
import com.employeecare.ui.adapter.UserPostListAdapter
import com.employeecare.ui.base.BaseFragment
import com.employeecare.ui.viewmodel.MainViewModel
import com.employeecare.utils.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserPostListFragment : BaseFragment(), RetryListener {

    private lateinit var binding: FragmentUserPostListBinding
    private val mainViewModel: MainViewModel by viewModel()

    private lateinit var userPostListAdapter: UserPostListAdapter
    private var userPostList = emptyList<UserPosts>()
    private lateinit var userPostListMap: HashMap<Int, MutableList<UserPosts>>
    private lateinit var finalUserPostListMap: HashMap<Int, MutableList<UserPosts>>

    companion object {
        private val TAG = UserPostListFragment::class.simpleName
        fun newInstance(args: Bundle?) =
            UserPostListFragment().apply {
                arguments = args
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.seacrh_menu, menu)
        val search = menu.findItem(R.id.action_search)
        val searchView = search.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    if (it.isNotEmpty())
                        searchItem(it)
                    else {
                        loadDefaultUserPost()
                    }
                }
                return true
            }
        })
        return super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentUserPostListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onRetryButtonCallBack() {
        if (userPostListMap.isNotEmpty()) {
            loadDefaultUserPost()
        } else requestUserPostsAPI()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initComponents()
        requestUserPostsAPI()
        observeUserPostsListResponse()
    }

    private fun initComponents() {
        binding.handler = this
        (requireActivity() as MainActivity).supportActionBar?.title = getString(R.string.user_post)
        setUserPostsAdapter()
    }

    private fun setUserPostsAdapter() {
        binding.rvUserList.layoutManager = LinearLayoutManager(requireContext())
        userPostListAdapter = UserPostListAdapter(::onUserPostClickListener)
        binding.rvUserList.adapter = userPostListAdapter
        binding.rvUserList.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                DividerItemDecoration.VERTICAL
            )
        )
    }

    private fun onUserPostClickListener(position: Int) {
        val data = Bundle()
        if (::userPostListMap.isInitialized && userPostListMap.isNotEmpty())
            data.putString(
                ARG_1,
                JsonUtils.jsonify(userPostListMap.get(userPostListMap.keys.toIntArray()[position]))
            )
        openFragment(FRAG_USER_POST_DETAIL, data)
    }

    //api request to fetch user posts
    private fun requestUserPostsAPI() {
        mainViewModel.requestUserPostListAPI()
    }

    //method is used to observe the user posts list
    private fun observeUserPostsListResponse() {
        mainViewModel._userPostListResponse.observe(viewLifecycleOwner, {
            when (it.status) {
                Status.SUCCESS -> {
                    binding.isProgressBarVisible = false
                    binding.isDataFetched = true
                    binding.isEmptyStateVisible = false
                    it.data?.let { userPostList ->
                        this.userPostList = userPostList
                        filterItems()
                    }
                }
                Status.LOADING -> {
                    binding.isProgressBarVisible = true
                    binding.isDataFetched = false
                    binding.isEmptyStateVisible = false
                }
                Status.ERROR -> {
                    binding.isProgressBarVisible = false
                    binding.isDataFetched = false
                    binding.isEmptyStateVisible = true
                    Utils.showToast(requireContext(), it.message)
                }
            }
        })
    }

    //method is used to filter the items to show on a view
    private fun filterItems() {
        userPostListMap = HashMap()

        userPostList.forEach { userPosts ->
            if (userPostListMap.containsKey(userPosts.userId)) {
                val postList = userPostListMap[userPosts.userId]
                postList?.add(userPosts)
            } else {
                val postList = mutableListOf<UserPosts>()
                postList.add(userPosts)
                userPostListMap[userPosts.userId] = postList
            }
        }
        userPostListAdapter.setUserList(userPostListMap)
        LogUtils.e(TAG, userPostListMap.toString())
    }

    //method is used to handle the search functionality
    private fun searchItem(searchQuery: String) {
        finalUserPostListMap = HashMap()
        userPostListMap.keys.forEach { userId ->
            if (userId == searchQuery.toInt())
                userPostListMap[userId]?.let {
                    finalUserPostListMap[userId] = it
                    userPostListAdapter.setUserList(finalUserPostListMap)
                }
        }
        binding.isEmptyStateVisible = finalUserPostListMap.isEmpty()
        binding.isDataFetched = finalUserPostListMap.isNotEmpty()
    }

    //method is used to load the default data
    private fun loadDefaultUserPost() {
        if (!::finalUserPostListMap.isInitialized)
            finalUserPostListMap = HashMap()
        finalUserPostListMap.clear()
        finalUserPostListMap = userPostListMap
        userPostListAdapter.setUserList(finalUserPostListMap)
        binding.isEmptyStateVisible = false
        binding.isDataFetched = true
    }
}

