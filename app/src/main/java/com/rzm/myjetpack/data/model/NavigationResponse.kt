package com.rzm.myjetpack.data.model

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@SuppressLint("ParcelCreator")
@Parcelize
data class NavigationResponse(var articles: ArrayList<ArticleResponse>,
                              var cid: Int,
                              var name: String) : Parcelable
