package alan.practice.archdemo.demo

import alan.practice.archdemo.base.BaseActivity
import alan.practice.archdemo.databinding.ActivityDemoBinding
import android.view.LayoutInflater
import org.koin.androidx.viewmodel.ext.android.viewModel

class DemoActivity : BaseActivity<ActivityDemoBinding, DemoViewModel>() {

    override val bindingInflater: (LayoutInflater) -> ActivityDemoBinding = ActivityDemoBinding::inflate
    override val viewModel: DemoViewModel by viewModel()

    override fun initView() {
        binding.btLoad.setOnClickListener {
            viewModel.loadCurrencyInfoList()
        }
        binding.btSort.setOnClickListener {
            viewModel.sortCurrencyInfoList()
        }
    }

    override fun initObserve() {
        viewModel.currencyInfosLivData.observe(this) {
            binding.btSort.takeUnless { it.isEnabled }?.isEnabled = true
        }
    }
}