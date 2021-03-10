package com.example.starwarscharacters

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

private const val TAG = "StarWarsGalleryFragment"

class StarWarsGalleryFragment : Fragment(){
    private lateinit var starWarsRecyclerView: RecyclerView
    private lateinit var starWarsPHotosViewModel: StarWarsPHotosViewModel
    private var adapter: PhotoAdapter? = PhotoAdapter(emptyList())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_star_wars_gallery,container,false)

        starWarsRecyclerView = view.findViewById(R.id.starwars_recycler_view)
        starWarsRecyclerView.layoutManager = LinearLayoutManager(context)
        starWarsRecyclerView.adapter = adapter

        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        starWarsPHotosViewModel=
            ViewModelProviders.of(this).get(StarWarsPHotosViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        starWarsPHotosViewModel.starWarsItemsLiveData.observe(
            viewLifecycleOwner,
            Observer {starWarsItems->
                Log.d(TAG,"Have star wars items from view model $starWarsItems")
                starWarsRecyclerView.adapter = PhotoAdapter(starWarsItems)
                starWarsRecyclerView.layoutManager = LinearLayoutManager(context)
                starWarsRecyclerView.adapter = adapter
            }
        )
    }

    private inner class PhotoHolder(itemView: View)
        :RecyclerView.ViewHolder(itemView){

        private val itemView: View = itemView

        val characterTextView: TextView = itemView.findViewById(R.id.character_textview)
        val bithdateTextView: TextView = itemView.findViewById(R.id.birth_textView)
    }

    private inner class PhotoAdapter(var starWarsItems: List<StarWarsItems>)
        :RecyclerView.Adapter<PhotoHolder>(){

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
                : PhotoHolder {
            val view = layoutInflater.inflate(R.layout.star_wars_list,parent,false)

            return PhotoHolder(view)
        }

        override fun getItemCount(): Int {
            return starWarsItems.size
        }

        override fun onBindViewHolder(holder: PhotoHolder, position: Int) {
            val starWarsItems = starWarsItems[position]
            holder.apply {
                characterTextView.text = starWarsItems.name
                bithdateTextView.text = starWarsItems.birth_year

            }
        }
    }

    companion object{
        fun newInstance() = StarWarsGalleryFragment()
    }
}