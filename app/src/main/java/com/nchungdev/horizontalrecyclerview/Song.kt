package com.nchungdev.horizontalrecyclerview

import android.net.Uri
import android.os.Parcel
import android.os.Parcelable

data class Song(
    val title: String = "",
    val album: String = "",
    val artist: String = "",
    val cover: Uri = Uri.EMPTY,
    val data: Uri = Uri.EMPTY
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readParcelable(Uri::class.java.classLoader) ?: Uri.EMPTY,
        parcel.readParcelable(Uri::class.java.classLoader) ?: Uri.EMPTY
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        with(parcel) {
            writeString(title)
            writeString(album)
            writeString(artist)
            writeParcelable(cover, flags)
            writeParcelable(data, flags)
        }
    }

    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<Song> {
        val EMPTY_SONG = Song()

        override fun createFromParcel(parcel: Parcel) = Song(parcel)

        override fun newArray(size: Int) = arrayOfNulls<Song?>(size)
    }
}