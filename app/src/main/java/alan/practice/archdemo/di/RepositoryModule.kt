package alan.practice.archdemo.di

import alan.practice.archdemo.demo.DemoRepository
import alan.practice.archdemo.demo.interfaces.IDemoRepository
import org.koin.dsl.module

val repositoryModule = module {
    factory<IDemoRepository> { DemoRepository(database = get()) }
}