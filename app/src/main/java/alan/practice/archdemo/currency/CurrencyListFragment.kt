package alan.practice.archdemo.currency

import alan.practice.archdemo.base.BaseFragment
import alan.practice.archdemo.currency.interfaces.OnItemClickListener
import alan.practice.archdemo.data.local.room.CurrencyInfo
import alan.practice.archdemo.databinding.FragmentCurrencyListBinding
import alan.practice.archdemo.demo.DemoViewModel
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_SHORT
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class CurrencyListFragment : BaseFragment<FragmentCurrencyListBinding, DemoViewModel>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentCurrencyListBinding =
        FragmentCurrencyListBinding::inflate
    override val viewModel: DemoViewModel by sharedViewModel()

    private val onItemClickListener = object : OnItemClickListener {
        override fun onItemClick(currencyInfo: CurrencyInfo) {
            binding?.root?.let{
                Snackbar.make(it,"click ${currencyInfo.name}", LENGTH_SHORT).show()
            }
        }
    }
    private val adapter = CurrencyAdapter(onItemClickListener)

    override fun initView() {
        binding?.rv?.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@CurrencyListFragment.adapter
        }
    }

    override fun initObserve() {
        viewModel.currencyInfosLivData.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

}