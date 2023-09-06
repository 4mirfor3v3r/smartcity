package gemastik.pendekar.ui.main.home.list_cctv

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import gemastik.pendekar.R
import gemastik.pendekar.data.model.CCTVModel
import gemastik.pendekar.databinding.ItemListCctvBinding

class ListCCTVAdapter(private val onItemClick: (CCTVModel) -> Unit) :
    RecyclerView.Adapter<ListCCTVAdapter.ViewHolder>() {
    val listData = ArrayList<CCTVModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: ItemListCctvBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_list_cctv,
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
    fun updateList(list: List<CCTVModel>) {
        this.listData.clear()
        this.listData.addAll(list)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ItemListCctvBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(data: CCTVModel, position: Int) {
            with(binding) {
                tvTitle.text = data.address
                tvStatus.text = "Status: ${data.status}"
                binding.root.setOnClickListener { onItemClick(data) }
            }
        }
    }
}