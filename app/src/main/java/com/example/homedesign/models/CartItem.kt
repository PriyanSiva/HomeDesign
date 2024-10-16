package com.example.homedesign.models

import android.os.Parcel
import android.os.Parcelable

data class CartItem(
    val userId: String = "",
    val furnitureId: String = "",
    val name: String = "",
    val price: Double = 0.0,
    val image: String = "",
    val quantity: Int = 1
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readDouble(),
        parcel.readString() ?: "",
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(userId)
        parcel.writeString(furnitureId)
        parcel.writeString(name)
        parcel.writeDouble(price)
        parcel.writeString(image)
        parcel.writeInt(quantity)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CartItem> {
        override fun createFromParcel(parcel: Parcel): CartItem {
            return CartItem(parcel)
        }

        override fun newArray(size: Int): Array<CartItem?> {
            return arrayOfNulls(size)
        }
    }
}

