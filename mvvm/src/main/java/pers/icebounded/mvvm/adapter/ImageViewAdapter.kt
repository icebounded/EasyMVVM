package pers.icebounded.mvvm.adapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import pers.icebounded.lib.util.dp2px

/**
 * Created by Andy
 * on 2021/3/5
 */
object ImageViewAdapter {
    @kotlin.jvm.JvmStatic
    @BindingAdapter(
        value = ["image_url",
            "image_holder",
            "image_round",
            "image_round_top_left",
            "image_round_top_right",
            "image_round_bottom_left",
            "image_round_bottom_right"],
        requireAll = false
    )
    fun setImageUri(
        imageView: ImageView,
        image_url: String?,
        image_holder: Int,
        image_round: Float,
        image_round_top_left: Float,
        image_round_top_right: Float,
        image_round_bottom_left: Float,
        image_round_bottom_right: Float
    ) {

        when {
            image_url.isNullOrEmpty() -> imageView.setImageResource(image_holder)

            image_round > 0 -> {
                Glide.with(imageView)
                    .load(image_url)
                    .placeholder(image_holder)
                    .error(image_holder)
                    .transform(CenterCrop(), RoundedCorners(dp2px(image_round)))
                    .into(imageView)
            }

            (image_round_top_left > 0
                    || image_round_top_right > 0f
                    || image_round_bottom_left > 0f
                    || image_round_bottom_right > 0f) -> {
                Glide
                    .with(imageView)
                    .load(image_url)
                    .placeholder(image_holder)
                    .error(image_holder)
                    .transform(
                        CenterCrop(),
                        GranularRoundedCorners(
                            dp2px(image_round_top_left).toFloat(),
                            dp2px(image_round_top_right).toFloat(),
                            dp2px(image_round_bottom_left).toFloat(),
                            dp2px(image_round_bottom_right).toFloat()
                        )
                    ).into(imageView)
            }

            else -> {
                Glide.with(imageView).load(image_url).placeholder(image_holder).error(image_holder).into(imageView)
            }

        }
    }

}