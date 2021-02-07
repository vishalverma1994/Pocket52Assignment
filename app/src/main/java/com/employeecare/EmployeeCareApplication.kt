package com.employeecare

import android.app.Application
import com.employeecare.di.appModule
import com.employeecare.di.repoModule
import com.employeecare.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext

class EmployeeCareApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        GlobalContext.startKoin {
            androidContext(this@EmployeeCareApplication)
            modules(listOf(appModule, repoModule, viewModelModule))
        }
    }
}