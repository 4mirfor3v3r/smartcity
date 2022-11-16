package gemastik.pendekar.ui.main.home.cctv

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import gemastik.pendekar.R
import gemastik.pendekar.data.model.HistoryReportModel
import gemastik.pendekar.data.model.SearchHistoryCCTVModel
import gemastik.pendekar.databinding.ItemHistoryReportBinding
import gemastik.pendekar.databinding.ItemMapSearchResultBinding
import gemastik.pendekar.utils.ReportStatus
import java.util.ArrayList

class CCTVSearchAdapter(private val onItemClick: (SearchHistoryCCTVModel) -> Unit) :
    RecyclerView.Adapter<CCTVSearchAdapter.ViewHolder>() {
    val listData = ArrayList<SearchHistoryCCTVModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: ItemMapSearchResultBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_map_search_result,
            parent,
            false
        )
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listData[position], position)
    }

    override fun getItemCount() = listData.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(list: List<SearchHistoryCCTVModel>) {
        this.listData.clear()
        this.listData.addAll(list)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ItemMapSearchResultBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: SearchHistoryCCTVModel, position: Int) {
            with(binding) {
                tvTitle.text = data.searchName
                binding.root.setOnClickListener { onItemClick(data) }
            }
        }
    }
}