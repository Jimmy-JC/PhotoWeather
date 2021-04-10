package com.jimmy.weather.ui.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy.*
import com.jimmy.weather.R
import com.jimmy.weather.data.persistence.ImageEntity
import java.io.File

class ImagesAdapter(
    private val images: List<ImageEntity>,
    private val onItemClick: (image: ImageEntity) -> Unit
) :
    RecyclerView.Adapter<ImagesAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val temperature: TextView = view.findViewById(R.id.degree)
        val name: TextView = view.findViewById(R.id.name)
        val weather: TextView = view.findViewById(R.id.weather)
        val image: ImageView = view.findViewById(R.id.image)
        val icon: ImageView = view.findViewById(R.id.icon)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.image_list_item, viewGroup, false))


    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val image = images[position]
        val context = viewHolder.itemView.context
        viewHolder.weather.text = image.weather
        viewHolder.name.text =
            context.getString(R.string.place_name, image.placeName, image.condition)
        viewHolder.temperature.text = context.getString(R.string.degree, image.temperature)
        Glide.with(viewHolder.itemView).load(image.iconUrl).into(viewHolder.icon)
        Glide.with(viewHolder.itemView).load(File(image.path))
            .thumbnail(0.1f).diskCacheStrategy(ALL).into(viewHolder.image)
        viewHolder.itemView.setOnClickListener { onItemClick(image) }
    }

    override fun getItemCount() = images.size

}
