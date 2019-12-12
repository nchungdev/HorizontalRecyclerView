package com.nchungdev.horizontalrecyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SongAdapter(private val songList: List<Song>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        when (viewType) {
            4 -> {
                Song2ViewHolder(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.item_adtima,
                        parent,
                        false
                    )
                )
            }
            5 -> {
                Song2ViewHolder(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.item_banner,
                        parent,
                        false
                    )
                )
            }
            else -> SongViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_song,
                    parent,
                    false
                )
            )
        }

    override fun getItemCount() = songList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is Song2ViewHolder) {
            holder.bind(songList[position])
        } else if (holder is SongViewHolder) {
            holder.bind(songList[position])
        }
    }

    override fun getItemViewType(position: Int): Int {
        return position % 6
    }

    class SongViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView = itemView.findViewById<ImageView>(R.id.image_view)
        private val textTitle = itemView.findViewById<TextView?>(R.id.text_title)
        private val textArtist = itemView.findViewById<TextView?>(R.id.text_artist)

        fun bind(song: Song) {
            imageView.setImageURI(song.cover)
            textTitle?.text = song.title
            textArtist?.text = song.artist
        }
    }

    class Song2ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView = itemView.findViewById<ImageView>(R.id.image_adtima)

        fun bind(song: Song) {
            imageView.setImageURI(song.cover)
        }
    }
}

