package gemastik.pendekar.ui.main.home.self_report

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import gemastik.pendekar.R
import gemastik.pendekar.data.model.HistoryReportModel
import gemastik.pendekar.databinding.ItemHistoryReportBinding
import gemastik.pendekar.utils.ReportStatus
import java.util.ArrayList

class HistoryReportAdapter : RecyclerView.Adapter<HistoryReportAdapter.ViewHolder>() {
    val listData = ArrayList<HistoryReportModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: ItemHistoryReportBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_history_report, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listData[position], position)
    }

    override fun getItemCount() = listData.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(list: List<HistoryReportModel>) {
        this.listData.clear()
        this.listData.addAll(list)
        notifyDataSetChanged()
    }

    fun add(data:HistoryReportModel){
        this.listData.add(data)
        notifyItemInserted(listData.lastIndex)
    }

    fun remove(data:HistoryReportModel){
        notifyItemRemoved(listData.indexOf(data))
        this.listData.remove(data)
    }

    inner class ViewHolder(private val binding: ItemHistoryReportBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: HistoryReportModel, position: Int) {
            with(binding){
                tvTitle.text = data.reportTitle
                tvLocation.text = data.reportAddress
                tvDate.text = data.reportDate

                tvStatus.text = data.reportStatus?.title
                when(data.reportStatus){
                    ReportStatus.SUCCESS -> tvStatus.setTextColor(ContextCompat.getColor(binding.root.context,R.color.green))
                    ReportStatus.FAILURE -> tvStatus.setTextColor(ContextCompat.getColor(binding.root.context,R.color.primary))
                    ReportStatus.PROCESS -> tvStatus.setTextColor(ContextCompat.getColor(binding.root.context,R.color.blue))
                    else -> tvStatus.setTextColor(ContextCompat.getColor(binding.root.context,R.color.blue))
                }
            }
        }
    }

}