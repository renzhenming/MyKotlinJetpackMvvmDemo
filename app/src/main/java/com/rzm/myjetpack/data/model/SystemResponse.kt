package com.rzm.myjetpack.data.model

import android.annotation.SuppressLint
import android.os.Parcelable
import com.rzm.myjetpack.data.model.ClassifyResponse
import kotlinx.android.parcel.Parcelize


@SuppressLint("ParcelCreator")
@Parcelize
data class SystemResponse(var children: ArrayList<ClassifyResponse>,
                          var courseId: Int,
                          var id: Int,
                          var name: String,
                          var order: Int,
                          var parentChapterId: Int,
                          var userControlSetTop: Boolean,
                          var visible: Int) : Parcelable
