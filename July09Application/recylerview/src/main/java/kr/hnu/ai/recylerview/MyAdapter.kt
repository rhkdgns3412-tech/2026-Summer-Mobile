package kr.hnu.ai.recylerview

import kr.hnu.ai.recylerview.databinding.ItemMainBinding

class MyAdapter(val datas: List<String>) : androidx.recyclerview.widget.RecyclerView.Adapter<MyAdapter.MyViewHolder>() {
    inner class MyViewHolder(val binding: ItemMainBinding) : androidx.recyclerview.widget.RecyclerView.ViewHolder(binding.root) {
        fun bind(data: String) {
            binding.itemData.text = data
        }
    }

    override fun onCreateViewHolder(parent: android.view.ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemMainBinding.inflate(android.view.LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.itemData.text = datas[position]
        holder.binding.itemRoot.setOnClickListener {
            android.widget.Toast.makeText(holder.itemView.context, "${datas[position]} clicked", android.widget.Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int = datas.size
}