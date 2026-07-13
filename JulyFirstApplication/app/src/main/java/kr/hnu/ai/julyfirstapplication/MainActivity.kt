package kr.hnu.ai.julyfirstapplication

import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 💡 enableEdgeToEdge() 등을 사용할 수도 있지만 화면 구성을 위해 기본 상속만 유지합니다.

        // 1. 변수명(textView)을 명확히 적고, apply 오타 수정
        val textView = TextView(this).apply {
            text = "Hello, World!"
            textSize = 24f
            setPadding(16, 16, 16, 16)
        }

        val image = ImageView(this).apply {
            // R.drawable.cat 파일이 프로젝트 내 res/drawable 폴더에 실제로 있어야 에러가 안 납니다!
            setImageResource(R.drawable.cat)
        }

        // 2. context= 제거하고 aaply -> apply 오타 수정
        val address = TextView(this).apply {
            text = "Address: 123 Main St, Anytown, USA"
            textSize = 16f
            setPadding(16, 16, 16, 16)
        }

        // 3. aaply -> apply 오타 수정
        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            addView(textView) // 이제 정상적으로 등록됩니다.
            addView(image)
            addView(address) // 레이아웃 파라미터를 지정하여 주소 텍스트뷰를 추가합니다.
        }

        // 4. ⭐ 중요: 생성한 레이아웃을 이 액티비티의 진짜 화면으로 설정합니다.
        setContentView(layout)
    }
}