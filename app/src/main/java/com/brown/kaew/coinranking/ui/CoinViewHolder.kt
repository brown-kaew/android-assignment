package com.brown.kaew.coinranking.ui

import android.graphics.drawable.PictureDrawable
import android.net.Uri
import android.util.Log
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
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.ImageViewTarget
import com.bumptech.glide.request.target.Target

class CoinViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val context = view.context
    private val imgIcon = view.findViewById<ImageView>(R.id.imgIcon)
    private val name = view.findViewById<TextView>(R.id.name)
    private val description = view.findViewById<TextView>(R.id.description)

    fun bind(coin: Coin?) {
        if (coin == null) {
            val resources = itemView.resources
            name.text = resources.getString(R.string.loading)
            description.text = resources.getString(R.string.loading)
        } else {
            showCoin(coin)
        }
    }

    private fun showCoin(coin: Coin) {
        name.text = coin.name
        description.text = coin.description
        var url = coin.iconUrl
        Log.d(javaClass.simpleName, "url = $url")
//        url = "https://upload.wikimedia.org/wikipedia/commons/0/05/Facebook_Logo_%282019%29.png"
        val uri = Uri.parse(url)

        if (!url.isNullOrBlank()) {

            if (url.endsWith(".svg", true)) {
                Log.d(javaClass.simpleName, "SVG image")
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
                Log.d(javaClass.simpleName, "Raster image")
                Glide.with(context.applicationContext)
                    .load(uri)
//                    .placeholder(R.drawable.image_loading)
                    .error(R.drawable.image_error)
                    .into(imgIcon)
            }

        }
    }

    companion object {
        fun create(parent: ViewGroup): CoinViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.coin_view_item, parent, false)
            return CoinViewHolder(view)
        }

        private val svgSoftwareLayerSetter = object : RequestListener<PictureDrawable> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<PictureDrawable>?,
                isFirstResource: Boolean
            ): Boolean {
                val view = (target as ImageViewTarget<*>).view
                view.setLayerType(ImageView.LAYER_TYPE_NONE, null)
                return false
            }

            override fun onResourceReady(
                resource: PictureDrawable?,
                model: Any?,
                target: Target<PictureDrawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {

                val view = (target as ImageViewTarget<*>).view
                view.setLayerType(ImageView.LAYER_TYPE_SOFTWARE, null)
                return false
            }

        }
    }
}