package alan.practice.archdemo.currency.interfaces

import alan.practice.archdemo.data.local.room.CurrencyInfo

interface OnItemClickListener {
    fun onItemClick(currencyInfo: CurrencyInfo)
}