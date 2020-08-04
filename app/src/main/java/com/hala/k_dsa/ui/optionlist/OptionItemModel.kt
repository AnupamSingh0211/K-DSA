package com.hala.k_dsa.ui.optionlist

import android.os.Parcel
import android.os.Parcelable

/**
 * @author Anupam Singh
 * @since 04/08/20
 */

data class OptionItemModel(val key: String?, val value: Any?, val path: String? = null) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(key)
        parcel.writeString(path)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<OptionItemModel> {
        override fun createFromParcel(parcel: Parcel): OptionItemModel {
            return OptionItemModel(parcel)
        }

        override fun newArray(size: Int): Array<OptionItemModel?> {
            return arrayOfNulls(size)
        }
    }
}