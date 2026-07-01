package kr.hnu.ai.viewbindingexam

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kr.hnu.ai.viewbindingexam.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // TextView.text는 프로퍼티로 값을 대입해야 합니다. 함수처럼 호출하면 오류 발생합니다.
        binding.resetBtn.setOnClickListener {
            binding.titleText.text = "view binding exam"
        }
        binding.changeBtn.setOnClickListener {
            binding.titleText.text = "텍스트가 변경되었음"
        }
    }
}
