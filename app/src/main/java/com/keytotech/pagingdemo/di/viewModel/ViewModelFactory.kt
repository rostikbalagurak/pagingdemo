package com.keytotech.pagingdemo.di.viewModel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider

import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

/**
 * ViewModelFactory
 *
 * @author Bogdan Ustyak (bogdan.ustyak@gmail.com)
 */
@Singleton
class ViewModelFactory @Inject constructor(
    private val viewModels: MutableMap<Class<out ViewModel>, Provider<ViewModel>>
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T = viewModels[modelClass]?.get() as T
}