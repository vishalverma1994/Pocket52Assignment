package com.employeecare.ui.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import com.employeecare.R
import com.employeecare.databinding.ActivityMainBinding
import com.employeecare.ui.base.BaseActivity
import com.employeecare.utils.ARG_REQCODE
import com.employeecare.utils.FRAG_USER_POST_LIST

class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding
    private val data = Bundle()

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home){
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this@MainActivity, R.layout.activity_main)
        extractData()
        initComponents()
    }

    override fun onFragmentSelected(reqCode: Int, data: Bundle?) {
        fragmentTransaction = fragmentManager.beginTransaction()
        val fragment = getFragment(reqCode, data)
        if (fragmentManager.backStackEntryCount > 0) {
            fragmentTransaction.setCustomAnimations(
                R.anim.in_from_right, R.anim.out_to_left,
                R.anim.in_from_left, R.anim.out_to_right
            )
        }
        if (fragment != null) {
            fragment.arguments = data
            fragmentTransaction.add(binding.flContainer.id, fragment)
            fragmentTransaction.addToBackStack(reqCode.toString())
            fragmentTransaction.commitAllowingStateLoss() //commit();
        }
    }

    override fun onBackPressed() {
        navigateBack()
    }

    private fun extractData() {
        fragReqCode = FRAG_USER_POST_LIST
        if (intent != null)
            fragReqCode = intent.getIntExtra(ARG_REQCODE, FRAG_USER_POST_LIST)
        intent.putExtras(data)
    }

    private fun initComponents() {
        setToolbar()
        onFragmentSelected(fragReqCode, intent.extras)
    }

    private fun setToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }
}