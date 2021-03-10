package com.example.photogalleryactivity

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

private const val TAG = "PhotoGalleryFragment"

class PhotoGalleryFragment: Fragment() {

    private lateinit var photoGalleryViewModel: PhotoGalleryViewModel
    private lateinit var photoRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        photoGalleryViewModel=
            ViewModelProviders.of(this).get(PhotoGalleryViewModel::class.java )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_photo_gallery,container, false)

        photoRecyclerView = view.findViewById(R.id.photo_recycler_view)
        photoRecyclerView.layoutManager = GridLayoutManager(context,3)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        photoGalleryViewModel.galleryItemLiveData.observe(
            viewLifecycleOwner,
            Observer { galleryItems ->
                Log.d(TAG,"Have gallery items from ViewModel $galleryItems")
                photoRecyclerView.adapter = PhotoAdapter(galleryItems)
            }
        )
    }

    private class PhotoHolder(itemView: View)
        :RecyclerView.ViewHolder(itemView){

        private val itemView:View = itemView
        private val imageView:ImageView = itemView.findViewById(R.id.image_view)


        fun bindImage(url:String){
            Picasso.get().load(url).into(imageView)
        }
    }


    private inner class PhotoAdapter(private val galleryItems: List<GalleryItem>)
        :RecyclerView.Adapter<PhotoHolder>(){

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): PhotoHolder {
            val view = layoutInflater.inflate(R.layout.list_item_gallery,parent,false) as ImageView
            return PhotoHolder(view)
        }

        override fun getItemCount(): Int{
            return galleryItems.size
        }

        override fun onBindViewHolder(holder:PhotoHolder, position:Int) {
            val gallerItem = galleryItems[position]
            holder.bindImage(gallerItem.url)
        }
    }

    companion object{
        fun newInstance() = PhotoGalleryFragment()
    }
}