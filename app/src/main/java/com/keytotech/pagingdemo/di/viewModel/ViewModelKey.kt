package com.keytotech.pagingdemo.di.viewModel

import android.arch.lifecycle.ViewModel
import dagger.MapKey
import kotlin.reflect.KClass

/**
 * ViewModelKey
 *
 * @author Bogdan Ustyak (bogdan.ustyak@gmail.com)
 */

@MustBeDocumented
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
@Retention
@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)