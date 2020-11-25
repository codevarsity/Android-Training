package com.example.photosclient

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

class PhotoHolder(view: View ) : RecyclerView.ViewHolder(view) {
    var imageView = view.findViewById<ImageView>(R.id.photoImageView)

}

class PhotosAdapter(var items:List<PhotoItem>,
                    var context:Context,
                    var downloaderThread:DownloaderThread) : RecyclerView.Adapter<PhotoHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.cell, parent, false)
        return PhotoHolder(view)
    }

    override fun onBindViewHolder(holder: PhotoHolder, position: Int) {
        holder.imageView.setImageResource(R.drawable.ic_outline_image_24)
        val photoItem = items.get(position)
        downloaderThread.enqueueDownload(holder, photoItem.url_s)


    }

    override fun onViewRecycled(holder: PhotoHolder) {
        super.onViewRecycled(holder)
        downloaderThread.enqueueDownload(holder, null)
    }

    override fun getItemCount(): Int = items.count()
}