package alan.practice.archdemo.currency

import alan.practice.archdemo.currency.interfaces.OnItemClickListener
import alan.practice.archdemo.data.local.room.CurrencyInfo
import alan.practice.archdemo.databinding.ItemCurrencyInfoBinding
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter

class CurrencyAdapter(private val onItemClickListener: OnItemClickListener? = null) :
    ListAdapter<CurrencyInfo, CurrencyListViewHolder>(CurrencyDiff) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyListViewHolder {
        val layoutInflate = LayoutInflater.from(parent.context)
        return CurrencyListViewHolder(
            ItemCurrencyInfoBinding.inflate(layoutInflate, parent, false),
            onItemClickListener
        )
    }

    override fun onBindViewHolder(holder: CurrencyListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

object CurrencyDiff : DiffUtil.ItemCallback<CurrencyInfo>() {
    override fun areItemsTheSame(oldItem: CurrencyInfo, newItem: CurrencyInfo): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CurrencyInfo, newItem: CurrencyInfo): Boolean {
        return oldItem == newItem
    }
}