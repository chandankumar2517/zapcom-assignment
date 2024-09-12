package com.sample.zap.core.bases

import com.sample.zap.core.models.Output
import kotlinx.coroutines.*

abstract class BaseUseCase<Type : Any> : UseCaseLifeCycle {

    private lateinit var parentJob: Job

    abstract suspend fun run(): Output<Type>

    operator fun invoke(onResult: (Output<Type>) -> Unit = {}) {

        parentJob = GlobalScope.launch(Dispatchers.Main) {


            onResult(Output.Loading())

            val remoteJob = withContext(Dispatchers.IO) {

                run()
            }

            onResult(remoteJob)

        }
    }

    override fun onCleared() {
        parentJob.cancel()
    }
}

interface UseCaseLifeCycle {

    fun onCleared()
}

class None




