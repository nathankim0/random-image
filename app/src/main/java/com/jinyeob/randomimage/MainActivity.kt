package com.jinyeob.randomimage

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.jinyeob.randomimage.common.LoadingStatus
import com.jinyeob.randomimage.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        init()

        setContentView(binding.root)
    }

    private fun init() {
        lifecycleScope.launch {
            mainViewModel.randomImageState.collect { image ->
                setImageGlide(image.url)
            }
        }

        lifecycleScope.launch {
            mainViewModel.getRandomImageLoadingStatus.collect { status ->
                when (status) {
                    LoadingStatus.LOADING -> {
                        binding.swipeLayout.isRefreshing = true
                    }
                    LoadingStatus.SUCCESS -> {
                        binding.swipeLayout.isRefreshing = false
                    }
                    LoadingStatus.FAILURE -> {
                        binding.swipeLayout.isRefreshing = false
                    }
                    else -> {
                    }
                }
            }
        }

        binding.swipeLayout.setOnRefreshListener {
            mainViewModel.refreshRandomImage()
        }
    }

    private fun setImageGlide(imageUrl: String) {
        Glide.with(this@MainActivity)
            .asBitmap()
            .load(imageUrl)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(
                    resource: Bitmap,
                    transition: Transition<in Bitmap>?
                ) {
                    binding.imageView.setImageBitmap(resource)
                    Palette.from(resource).generate { palette ->
                        val color = palette?.getDominantColor(0)
                        binding.root.setBackgroundColor(color ?: 0)
                    }
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                }
            })
    }
}
