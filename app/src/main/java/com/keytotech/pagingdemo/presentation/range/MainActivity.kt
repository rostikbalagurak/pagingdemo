package com.keytotech.pagingdemo.presentation.range

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.EditText
import android.widget.Toast
import com.jakewharton.rxbinding2.widget.RxTextView
import com.keytotech.pagingdemo.R
import com.keytotech.pagingdemo.presentation.comments.CommentsActivity
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), RangeView {

    private val compositeDisposable = CompositeDisposable()

    lateinit var presenter: RangePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        this.presenter = RangePresenter(this)
        //  this.bindMockData()
        this.initUI()
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
            this.presenter.validate(this.start(), this.end())
        }
    }

    override fun openCommentsScreen(start: Int, end: Int) {
        CommentsActivity.start(this, start, end)
    }

    override fun showInvalidRangeError() {
        Toast.makeText(baseContext, getString(R.string.invalid_range), Toast.LENGTH_SHORT).show()
    }

    private fun bindMockData() {
        this.etLimit.setText("2")
        this.etStart.setText("15")
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
