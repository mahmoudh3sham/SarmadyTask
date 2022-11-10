package com.sarmady.task.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.sarmady.task.data.remote.RemoteApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataRepository @Inject constructor(private val remoteApi: RemoteApi) {

    fun getPhotosResults() =
        Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 100,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { DataPagingSource(remoteApi) }
        ).liveData

}