package com.ilya.advicesapp.advicedetails.presentation

import androidx.lifecycle.ViewModel
import com.ilya.advicesapp.advicedetails.data.AdviceDetails
import com.ilya.advicesapp.advices.presentation.AdvicesUi
import javax.inject.Inject

/**
 * Created by HP on 25.11.2022.
 **/
class DetailsViewModel @Inject constructor(
    private val data: AdviceDetails.Read<AdvicesUi>,
): ViewModel(), AdviceDetails.Read<AdvicesUi> {

    override fun read(): AdvicesUi = data.read()
}