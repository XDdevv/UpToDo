package uz.xd.tasksapp.adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import uz.xd.tasksapp.databinding.ItemFlagBinding
import uz.xd.tasksapp.models.Flag

class FlagsAdapter(
    var context: Context,
    private var list: List<Flag>,
    private var callBack: CallBack
) : Adapter<FlagsAdapter.FlagViewHolder>() {
    private lateinit var binding: ItemFlagBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlagViewHolder {
        binding = ItemFlagBinding.inflate(LayoutInflater.from(context), parent, false)
        return FlagViewHolder(binding.root)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: FlagViewHolder, position: Int) {
        holder.count.text = list[position].number.toString()
        holder.itemView.setOnClickListener {
            holder.itemView.setBackgroundColor(Color.BLACK)
            callBack.item(position)
        }
    }

    inner class FlagViewHolder(itemView: View) : ViewHolder(itemView) {
        var count = binding.countTv
    }
    interface CallBack {
        fun item(position: Int)
    }
}