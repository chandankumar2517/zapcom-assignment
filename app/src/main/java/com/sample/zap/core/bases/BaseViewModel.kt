package com.sample.zap.core.bases

import androidx.lifecycle.ViewModel

open class BaseViewModel(private var useCaseLifeCycle : UseCaseLifeCycle) : ViewModel() {

    override fun onCleared() {

        super.onCleared()

        useCaseLifeCycle.onCleared()
    }
}