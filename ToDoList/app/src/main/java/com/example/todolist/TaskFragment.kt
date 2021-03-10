package com.example.todolist


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import java.util.*

private const val ARG_TASK_ID = "task_id"
private const val TAG = "TaskFragment"
private const val  DIALOG_DATE = "DialogDate"
private const val REQUEST_DATE = 0;

class TaskFragment : Fragment(),DatePickerFragment.Callbacks {

    private lateinit var task: Task
    private lateinit var titleField: EditText
    private lateinit var dateButton: Button
    private lateinit var deleteButton: Button
    private lateinit var descriptField: EditText
    private lateinit var solvedCheckBox: CheckBox

    private val taskDetailViewModel: TaskDetailViewModel by lazy{
        ViewModelProviders.of(this).get(TaskDetailViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        task = Task()
        val taskId: UUID = arguments?.getSerializable(ARG_TASK_ID) as UUID
//        Log.d(TAG,"args bundle task ID $taskId")
        //Eventually, load task from database
        taskDetailViewModel.loadTask((taskId))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_task,container,false)
        titleField = view.findViewById(R.id.edit_title) as EditText
        dateButton = view.findViewById(R.id.date_button) as Button
        descriptField = view.findViewById(R.id.edit_list) as EditText
        deleteButton = view.findViewById(R.id.btn_delete) as Button
        solvedCheckBox = view.findViewById(R.id.check_complete) as CheckBox

//        dateButton.apply{
//            text = task.date.toString()
//            isEnabled = false
//        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        taskDetailViewModel.taskLiveData.observe(
            viewLifecycleOwner,
            Observer { task ->
                task?.let{
                    this.task = task
                    updateUI()
                }
            })
    }



    private fun updateUI(){
        titleField.setText(task.title)
        descriptField.setText(task.description)
        dateButton.text = task.date.toString()
        solvedCheckBox.apply{
            isChecked = task.isDone
            jumpDrawablesToCurrentState()
        }
    }

    override fun onStart() {
        super.onStart()

        val titleWatcher = object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
                // Keep Blank
            }


            override fun onTextChanged(
                s: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
                task.title = s.toString()
            }

            override fun afterTextChanged(s: Editable?) {
                // Keep Blank
            }
        }

        val descriptionWatcher = object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
                // Keep Blank
            }


            override fun onTextChanged(
                s: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
                task.description = s.toString()
            }

            override fun afterTextChanged(s: Editable?) {
                // Keep Blank
            }
        }


        titleField.addTextChangedListener(titleWatcher)
        descriptField.addTextChangedListener(descriptionWatcher)


        solvedCheckBox.apply {
            setOnCheckedChangeListener { _, isChecked ->
                task.isDone = isChecked
            }
        }

        dateButton.setOnClickListener {
            DatePickerFragment.newInstance(task.date).apply {
                setTargetFragment(this@TaskFragment, REQUEST_DATE)
                show(this@TaskFragment.requireFragmentManager(), DIALOG_DATE)
            }


        }

        deleteButton.setOnClickListener {
             taskDetailViewModel.deleteTask(task)
             activity?.onBackPressed()

        }

    }



    override fun onStop(){
        super.onStop()
        taskDetailViewModel.saveTask(task)
    }



    companion object{
        fun newInstance(taskId: UUID): TaskFragment{
            val args =Bundle().apply{
                putSerializable(ARG_TASK_ID, taskId)
            }
            return TaskFragment().apply{
                arguments = args
            }
        }
    }

    override fun onDateSelected(date: Date) {
        task.date = date
        updateUI()
    }
}

