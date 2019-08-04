package com.koraextra.app.ui

import androidx.lifecycle.ViewModel
import com.koraextra.app.utily.Injector
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

open class KoraViewModel: ViewModel() {
    val dispatcherProvider = Injector.getCoroutinesDispatcherProvider()

    private val job = Job()
    val scope = CoroutineScope(Dispatchers.Main + job)

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

}