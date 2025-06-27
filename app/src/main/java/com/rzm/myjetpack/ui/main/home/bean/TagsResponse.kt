package com.rzm.myjetpack.ui.main.home.bean

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TagsResponse(var name: String, var url: String) : Parcelable
