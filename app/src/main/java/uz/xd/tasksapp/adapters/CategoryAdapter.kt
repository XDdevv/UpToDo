package uz.xd.tasksapp.adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import uz.xd.tasksapp.databinding.ItemCategoryBinding
import uz.xd.tasksapp.databinding.ItemFlagBinding
import uz.xd.tasksapp.db.entities.Category
import uz.xd.tasksapp.models.Flag

class CategoryAdapter(
    var context: Context,
    private var list: List<Category>,
    private var callBack: CallBack
) : Adapter<CategoryAdapter.FlagViewHolder>() {

    private lateinit var binding: ItemCategoryBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlagViewHolder {
        binding = ItemCategoryBinding.inflate(LayoutInflater.from(context), parent, false)
        return FlagViewHolder(binding.root)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: FlagViewHolder, position: Int) {
        holder.title.text = list[position].title
        holder.title.setBackgroundColor(list[position].color)
        holder.itemView.setOnClickListener {
            callBack.item(position)
        }
    }

    inner class FlagViewHolder(itemView: View) : ViewHolder(itemView) {
        var title = binding.titleTv
    }
    interface CallBack {
        fun item(position: Int)
    }
}