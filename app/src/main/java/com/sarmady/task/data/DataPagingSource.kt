package com.sarmady.task.data

import android.util.Log
import androidx.paging.PagingSource
import com.sarmady.task.data.models.PhotoModel
import com.sarmady.task.data.remote.RemoteApi
import com.sarmady.task.utils.Constants.Companion.STARTING_PAGE_INDEX
import retrofit2.HttpException
import java.io.IOException

class DataPagingSource(
    private val remoteApi: RemoteApi
) : PagingSource<Int, PhotoModel> () {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PhotoModel> {
        val position = params.key ?: STARTING_PAGE_INDEX

        return try {
            val response = remoteApi.getPhotos(page = STARTING_PAGE_INDEX, perPage = params.loadSize)
            val results = response.photos.photo


            //adding ads every 5 items
            val resultsWithAds = mutableListOf<PhotoModel>()
            for (item in results){
                val pos = results.indexOf(item)
                Log.e("Pos", "load: $pos")

                if (pos > 0 && pos % 5 == 0) {
                    Log.e("Pos", "load: Ad Added $pos")
                    val bannerItem = PhotoModel("banner")
                    resultsWithAds.add(bannerItem)
                }
                resultsWithAds.add(item)
            }


            LoadResult.Page(
                data = resultsWithAds,
                prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (resultsWithAds.isEmpty()) null else position + 1
            )
        }catch (ex: IOException){
            LoadResult.Error(ex)
        }catch (ex: HttpException){
            LoadResult.Error(ex)
        }

    }

}