package alan.practice.archdemo.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<VB : ViewBinding, VM : ViewModel> : AppCompatActivity() {
    abstract val viewModel: VM

    abstract val bindingInflater: (LayoutInflater) -> VB
    protected val binding: VB by lazy { bindingInflater(layoutInflater) }

    abstract fun initView()
    abstract fun initObserve()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initView()
        initObserve()
    }
}