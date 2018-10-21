package com.keytotech.pagingdemo.di.viewModel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.keytotech.pagingdemo.presentation.comments.CommentsViewModel
import com.keytotech.pagingdemo.presentation.range.RangeViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * ViewModelModule
 *
 * @author Bogdan Ustyak (bogdan.ustyak@gmail.com)
 */
@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(CommentsViewModel::class)
    internal abstract fun bindCommentsViewModel(viewModel: CommentsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RangeViewModel::class)
    internal abstract fun bindRangeViewModel(viewModel: RangeViewModel): ViewModel
}