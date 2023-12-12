package uz.xd.tasksapp.ui.fragments.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import uz.xd.tasksapp.R
import uz.xd.tasksapp.databinding.FragmentThirdBinding

class ThirdFragment : Fragment() {
    private lateinit var binding: FragmentThirdBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentThirdBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.skipTv.setOnClickListener {
            findNavController().navigate(R.id.action_thirdFragment_to_mainFragment)
        }
        binding.nextTv.setOnClickListener {
            findNavController().navigate(R.id.action_thirdFragment_to_mainFragment)
        }
        binding.backTv.setOnClickListener {
            activity?.onBackPressed()
        }
    }
}