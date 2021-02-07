package com.employeecare.listeners

import android.os.Bundle

interface FragmentSelectionListener {
    fun onFragmentSelected(reqCode: Int, data: Bundle?)
}