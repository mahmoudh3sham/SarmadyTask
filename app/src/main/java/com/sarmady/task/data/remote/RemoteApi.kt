package com.sarmady.task.data.remote

import com.sarmady.task.data.models.PhotoResponse
import com.sarmady.task.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Query

interface RemoteApi {

    @GET("services/rest")
    suspend fun getPhotos(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
        @Query("text") query: String? = "Color",
        @Query("method") method: String? = "flickr.photos.search",
        @Query("api_key") api: String? = Constants.API_KEY,
        @Query("format") format: String? = "json",
        @Query("nojsoncallback") no_json: Int? = 50,
        ) : PhotoResponse
}