package com.esafirm.sample

import paperparcel.PaperParcel
import paperparcel.PaperParcelable
import java.io.Serializable

@PaperParcel
data class DetailProps(val image: String, val caption: String) : PaperParcelable {
    companion object {
        @JvmField val CREATOR = PaperParcelDetailProps.CREATOR
    }
}

data class DetailPropsSerializeable(val image: String, val caption: String) : Serializable
