package com.sarmady.task.ui.full_screen

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.sarmady.task.R
import com.sarmady.task.data.models.PhotoModel
import com.sarmady.task.databinding.ActivityFullScreenBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FullScreenActivity : AppCompatActivity() {

    lateinit var binding: ActivityFullScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFullScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val photo = intent.getParcelableExtra<PhotoModel>("photo")
        val image = "https://farm${photo?.farm}.static.flickr.com/${photo?.server}/${photo?.id}_${photo?.secret}.jpg"

        Glide.with(this)
            .load(image)
            .centerCrop()
            .transition(DrawableTransitionOptions.withCrossFade())
            .error(R.drawable.ic_error)
            .into(binding.imageView)
    }

    companion object {
        fun newIntent(context: Context?): Intent {
            return Intent(context, FullScreenActivity::class.java)
        }
    }
}