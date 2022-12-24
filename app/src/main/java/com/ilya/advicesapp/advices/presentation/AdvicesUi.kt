package com.ilya.advicesapp.advices.presentation

import com.ilya.advicesapp.databinding.AdviceItemBinding
import com.ilya.advicesapp.databinding.FragmentDetailsBinding
import javax.inject.Inject

/**
 * Created by HP on 15.11.2022.
 **/
data class AdvicesUi(
  private val advices: String,
  private val date: String,
  private val searchTerm: String
){
  fun <T> map(mapper: Mapper<T>): T = mapper.map(advices, date,searchTerm)

  interface Mapper<T>{
    fun map(
      advices: String,
      date: String,
      searchTerm: String
    ): T

    class ListItemUi(
      private val binding: AdviceItemBinding
    ):  Mapper<Unit>{
      override fun map(advices: String, date: String, searchTerm: String) {
       binding.date.text = date
       binding.searchTerm.text = searchTerm
      }
    }

    class Details @Inject constructor(
      private val binding: FragmentDetailsBinding
    ):  Mapper<Unit>{
      override fun map(advices: String, date: String, searchTerm: String) {
        binding.detailsHeader.text = searchTerm
        binding.detailsContent.text = advices
      }
    }
  }

  fun map(item: AdvicesUi): Boolean = this.searchTerm == searchTerm
}
