package kr.hnu.ai.pickerdialog

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kr.hnu.ai.pickerdialog.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.dateBtn.setOnClickListener {
            val cal = Calender.getInstance()
            val y = cal.get(Calender.YEAR)
            val m = cal.get(Calender.MONTH)
            val d = cal.get(Calender.DAY_OF_MONTH)
            DatePickerDialog(this, { view, year, month, dayOfMonth ->
                binding.dateBtn.text = "$year-${month + 1}-$dayOfMonth"
            }, y, m, d).show()
        }
        binding.timeBtn.setOnClickListener {
            val cal = Calender.getInstance()
            val h = cal.get(Calender.HOUR_OF_DAY)
            val m = cal.get(Calender.MINUTE)
            TimePickerDialog(this, { view, hourOfDay, minute ->
                binding.timeBtn.text = "$hourOfDay:$minute"
            }, h, m, true).show()
        }
        binding.dlgBtn.setOnClickListener {
            val dlg = CustomDialog(this)
            dlg.show()
        }
        binding.alertBtn.setOnClickListener {
            val dlg = CustomAlertDialog(this)
            dlg.show()
        } }}

}
