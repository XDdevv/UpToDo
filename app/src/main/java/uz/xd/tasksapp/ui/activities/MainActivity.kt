package uz.xd.tasksapp.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController
import uz.xd.tasksapp.R
import uz.xd.tasksapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var fragmentChanger = findViewById<View>(R.id.container)
        fragmentChanger.findNavController()
            .addOnDestinationChangedListener { _, dest, _ ->
                when (dest.id) {
                    R.id.mainFragment, R.id.calendarFragment2 -> {
                        binding.bottomNavigationView.visibility = View.VISIBLE
                    }

                    else -> {
                        binding.bottomNavigationView.visibility = View.GONE
                    }
                }
            }

        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.mainFragment -> {
                    findNavController(R.id.container).navigate(R.id.mainFragment)
                }

                R.id.calendarFragment2 -> {
                    findNavController(R.id.container).navigate(R.id.calendarFragment2)
                }

                else -> false
            }
            true
        }
    }
}