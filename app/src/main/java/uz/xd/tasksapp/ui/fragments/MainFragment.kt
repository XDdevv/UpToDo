package uz.xd.tasksapp.ui.fragments

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import uz.xd.tasksapp.R
import uz.xd.tasksapp.adapters.TasksAdapter
import uz.xd.tasksapp.databinding.FragmentMainBinding
import uz.xd.tasksapp.db.TasksDatabase
import uz.xd.tasksapp.db.entities.Task

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private lateinit var list: List<Task>
    private lateinit var adapter: TasksAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        createNotificationChannel()

        binding.addTaskBtn.setOnClickListener {
            findNavController().navigate(R.id.addTaskFragment)
        }
        binding.sortIv.setOnClickListener {
            findNavController().navigate(R.id.taskScreenFragment)
        }

        list = TasksDatabase.getInstance(requireActivity()).tasksDatabase().getAllTasksByFlag()
        try {
            adapter = TasksAdapter(requireActivity(), list, object : TasksAdapter.CallBack {
                override fun item(position: Int) {
                    val bundle = Bundle()

                    bundle.putString("title", list[position].title)
                    bundle.putString("description", list[position].description)
                    bundle.putString("time", list[position].dateFull)
                    bundle.putString("flag", list[position].flag.toString())
                    bundle.putString("category", list[position].category)

                    findNavController().navigate(R.id.taskScreenFragment, bundle)
                }

                override fun item_done(position: Int) {
                    TasksDatabase.getInstance(requireActivity()).tasksDatabase().getAllTasks()[position].completed = true
                    adapter.notifyDataSetChanged()
                }
            })

        } catch (e: Exception) {

        }
        binding.listToday.adapter = adapter
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name: CharSequence = "uptodoReminderChannel"
            val description = "Channel From Alarm Manager"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel("uptodo", name, importance)
            channel.description = description
            val notificationManager = requireActivity().getSystemService(
                NotificationManager::class.java
            )
            notificationManager.createNotificationChannel(channel)
        }
    }
}