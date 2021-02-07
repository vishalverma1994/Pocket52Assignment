package com.employeecare.utils

import android.util.Log

object LogUtils {

    private val DEFAULT_TAG = "TAG"
    private var showLogs = true


    fun setShowLogs(show: Boolean) {
        showLogs = show
    }

    fun getShowLogs(): Boolean {
        return showLogs
    }


    fun i(tag: String?, msg: String?) {
        if (getShowLogs()) Log.i(tag, msg!!)
    }

    fun i(msg: String?) {
        if (getShowLogs()) Log.i(DEFAULT_TAG, msg!!)
    }

    fun d(tag: String?, msg: String?) {
        if (getShowLogs()) Log.i(tag, msg!!)
    }

    fun d(msg: String?) {
        if (getShowLogs()) Log.i(DEFAULT_TAG, msg!!)
    }

    fun e(msg: String?) {
        if (getShowLogs()) Log.e(DEFAULT_TAG, msg!!)
    }

    fun e(tag: String?, msg: String?) {
        if (getShowLogs()) Log.e(tag, msg!!)
    }

    //what a terrible failure! To log stuff that defies logic of the android world :P
    fun wtf(tag: String?, msg: String?) {
        if (getShowLogs()) Log.wtf(tag, msg)
    }
}