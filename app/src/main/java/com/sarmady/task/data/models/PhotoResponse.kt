package com.sarmady.task.data.models

data class PhotoResponse(
    val photos: PhotoData
){
    data class PhotoData(
        val photo: MutableList<PhotoModel>
    )
}
