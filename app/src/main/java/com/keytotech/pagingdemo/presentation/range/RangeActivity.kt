package com.keytotech.pagingdemo.presentation.range

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.EditText
import android.widget.Toast
import com.jakewharton.rxbinding2.widget.RxTextView
import com.keytotech.pagingdemo.R
import com.keytotech.pagingdemo.di.viewModel.NetworkResource
import com.keytotech.pagingdemo.di.viewModel.Status
import com.keytotech.pagingdemo.domain.entity.IdsRange
import com.keytotech.pagingdemo.presentation.comments.CommentsActivity
import dagger.android.AndroidInjection
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction
import kotlinx.android.synthetic.main.activity_range.*
import javax.inject.Inject
import android.content.DialogInterface
import android.app.ProgressDialog

/**
 * RangeActivity
 *
 * @author Bogdan Ustyak (bogdan.ustyak@gmail.com)
 */
class RangeActivity : AppCompatActivity() {

    private val compositeDisposable = CompositeDisposable()

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var viewModel: RangeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_range)
        this.bindViewModel()
        this.initUI()
    }

    private fun bindViewModel() {
        this.viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(RangeViewModel::class.java)
        this.viewModel.range().observe(this, Observer { resource ->
            resource?.let { bindRange(it) }
        })
    }

    private fun bindRange(resource: NetworkResource<IdsRange>) {
        when (resource.status) {
            Status.FAILED -> {
                showInvalidRangeError()
            }
            Status.SUCCESS -> {
                openCommentsScreen(resource.get())
            }
            Status.RUNNING -> {
                showLoadingDialog()
            }
        }
    }

    private var loadingDialog: ProgressDialog? = null

    private fun showLoadingDialog() {
        loadingDialog = ProgressDialog(this)
        loadingDialog?.setTitle(getString(R.string.loading_title))
        loadingDialog?.setMessage(getString(R.string.loading_message))
        loadingDialog?.setButton(getString(R.string.loading_cancel), DialogInterface.OnClickListener { _, _ ->
            hideLoading()
            viewModel.cancelLoading()
            return@OnClickListener
        })
        loadingDialog?.show()
    }

    private fun hideLoading() {
        loadingDialog?.dismiss()
        loadingDialog = null
    }

    private fun initUI() {
        val startTextChangesObservable = RxTextView.textChanges(etStart)
        val limitTextChangesObservable = RxTextView.textChanges(etLimit)
        val inputSubscription: Observable<Boolean> = Observable.combineLatest(
            startTextChangesObservable,
            limitTextChangesObservable,
            BiFunction { start, limit ->
                return@BiFunction start.isNotBlank() && limit.isNotBlank()
            }
        )
        this.compositeDisposable.add(
            inputSubscription.subscribe {
                button.isEnabled = it
            }
        )
        button.setOnClickListener {
            this.viewModel.validate(IdsRange(this.start(), this.end()))
        }
    }

    private fun openCommentsScreen(idsRange: IdsRange) {
        hideLoading()
        CommentsActivity.start(this, idsRange)
    }

    private fun showInvalidRangeError() {
        Toast.makeText(baseContext, getString(R.string.invalid_range), Toast.LENGTH_SHORT).show()
    }

    private fun start(): Int {
        return this.editNumberValue(etStart)
    }

    private fun end(): Int {
        return this.editNumberValue(etLimit)
    }

    private fun editNumberValue(editText: EditText): Int {
        val text = editText.text.toString()
        return text.toInt()
    }

    override fun onDestroy() {
        super.onDestroy()
        this.compositeDisposable.clear()
    }
}
