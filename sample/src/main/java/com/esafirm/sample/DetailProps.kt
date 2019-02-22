package com.esafirm.sample

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

@Parcelize
data class DetailProps(val image: String, val caption: String) : Parcelable

data class DetailPropsSerializeable(val image: String, val caption: String) : Serializable
