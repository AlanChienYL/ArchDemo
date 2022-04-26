package alan.practice.archdemo.currency

import alan.practice.archdemo.currency.interfaces.OnItemClickListener
import alan.practice.archdemo.data.local.room.CurrencyInfo
import alan.practice.archdemo.databinding.ItemCurrencyInfoBinding
import androidx.recyclerview.widget.RecyclerView

class CurrencyListViewHolder(
    private val binding: ItemCurrencyInfoBinding,
    private val onItemClickListener: OnItemClickListener? = null
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(currencyInfo: CurrencyInfo) {
        binding.tvName.text = currencyInfo.name
        binding.tvSymbol.text = currencyInfo.symbol
        binding.tvFirstChar.text = currencyInfo.name[0].toString()
        onItemClickListener?.let { onItemClickListener ->
            binding.root.setOnClickListener {
                onItemClickListener.onItemClick(currencyInfo)
            }
        }
    }
}