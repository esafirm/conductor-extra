package com.conductor.esafirm.com.sample

import paperparcel.PaperParcel
import paperparcel.PaperParcelable

@PaperParcel
data class DetailProps(val image: String, val caption: String) : PaperParcelable {
    companion object{
        @JvmField val CREATOR = PaperParcelDetailProps.CREATOR
    }
}
