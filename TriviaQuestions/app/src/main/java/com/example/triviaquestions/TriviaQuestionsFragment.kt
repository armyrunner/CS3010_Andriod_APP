package com.example.triviaquestions

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout

import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView



private const val TAG = "TriviaQuestionsFragment"

class TriviaQuestionsFragment: Fragment() {

    private lateinit var triviaItemsViewModel: TriviaViewModel
    private lateinit var triviaRecyclerView: RecyclerView
    private var adapter: TriviaAdapter? = TriviaAdapter(emptyList())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        triviaItemsViewModel =
            ViewModelProviders.of(this).get(TriviaViewModel::class.java)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_trivia_questions,container,false)

        triviaRecyclerView = view.findViewById(R.id.trivia_recycler_view) as RecyclerView
        triviaRecyclerView.layoutManager = LinearLayoutManager(context)
        triviaRecyclerView.adapter = adapter

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        triviaItemsViewModel.triviaItemsLiveData.observe(
            viewLifecycleOwner,
            Observer { triviaItems->
                Log.d(TAG,"Have trivia items from view Model $triviaItems")
                triviaRecyclerView.adapter = TriviaAdapter(triviaItems)

            }
        )

    }


    private inner class TriviaHolder(itemView: View)
        :RecyclerView.ViewHolder(itemView){

        private val itemView: View = itemView

        val categoryTextView: TextView = itemView.findViewById(R.id.category_textView)
        val questionTextView: TextView = itemView.findViewById(R.id.question_textView)

        val datasound: ConstraintLayout = itemView.findViewById(R.id.data_view_holder)

    }

    private inner class TriviaAdapter(var triviaItems: List<TriviaItems>)
        :RecyclerView.Adapter<TriviaHolder>(){

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
                : TriviaHolder {
          val view = layoutInflater.inflate(R.layout.trivia_list_item,parent,false)

            return TriviaHolder(view)
        }

        override fun getItemCount(): Int {
            return triviaItems.size
        }

        override fun onBindViewHolder(holder: TriviaHolder, position: Int) {
            val triviaItems = triviaItems[position]
           holder.apply {
               categoryTextView.text = triviaItems.category
               questionTextView.text = triviaItems.question

                datasound.setOnClickListener {
                    var beatBox = sounds(parentActivity.assets)
                    var sounds = beatBox.loadSounds()

                    beatBox.play(sounds[0])

                }
            }
        }
    }

    companion object{
        fun newInstance() = TriviaQuestionsFragment()
        lateinit var parentActivity: Activity
    }
}