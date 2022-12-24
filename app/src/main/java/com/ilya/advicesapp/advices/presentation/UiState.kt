package com.ilya.advicesapp.advices.presentation

import android.widget.EditText
import com.google.android.material.textfield.TextInputLayout

/**
 * Created by HP on 15.11.2022.
 **/
sealed class UiState{

   abstract fun apply(
        textInputLayout: TextInputLayout,
        editText: EditText
    )

    object Success : UiState() {
        override fun apply(textInputLayout: TextInputLayout, editText: EditText) {
            editText.setText("")
            textInputLayout.apply {
                isErrorEnabled  = false
                error = ""
                clearFocus()
            }
        }
    }

    abstract class Error(
        private val errorEnabled: Boolean,
        private val message: String
    ): UiState(){
        override fun apply(
            textInputLayout: TextInputLayout,
            editText: EditText,
        )= with(textInputLayout){
            isErrorEnabled  = errorEnabled
            error = message
        }
    }

    class ShowError(message: String): Error(true, message)
    class ClearError: Error(false, "")
}
