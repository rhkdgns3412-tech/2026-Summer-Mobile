package kr.hnu.ai.july07application

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kr.hnu.ai.july07application.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val intent = intent
        val where = intent.getStringExtra("Where")
        val value = intent.getIntExtra("Value",0)
        Toast.makeText(this, "MainActivity에서 넘어온 값 : $where, $value", Toast.LENGTH_SHORT).show()
        binding.finish2nd.setOnClickListener {
            Toast.makeText(this, "SecondActivity 종료", Toast.LENGTH_SHORT).show()
            finish()
        }
        binding.thirdBtn.setOnClickListener {
            val intent = Intent(
                this, ThirdActivity::class.java)
            startActivity (intent)
        }
        binding.fourthBtn.setOnClickListener {
            val intent = Intent(
                this, FourthActivity::class.java)
            startActivity (intent)
        }
        }
    }