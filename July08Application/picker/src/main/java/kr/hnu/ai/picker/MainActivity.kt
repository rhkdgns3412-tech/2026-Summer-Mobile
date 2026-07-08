package kr.hnu.ai.picker

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kr.hnu.ai.picker.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.datePicker.visibility = View.VISIBLE
        binding.timePicker.visibility = View.GONE
        binding.dateBtn.isEnabled = false
        binding.timeBtn.isEnabled = true

        binding.dateBtn.setOnClickListener {
            binding.datePicker.visibility = View.VISIBLE
            binding.timePicker.visibility = View.GONE
            binding.dateBtn.isEnabled = false
            binding.timeBtn.isEnabled = true
        }

        binding.timeBtn.setOnClickListener {
            binding.datePicker.visibility = View.GONE
            binding.timePicker.visibility = View.VISIBLE
            binding.dateBtn.isEnabled = true
            binding.timeBtn.isEnabled = false
        }
    }
}