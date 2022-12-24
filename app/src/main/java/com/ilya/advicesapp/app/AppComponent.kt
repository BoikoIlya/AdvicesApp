package com.ilya.advicesapp.app

import com.ilya.advicesapp.advicedetails.presentation.DetailsFragment
import com.ilya.advicesapp.advices.di.AdvicesModule
import com.ilya.advicesapp.advices.presentation.AdviceFragment
import com.ilya.advicesapp.main.presentation.MainActivity
import com.ilya.advicesapp.workmanager.di.WorkManagerModule
import dagger.Component
import javax.inject.Singleton

/**
 * Created by HP on 05.12.2022.
 **/
@Singleton
@Component(modules = [
    AdvicesModule::class,
    WorkManagerModule::class
])
interface AppComponent {

    fun inject(adviceFragment: AdviceFragment)
    fun inject(detailsFragment: DetailsFragment)
    fun inject(mainActivity: MainActivity)


}