package com.sarmady.task.ui.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.sarmady.task.R
import com.sarmady.task.data.models.PhotoModel
import com.sarmady.task.databinding.ItemAdBinding
import com.sarmady.task.databinding.ItemPhotoBinding
import com.sarmady.task.utils.BaseViewHolder

class PhotoAdapter (private val listener: OnPhotoClickListener) : PagingDataAdapter<PhotoModel, BaseViewHolder<PhotoModel>>(
    ITEM_COMPARATOR
){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<PhotoModel> {
        return when (viewType){
            VIEW_TYPE_BANNER -> BannerViewHolder(ItemAdBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            else -> PhotoViewHolder(ItemPhotoBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        }
    }


    override fun onBindViewHolder(holder: BaseViewHolder<PhotoModel>, position: Int) {
        val  currentItem = getItem(position)

        if (currentItem != null){
            holder.bind(currentItem)
        }
    }

    override fun getItemViewType(position: Int): Int {

//        return if (position % 5 == 0) VIEW_TYPE_BANNER
//        else VIEW_TYPE_PHOTO

        return when (getItem(position)?.type) {
            "banner" -> VIEW_TYPE_BANNER
            else -> VIEW_TYPE_PHOTO
        }
    }


    inner class PhotoViewHolder(private val binding: ItemPhotoBinding) : BaseViewHolder<PhotoModel>(binding.root){

        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION){
                    val item = getItem(position)
                    if (item != null){
                        listener.onItemClick(item)
                    }
                }
            }
        }
        override fun bind(item: PhotoModel){
            binding.apply {
                val image = "https://farm${item.farm}.static.flickr.com/${item.server}/${item.id}_${item.secret}.jpg"
                Glide.with(itemView)
                    .load(image)
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.ic_error)
                    .into(imageView)


                textViewTitle.text = item.title ?: ""
            }
        }
    }


    inner class BannerViewHolder(private val binding: ItemAdBinding) : BaseViewHolder<PhotoModel>(binding.root){

        @SuppressLint("SetTextI18n")
        override fun bind(item: PhotoModel){
            //we can either bind to a random image or just sit a static one in the view adapter
            binding.apply {
                Glide.with(itemView)
                    .load(item.bannerImg)
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.ic_error)
                    .into(imageView)

                textViewTitle.text = "This is an AD"
            }
        }

    }


    interface OnPhotoClickListener{
        fun onItemClick(photo: PhotoModel)
    }


    companion object {
        private val ITEM_COMPARATOR = object : DiffUtil.ItemCallback<PhotoModel>(){
            override fun areItemsTheSame(oldItem: PhotoModel, newItem: PhotoModel): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: PhotoModel,
                newItem: PhotoModel
            ): Boolean {
                return oldItem == newItem
            }

        }


        const val VIEW_TYPE_PHOTO = 1
        const val VIEW_TYPE_BANNER = 2

    }

}