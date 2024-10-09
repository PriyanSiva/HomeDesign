package com.example.homedesign.models

import android.os.Parcel
import android.os.Parcelable

data class Furniture(
    val id: String = "",
    val name: String = "",
    val price: Double = 0.0,
    val details: String = "",
    val image: String = "",
    val roomType: String = ""  // e.g., Bedroom, Living Room, Kitchen
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readDouble(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeDouble(price)
        parcel.writeString(details)
        parcel.writeString(image)
        parcel.writeString(roomType)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Furniture> {
        override fun createFromParcel(parcel: Parcel): Furniture {
            return Furniture(parcel)
        }

        override fun newArray(size: Int): Array<Furniture?> {
            return arrayOfNulls(size)
        }
    }
}
