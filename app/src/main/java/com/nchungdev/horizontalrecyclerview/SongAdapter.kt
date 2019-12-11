package com.nchungdev.horizontalrecyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SongAdapter(private val songList: List<Song>) :
    RecyclerView.Adapter<SongAdapter.SongViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        SongViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_song,
                parent,
                false
            )
        )

    override fun getItemCount() = songList.size

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        holder.bind(songList[position])
    }

    class SongViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView = itemView.findViewById<ImageView>(R.id.image_view)
        private val textTitle = itemView.findViewById<TextView>(R.id.text_title)
        private val textArtist = itemView.findViewById<TextView>(R.id.text_artist)

        fun bind(song: Song) {
            imageView.setImageURI(song.cover)
            textTitle.text = song.title
            textArtist.text = song.artist
        }
    }
}

