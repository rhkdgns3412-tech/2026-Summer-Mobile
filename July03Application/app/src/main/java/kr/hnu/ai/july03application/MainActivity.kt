package kr.hnu.ai.july03application

import android.os.Bundle
import android.os.SystemClock
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kr.hnu.ai.july03application.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    var elapsedTime = 0L
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.startBtn.setOnClickListener {
            binding.chronometer.setBase(SystemClock.elapsedRealtime()+ elapsedTime)
            binding.startBtn.isEnabled=false
            binding.stopBtn.isEnabled=true
            binding.chronometer.start()

            Toast.makeText(this@MainActivity, "Chronometer started", Toast.LENGTH_SHORT).show()
        }

        binding.stopBtn.setOnClickListener {
            elapsedTime = binding.chronometer.base - SystemClock.elapsedRealtime()
            binding.stopBtn.isEnabled=false
            binding.startBtn.isEnabled=true
            binding.chronometer.stop()

            Toast.makeText(this@MainActivity, "Chronometer stopped", Toast.LENGTH_SHORT).show()
        }
        binding.resetBtn.setOnClickListener {
            binding.stopBtn.isEnabled=true
            binding.startBtn.isEnabled=true
            binding.chronometer.base = SystemClock.elapsedRealtime()
            elapsedTime = 0L
            //binding.chronometer.stop()
        }




    }
}