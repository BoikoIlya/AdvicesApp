package com.ilya.advicesapp.advices.domain

import com.ilya.advicesapp.advices.presentation.AdvicesUi

/**
 * Created by HP on 15.11.2022.
 **/
class LanguageUiMapper: AdvicesDomain.Mapper<AdvicesUi> {
    override fun map(extension: String, language_name: String, program: String): AdvicesUi =
        AdvicesUi(extension, language_name, program)
}