package uz.xd.tasksapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import uz.xd.tasksapp.R
import uz.xd.tasksapp.databinding.FragmentTaskScreenBinding
import uz.xd.tasksapp.db.TasksDatabase

class TaskScreenFragment : Fragment() {
    private lateinit var binding: FragmentTaskScreenBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTaskScreenBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val position = arguments?.getInt("position")
        val title = arguments?.getString("title")
        val description = arguments?.getString("description")
        val time = arguments?.getString("time")
        val flag = arguments?.getString("flag")
        val category = arguments?.getString("category")

        binding.titleTv.text = title
        binding.descriptionTv.text = description
        binding.timeBtn.text = time.toString()
        binding.flagBtn.text = flag
        binding.categoryBtn.text = category

        binding.closeIv.setOnClickListener {
            findNavController().navigate(R.id.mainFragment)
        }

        binding.deleteIv.setOnClickListener {
            TasksDatabase.getInstance(requireActivity())
                .tasksDatabase()
                .deleteNote(
                    TasksDatabase.getInstance(requireActivity()).tasksDatabase()
                        .getAllTasksByFlag()[position!!]
                )
            activity?.onBackPressed()
        }
        binding.deleteTv.setOnClickListener {
            TasksDatabase.getInstance(requireActivity())
                .tasksDatabase()
                .deleteNote(
                    TasksDatabase.getInstance(requireActivity()).tasksDatabase()
                        .getAllTasksByFlag()[position!!]
                )
            activity?.onBackPressed()
        }
    }
}