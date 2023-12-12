package uz.xd.tasksapp.ui.fragments

import android.app.AlarmManager
import android.app.AlertDialog
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import uz.xd.tasksapp.R
import uz.xd.tasksapp.adapters.CategoryAdapter
import uz.xd.tasksapp.adapters.FlagsAdapter
import uz.xd.tasksapp.databinding.AlertCalendarBinding
import uz.xd.tasksapp.databinding.AlertCategoryBinding
import uz.xd.tasksapp.databinding.AlertCreateCategoryBinding
import uz.xd.tasksapp.databinding.AlertFlagBinding
import uz.xd.tasksapp.databinding.AlertTimeBinding
import uz.xd.tasksapp.databinding.FragmentAddTaskBinding
import uz.xd.tasksapp.db.TasksDatabase
import uz.xd.tasksapp.db.entities.Category
import uz.xd.tasksapp.db.entities.Task
import uz.xd.tasksapp.models.Flag
import uz.xd.tasksapp.utils.AlarmReceiver
import java.util.Calendar

class AddTaskFragment : Fragment() {

    private lateinit var binding: FragmentAddTaskBinding
    private lateinit var calendar: Calendar
    private lateinit var alarmManager: AlarmManager
    private lateinit var pendingIntent: PendingIntent

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentAddTaskBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    var title = ""
    var description = ""
    var date_time = ""
    var date_day = ""
    var date_dd = ""
    var date_full = ""
    var flag = ""
    var category = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        calendar = Calendar.getInstance()

        binding.sendIv.setOnClickListener {
            title = binding.titleEt.text.toString().trim()
            description = binding.descriptionEt.text.toString().trim()
            date_full = date_time
            if (title.isEmpty()) {
                Toast.makeText(activity, "Title Required", Toast.LENGTH_SHORT).show()
            } else if (description.isEmpty()) {
                Toast.makeText(activity, "Description Required", Toast.LENGTH_SHORT).show()
            } else if (date_time.isEmpty()) {
                Toast.makeText(activity, "Time Required", Toast.LENGTH_SHORT).show()
            } else if (flag.isEmpty()) {
                Toast.makeText(activity, "Priority Required", Toast.LENGTH_SHORT).show()
            } else if (category.isEmpty()) {
                Toast.makeText(activity, "Category Required", Toast.LENGTH_SHORT).show()
            } else {
                addTask()
            }
        }

        binding.timeIv.setOnClickListener {
            pickDay()
        }
        binding.categoryIv.setOnClickListener {
            pickCategory()
        }
        binding.flagIv.setOnClickListener {
            pickFlag()
        }
    }

    private fun pickCategory() {
        val dialog = AlertDialog.Builder(activity).create()
        val alertDialogCategoryBinding = AlertCategoryBinding.inflate(layoutInflater)
        dialog.setView(alertDialogCategoryBinding.root)

        alertDialogCategoryBinding.apply {
            listRv.adapter = CategoryAdapter(requireActivity(),
                TasksDatabase.getInstance(requireActivity()).categoryDatabase().getAllCategories(),
                object : CategoryAdapter.CallBack {
                    override fun item(position: Int) {
                        category = TasksDatabase.getInstance(requireActivity())
                            .categoryDatabase()
                            .getAllCategories()[position].title
                        dialog.dismiss()
                    }
                })

            addBtn.setOnClickListener {
                dialog.dismiss()
                openAddCategory()
            }
        }

        dialog.show()
    }

    private fun openAddCategory() {
        val dialog = AlertDialog.Builder(activity).create()
        val alertDialogAddCategoryBinding = AlertCreateCategoryBinding.inflate(layoutInflater)
        dialog.setView(alertDialogAddCategoryBinding.root)

        alertDialogAddCategoryBinding.apply {
            cancelBtn.setOnClickListener {
                dialog.dismiss()
            }
            saveBtn.setOnClickListener {
                val title1 = titleEt.text.toString().trim()

                TasksDatabase.getInstance(requireActivity())
                    .categoryDatabase()
                    .insertCategory(
                        Category(
                            title1,
                            colorPicker.color
                        )
                    )

                dialog.dismiss()

                pickCategory()
            }
        }

        dialog.show()
    }

    private fun pickFlag() {
        val dialog = AlertDialog.Builder(activity).create()
        val alertDialogFlagBinding = AlertFlagBinding.inflate(layoutInflater)
        dialog.setView(alertDialogFlagBinding.root)

        var flagI = 0

        alertDialogFlagBinding.apply {
            listRv.adapter =
                FlagsAdapter(requireActivity(), Flag.load(), object : FlagsAdapter.CallBack {
                    override fun item(position: Int) {
                        flagI = Flag.load()[position].number
                    }
                })
            cancelBtn.setOnClickListener {
                dialog.dismiss()
            }
            saveBtn.setOnClickListener {
                flag = flagI.toString()
                Toast.makeText(activity, flag, Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }
        }
        dialog.show()
    }

    private fun pickDay() {
        val dialog = AlertDialog.Builder(activity).create()
        val alertDialogCalendarBinding = AlertCalendarBinding.inflate(layoutInflater)
        dialog.setView(alertDialogCalendarBinding.root)

        alertDialogCalendarBinding.apply {
            calendarCv.setOnDateChangeListener { _, _, month, dayOfMonth ->

                var month_str = ""

                if (month == 0) month_str = "January"
                if (month == 1) month_str = "February"
                if (month == 2) month_str = "March"
                if (month == 3) month_str = "April"
                if (month == 4) month_str = "May"
                if (month == 5) month_str = "June"
                if (month == 6) month_str = "July"
                if (month == 7) month_str = "August"
                if (month == 8) month_str = "September"
                if (month == 9) month_str = "October"
                if (month == 10) month_str = "November"
                if (month == 11) month_str = "December"

                date_day = "$dayOfMonth"
                date_dd = "$month_str $dayOfMonth"
            }
            cancelBtn.setOnClickListener {
                dialog.dismiss()
            }
            saveBtn.setOnClickListener {
                dialog.dismiss()

                pickTime()
            }
        }
        dialog.show()
    }


    private fun pickTime() {
        val dialog = AlertDialog.Builder(activity).create()
        val alertDialogTimeBinding = AlertTimeBinding.inflate(layoutInflater)
        dialog.setView(alertDialogTimeBinding.root)

        alertDialogTimeBinding.apply {

            timepicker.setOnTimeChangedListener { _, hourOfDay, minute ->
                date_time = "$hourOfDay:$minute"
                calendar[Calendar.HOUR_OF_DAY] = hourOfDay
                calendar[Calendar.MINUTE] = minute
                calendar[Calendar.SECOND] = 0
                calendar[Calendar.MILLISECOND] = 0
            }

            cancelBtn.setOnClickListener {
                dialog.dismiss()
            }

            saveBtn.setOnClickListener {
                dialog.dismiss()
                date_full = "$date_dd at $date_time"
                Toast.makeText(activity, date_full, Toast.LENGTH_SHORT).show()
            }

        }
        dialog.show()
    }

    private fun addTask() {
        val task = Task(
            title,
            date_time,
            date_day,
            date_full,
            description,
            flag.toInt(),
            category,
            false
        )

        TasksDatabase.getInstance(requireActivity()).tasksDatabase().insertTask(task)

        alarmManager = activity?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(activity, AlarmReceiver::class.java)
        pendingIntent = PendingIntent.getBroadcast(activity, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP, calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY, pendingIntent
        )

        findNavController().navigate(R.id.mainFragment)

        /*
        Looper.getMainLooper()

        if (calendar.timeInMillis == System.currentTimeMillis()) {
        TasksDatabase.getInstance(requireActivity()).tasksDatabase().deleteNote(task)

        val taskCompleted = Task(
        title,
        date_time,
        date_day,
        date_full,
        description,
        flag.toInt(),
        category,
        true
        )

        TasksDatabase.getInstance(requireActivity()).tasksDatabase().insertTask(taskCompleted)
        */

    }

}