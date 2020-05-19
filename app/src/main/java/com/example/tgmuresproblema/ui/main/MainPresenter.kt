package com.example.tgmuresproblema.ui.main

import com.example.tgmuresproblema.data.persistence.SavedValues
import com.example.tgmuresproblema.data.startup.StartupFlow
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import org.koin.core.KoinComponent
import org.koin.core.inject

/**
 * Class comment here
 *
 * @author Arnold Baroti
 * @since 05/17/2020
 *
 */

class MainPresenter(view: MainContract.View) : MainContract.Presenter(view), KoinComponent {

    private val startupFlow: StartupFlow by inject()

    override fun doStartup() {
        startupFlow.executeStartup()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : Observer<Boolean> {
                override fun onComplete() {
                }

                override fun onSubscribe(d: Disposable?) {
                    compositeDisposable.add(d)
                }

                override fun onNext(success: Boolean?) {
                    if (success == true) {
                        view?.onStartupSuccessful(SavedValues.firstStart.getBoolean())
                    } else {
                        view?.onStartupFailed()
                    }
                }

                override fun onError(e: Throwable?) {
                    view?.onStartupFailed()
                }
            })
    }
}