package com.ilya.advicesapp.uitest

import com.ilya.advicesapp.R

/**
 * Created by HP on 17.12.2022.
 **/

class AdvicesPage: Page() {

    val findAdviceBtn  = R.id.btnFind.view()
    val randomAdviceBtn  = R.id.btnRandom.view()

    val input = R.id.edText.view()
    val recycler = R.id.rcv

    val titleItem = R.id.search_term
}