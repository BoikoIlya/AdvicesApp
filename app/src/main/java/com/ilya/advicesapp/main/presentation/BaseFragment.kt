package com.ilya.advicesapp.main.presentation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.ilya.advicesapp.advices.presentation.AdvicesViewModel

/**
 * Created by HP on 15.11.2022.
 **/
abstract class BaseFragment<T: ViewBinding, S:Any>(
    protected val layoutId: Int,
    private val bindingInflater: (layoutInflater:LayoutInflater) -> T,
): Fragment() {

    protected lateinit var binding: T
    protected lateinit var viewModel: S

    protected abstract fun provideViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        provideViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = bindingInflater.invoke(inflater)
        return binding.root
    }
}

