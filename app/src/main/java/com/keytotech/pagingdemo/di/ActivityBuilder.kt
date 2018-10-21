package com.keytotech.pagingdemo.di

import com.keytotech.pagingdemo.presentation.range.MainActivity
import com.keytotech.pagingdemo.presentation.comments.CommentsActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * ActivityBuilder
 *
 * @author Bogdan Ustyak (bogdan.ustyak@gmail.com)
 */
@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector(
        modules = [

        ]
    )
    internal abstract fun bindMainActivity(): MainActivity

    @ContributesAndroidInjector
    internal abstract fun bindComentsActivity(): CommentsActivity
}