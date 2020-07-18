package com.brown.kaew.coinranking.svg

import android.content.Context
import android.graphics.drawable.PictureDrawable
import android.util.Log
import com.bumptech.glide.load.Options
import com.bumptech.glide.load.engine.Resource
import com.bumptech.glide.load.resource.SimpleResource
import com.bumptech.glide.load.resource.transcode.ResourceTranscoder
import com.caverock.androidsvg.SVG

/**
 * Convert the {@link SVG}'s internal representation to an Android-compatible one ({@link Picture}).
 */
class SvgDrawableTranscoder(context: Context) : ResourceTranscoder<SVG, PictureDrawable> {
    override fun transcode(
        toTranscode: Resource<SVG?>,
        options: Options
    ): Resource<PictureDrawable>? {

        val svg = toTranscode.get()
        val picture = svg.renderToPicture()
        Log.d(javaClass.simpleName,"toTranscode= $picture")

        val drawable = PictureDrawable(picture)
        return SimpleResource(drawable)
    }
}