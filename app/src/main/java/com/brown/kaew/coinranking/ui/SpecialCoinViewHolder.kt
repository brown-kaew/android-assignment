package com.brown.kaew.coinranking.ui

import android.graphics.drawable.PictureDrawable
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.brown.kaew.coinranking.R
import com.brown.kaew.coinranking.data.Coin
import com.brown.kaew.coinranking.svg.GlideApp
import com.brown.kaew.coinranking.svg.SvgSoftwareLayerSetter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade

class SpecialCoinViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val context = view.context
    private val imgIcon = view.findViewById<ImageView>(R.id.imgIcon)
    private val name = view.findViewById<TextView>(R.id.name)

    fun bind(coin: Coin?) {
        if (coin == null) {
            val resources = itemView.resources
            name.text = resources.getString(R.string.loading)
        } else {
            showCoin(coin)
        }
    }

    private fun showCoin(coin: Coin) {
        name.text = coin.name
        val url = coin.iconUrl

        if (!url.isNullOrBlank()) {
            val uri = Uri.parse(url)

            if (url.endsWith(".svg", true)) {
//                Log.d(javaClass.simpleName, "SVG image")
                imgIcon.scaleType = ImageView.ScaleType.FIT_XY
                GlideApp.with(context.applicationContext)
                    .`as`(PictureDrawable::class.java)
//                        .placeholder(R.drawable.image_loading)
                    .error(R.drawable.image_error)
                    .transition(withCrossFade())
                    .listener(SvgSoftwareLayerSetter())
                    .load(uri)
                    .into(imgIcon)
            } else {
//                Log.d(javaClass.simpleName, "Raster image")
                Glide.with(context.applicationContext)
                    .load(uri)
//                    .placeholder(R.drawable.image_loading)
                    .error(R.drawable.image_error)
                    .into(imgIcon)
            }
        }
    }

    companion object {
        fun create(parent: ViewGroup, viewType: Int): SpecialCoinViewHolder {
            val layout = R.layout.special_coin_view_item

            val view = LayoutInflater.from(parent.context)
                .inflate(layout, parent, false)

            return SpecialCoinViewHolder(view)
        }
    }
}