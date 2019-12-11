package com.nchungdev.horizontalrecyclerview

import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore

object DummyData {
    private val artworkUri = Uri.parse("content://media/external/audio/albumart")
    private val songUri = Uri.parse("content://media/external/audio/media")

    operator fun invoke(context: Context): List<Song> {
        val songList = mutableListOf<Song>()
        val contentResolver = context.contentResolver
        val uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        contentResolver.query(uri, null, null, null, null)?.use {
            while (it.moveToNext()) {
                val indices = ColumnIndices(it)
                songList.add(
                    Song(
                        it.getString(indices.title),
                        it.getString(indices.album),
                        it.getString(indices.artist),
                        makeCoverUri(it, indices.albumId),
                        makeSongUri(it, indices.id)
                    )
                )
            }
        }
        return songList
    }

    private fun makeCoverUri(cursor: Cursor, columnIndex: Int): Uri =
        ContentUris.withAppendedId(artworkUri, cursor.getLong(columnIndex))

    private fun makeSongUri(cursor: Cursor, columnIndex: Int) =
        ContentUris.withAppendedId(songUri, cursor.getLong(columnIndex))

    class ColumnIndices(cursor: Cursor) {
        val title = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)
        val id = cursor.getColumnIndex(MediaStore.Audio.Media._ID)
        val albumId = cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID)
        val artist = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)
        val album = cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM)
    }

}
