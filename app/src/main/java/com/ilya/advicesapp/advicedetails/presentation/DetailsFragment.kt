package com.ilya.advicesapp.advicedetails.presentation

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.ilya.advicesapp.R
import com.ilya.advicesapp.app.App
import com.ilya.advicesapp.databinding.FragmentDetailsBinding
import com.ilya.advicesapp.advicedetails.di.DetailsViewModelFactory
import com.ilya.advicesapp.advices.presentation.AdvicesUi
import com.ilya.advicesapp.core.EspressoIdlingResource
import com.ilya.advicesapp.main.presentation.BaseFragment
import javax.inject.Inject

class DetailsFragment :
    BaseFragment<FragmentDetailsBinding,DetailsViewModel>
        (R.layout.fragment_details, FragmentDetailsBinding::inflate) {

    @Inject
    lateinit var vmFactory: DetailsViewModelFactory

    override fun provideViewModel() {
        (context?.applicationContext as App).appComponent.inject(this)

         viewModel = ViewModelProvider(this, vmFactory)
            .get(DetailsViewModel::class.java)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val details = viewModel.read()

        val mapper = AdvicesUi.Mapper.Details(binding)

        details.map(mapper)

    }

}