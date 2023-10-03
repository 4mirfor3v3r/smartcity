package gemastik.pendekar.ui.main.home.self_report

import android.annotation.SuppressLint
import android.util.Base64
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import gemastik.pendekar.R
import gemastik.pendekar.data.model.HistoryReportModel
import gemastik.pendekar.databinding.ItemHistoryReportBinding
import gemastik.pendekar.utils.logError
import java.text.SimpleDateFormat
import java.util.Locale

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
                if (!data.senderName.isNullOrEmpty()){
                    tvName.text = data.senderName
                }else{
                    tvName.text = "Anonymous"
                }
                tvTitle.text = data.reportTitle
                tvLocation.text = data.reportAddress

                try {
                    val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
                    val mDate = formatter.parse(data.reportDate)
                    val formatter2 = SimpleDateFormat("yyyy MMM dd", Locale.getDefault())
                    tvDate.text = formatter2.format(mDate)
                } catch (e: Exception){
                    logError(e.toString())
                }

                val imageByteArray: ByteArray = Base64.decode(data.image, Base64.DEFAULT)
                Glide.with(binding.root.context)
                    .asBitmap()
                    .load(imageByteArray)
                    .into(ivImage)
            }
        }
    }

}