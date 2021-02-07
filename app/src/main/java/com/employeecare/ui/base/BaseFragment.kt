package com.employeecare.ui.base

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.employeecare.listeners.FragmentSelectionListener

abstract class BaseFragment : Fragment() {

    private val TAG = BaseFragment::class.java.simpleName
    lateinit var listener: FragmentSelectionListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragmentSelectionListener) listener = context
    }

    fun openFragment(reqCode: Int, data: Bundle? = null) {
        if (isAdded && activity != null && activity is BaseActivity
            && ::listener.isInitialized
        ) {
            listener.onFragmentSelected(reqCode, data)
        }
    }
}