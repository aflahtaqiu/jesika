package id.koridor50.jesika.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import id.koridor50.jesika.R

object BindingUtil {

    @JvmStatic
    @BindingAdapter("imageUrlCrop")
    fun setImageUrlCrop(imageView: ImageView, url: String?) {
        Glide.with(imageView.context).load(url)
            .error(
                R.drawable.no_image)
            .into(imageView)
    }
}