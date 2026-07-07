package kr.hnu.ai.july07application

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kr.hnu.ai.july07application.databinding.ActivityThirdBinding

class ThirdActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityThirdBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.finish3rd.setOnClickListener {
            Toast.makeText(this, "ThirdActivity 종료", Toast.LENGTH_SHORT).show()
            finish()
        }
        binding.secondBtn.setOnClickListener {
            val intent = Intent(
                this, SecondActivity::class.java)
            startActivity (intent)
        }
        binding.fourthBtn.setOnClickListener {
            val intent = Intent(
                this, FourthActivity::class.java)
            startActivity (intent)
        }
        }
    }