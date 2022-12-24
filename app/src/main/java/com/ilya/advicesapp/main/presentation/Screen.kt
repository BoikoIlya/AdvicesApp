package com.ilya.advicesapp.main.presentation

import androidx.fragment.app.Fragment
import com.ilya.advicesapp.advicedetails.presentation.DetailsFragment
import com.ilya.advicesapp.advices.presentation.AdviceFragment

/**
 * Created by HP on 15.11.2022.
 **/
sealed class Screen{

    abstract fun fragment(): Class<out Fragment>

    object Languages: Screen(){
        override fun fragment(): Class<out Fragment> = AdviceFragment::class.java
    }

    object Details: Screen(){
        override fun fragment(): Class<out Fragment> = DetailsFragment::class.java
    }
}
