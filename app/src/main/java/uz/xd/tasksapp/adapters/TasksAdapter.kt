package uz.xd.tasksapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import uz.xd.tasksapp.databinding.ItemTaskBinding
import uz.xd.tasksapp.db.TasksDatabase
import uz.xd.tasksapp.db.entities.Task

class TasksAdapter(
    private var context: Context,
    private var list: List<Task>,
    private var callBack: CallBack
) : Adapter<TasksAdapter.TasksViewHolder>() {

    private lateinit var binding: ItemTaskBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TasksViewHolder {
        binding = ItemTaskBinding.inflate(LayoutInflater.from(context), parent, false)
        return TasksViewHolder(binding.root)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: TasksViewHolder, position: Int) {
        holder.titleTv.text = list[position].title
        holder.dateTv.text = list[position].dateFull
        holder.categoryBtn.text = list[position].category
        holder.flagBtn.text = list[position].flag.toString()

        holder.itemView.setOnClickListener {
            callBack.item(position)
        }
        holder.ellipseIv.setOnClickListener {
            callBack.item_done(
                TasksDatabase.getInstance(context).tasksDatabase().getAllTasks()[position].id
            )
        }
    }

    inner class TasksViewHolder(itemView: View) : ViewHolder(itemView) {
        var titleTv = binding.titleTv
        var dateTv = binding.dateTv
        var ellipseIv = binding.ellipseIv
        var flagBtn = binding.flagBtn
        var categoryBtn = binding.categoryBtn
    }

    interface CallBack {
        fun item(position: Int)
        fun item_done(position: Int)
    }
}