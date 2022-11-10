package com.sarmady.task.data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PhotoModel(
    var farm: Int?,
    var id: String?,
    var isfamily: Int?,
    var isfriend: Int?,
    var ispublic: Int?,
    var owner: String?,
    var secret: String?,
    var server: String?,
    var title: String?,
    var type: String? = "photo",
    var bannerImg: String? = "https://i.dawn.com/large/2021/10/61761cf2df5a6.jpg" //just a random ad
): Parcelable {

    constructor(type: String):
            this (null, null, null, null, null, null, null, null, null, type)


}