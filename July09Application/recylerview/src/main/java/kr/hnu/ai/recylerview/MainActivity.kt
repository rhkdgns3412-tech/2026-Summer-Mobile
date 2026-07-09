package kr.hnu.ai.recylerview

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import kr.hnu.ai.recylerview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val datas = mutableListOf<String>()
        for (i in 1..100) {
            datas.add("Item $i")

        }

        binding.recylerView.layoutManager= LinearLayoutManager(this)
        binding.recylerView.adapter = MyAdapter(datas)

        binding.addBtn.setOnClickListener {
            val newItem = "Item ${datas.size + 1}"
            datas.add(newItem)
            binding.recylerView.adapter?.notifyItemInserted(datas.size - 1)
        }
        binding.delBtn.setOnClickListener {
            if (datas.isNotEmpty()) {
                datas.removeAt(datas.size - 1)
                binding.recylerView.adapter?.notifyItemRemoved(datas.size)
            } }
    }
    }