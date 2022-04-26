package alan.practice.archdemo.demo

import alan.practice.archdemo.data.local.room.CurrencyInfo
import alan.practice.archdemo.demo.interfaces.IDemoRepository
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DemoViewModel(private val repository: IDemoRepository) : ViewModel() {
    private var currencyInfos: MutableList<CurrencyInfo> = arrayListOf()

    private val _currencyInfosLivData: MutableLiveData<List<CurrencyInfo>> = MutableLiveData()
    val currencyInfosLivData: LiveData<List<CurrencyInfo>> = _currencyInfosLivData

    fun loadCurrencyInfoList() {
        viewModelScope.launch {
            repository.fetchCurrencyInfo()?.let {
                currencyInfos = ArrayList(it)
                _currencyInfosLivData.postValue(it)
            } ?: _currencyInfosLivData.postValue(arrayListOf())
        }
    }

    fun sortCurrencyInfoList() {
        viewModelScope.launch(Dispatchers.IO) {
            // prevent concurrency issue we copy a new list to sort then save value back to currencyInfos
            currencyInfos = arrayListOf<CurrencyInfo>().apply {
                addAll(currencyInfos)
                sortBy { it.id }
            }
            _currencyInfosLivData.postValue(currencyInfos)
        }
    }
}