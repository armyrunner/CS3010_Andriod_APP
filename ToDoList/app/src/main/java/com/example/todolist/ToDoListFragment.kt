package com.example.todolist

import android.content.ContentValues.TAG
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.lifecycle.Observer
import java.text.SimpleDateFormat
import java.util.*



private const val TAG = "ToDoListFragment"


class ToDoListFragment : Fragment() {

    /**
     * Required interface for hosting activities
     *
     */
    interface Callbacks{
        fun onTaskSelected(taskid: UUID)
    }

    private var callbacks: Callbacks? = null

    private lateinit var taskRecyclerView: RecyclerView
//    private var adapter: TaskAdapter? = null
    private var adapter: TaskAdapter? = TaskAdapter(emptyList())

    private val todoListViewModel: ToDoListViewModel by lazy{
        ViewModelProviders.of(this).get(ToDoListViewModel::class.java)
    }

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        Log.d(TAG,"Total Tasks is : ${ToDoListViewModel.tasks.size}")
//
//    }

    override fun onAttach(context: Context){
        super.onAttach(context)
        callbacks = context as Callbacks?
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_task_list, container, false)

        taskRecyclerView =
            view.findViewById(R.id.task_recycler_view) as RecyclerView
        taskRecyclerView.layoutManager = LinearLayoutManager(context)
        taskRecyclerView.adapter = adapter

//        updateUI()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        todoListViewModel.taskListLiveData.observe(
            viewLifecycleOwner,
            Observer { tasks ->
                tasks?.let {
                    Log.i(TAG,"Got Tasks ${tasks.size}")
                    updateUI(tasks)
                }
            }
        )
    }

    override fun onDetach(){
        super.onDetach()
        callbacks = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragment_task_list,menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.new_task ->{
                val task = Task()
                todoListViewModel.addTask(task)
                callbacks?.onTaskSelected(task.id)
                true
            }
            else-> return super.onOptionsItemSelected(item)
        }
    }

//    private fun updateUI(){
//        val tasks = ToDoListViewModel.tasks
//        adapter = TaskAdapter(tasks)
//        taskRecyclerView.adapter = adapter
//
//    }

    private fun updateUI(tasks:List<Task>){
        adapter = TaskAdapter(tasks)
        taskRecyclerView.adapter = adapter

    }

    private inner class TaskHolder(view:View)
        :RecyclerView.ViewHolder(view),View.OnClickListener{

        private lateinit var task: Task

        private val titleTextView: TextView = itemView.findViewById(R.id.task_title)
        private val descriptionTextView:TextView =itemView.findViewById(R.id.task_description)
        private val dateTextView: TextView = itemView.findViewById(R.id.task_date)
        private val completed: ImageView = itemView.findViewById(R.id.image_complete)

        init{
            itemView.setOnClickListener(this)
        }


        fun bind(task: Task){

          //  val myFormat = SimpleDateFormat("MMM/DD/YYYY",Locale.US)


            this.task = task
            titleTextView.text = this.task.title
            descriptionTextView.text = this.task.description
            dateTextView.text = this.task.date.toString()
            completed.visibility = if(task.isDone){
                View.VISIBLE
            }else{
                View.GONE
            }
        }

        override fun onClick(v: View){
//            Toast.makeText(context,"${task.title} pressed!", Toast.LENGTH_SHORT)
//                .show()
            callbacks?.onTaskSelected(task.id)
        }


    }

    private inner class TaskAdapter(var tasks: List<Task>)
        :RecyclerView.Adapter<TaskHolder>(){

        override fun onCreateViewHolder(parent: ViewGroup,viewType: Int)
                : TaskHolder {
            val view = layoutInflater.inflate(R.layout.list_item,parent,false)
            return TaskHolder(view)

        }

        override fun getItemCount() = tasks.size

        override fun onBindViewHolder(holder: TaskHolder, position: Int) {
            val task = tasks[position]
//            holder.apply{
//                titleTextView.text = task.title;
//                descriptionTextView.text = task.description
//                dateTextView.text= task.date.toString()
//            }
            holder.bind(task)
        }
    }

    companion object{
        fun newInstance(): ToDoListFragment{
            return ToDoListFragment()
        }

    }

}