package uz.xd.tasksapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import uz.xd.tasksapp.R
import uz.xd.tasksapp.adapters.TasksAdapter
import uz.xd.tasksapp.databinding.FragmentCalendarBinding
import uz.xd.tasksapp.db.TasksDatabase
import uz.xd.tasksapp.db.entities.Task

class CalendarFragment : Fragment() {

    private lateinit var binding: FragmentCalendarBinding
    private lateinit var list: List<Task>
    private var today: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCalendarBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.calendarCv.setOnDateChangeListener { _, _, _, dayOfMonth ->
            today = dayOfMonth.toString()
            list = TasksDatabase.getInstance(requireActivity()).tasksDatabase()
                .getAllTasksByDayNOT(dayOfMonth.toString())

            binding.listRv.adapter = TasksAdapter(requireActivity(), list,
                object : TasksAdapter.CallBack {
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

                    }

                })
        }
        binding.todayBtn.setOnClickListener {
            list = TasksDatabase.getInstance(requireActivity()).tasksDatabase()
                .getAllTasksByDayNOT(today)


            binding.listRv.adapter = TasksAdapter(requireActivity(), list,
                object : TasksAdapter.CallBack {
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
                        TasksDatabase.getInstance(requireActivity()).tasksDatabase()
                            .getAllTasksByFlag()[position].completed = true
                    }

                })
        }

        binding.completedBtn.setOnClickListener {
            list = TasksDatabase.getInstance(requireActivity()).tasksDatabase()
                .getAllTasksByDay(today)

            binding.listRv.adapter = TasksAdapter(requireActivity(), list,
                object : TasksAdapter.CallBack {
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

                    }

                })
        }
    }
}