package pers.icebounded.mvvm.demo.paging

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import pers.icebounded.mvvm.demo.R
import pers.icebounded.mvvm.demo.databinding.ItemPagingTestBinding

/**
 * Created by Andy
 * on 2021/3/9
 */
class PagingTestAdapter : PagedListAdapter<Art, PagingTestViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Art>() {
            override fun areItemsTheSame(oldItem: Art, newItem: Art): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Art, newItem: Art): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagingTestViewHolder {
        return PagingTestViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_paging_test,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PagingTestViewHolder, position: Int) {
        getItem(position)?.let {
            holder.binding.data = it
            holder.binding.executePendingBindings()
        }
    }
}

class PagingTestViewHolder(val binding: ItemPagingTestBinding) : RecyclerView.ViewHolder(binding.root) {

}