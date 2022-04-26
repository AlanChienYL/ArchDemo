package alan.practice.archdemo.di

import alan.practice.archdemo.demo.DemoViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { DemoViewModel(repository = get()) }
}


