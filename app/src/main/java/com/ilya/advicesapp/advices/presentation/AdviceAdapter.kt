package com.ilya.advicesapp.advices.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ilya.advicesapp.core.EspressoIdlingResource
import com.ilya.advicesapp.core.Mapper
import com.ilya.advicesapp.databinding.AdviceItemBinding

/**
 * Created by HP on 07.12.2022.
 **/


class AdviceAdapter(private val clickListener: ClickListener): RecyclerView.Adapter<AdviceViewHolder>(),Mapper.Unit<List<AdvicesUi>> {

    private val list = mutableListOf<AdvicesUi>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdviceViewHolder {
        return AdviceViewHolder(
            AdviceItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false),clickListener)
    }

    override fun onBindViewHolder(holder: AdviceViewHolder, position: Int) {
         holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    override fun map(data: List<AdvicesUi>) {
        val diff = DiffUtilCallback(data, list)
        val result = DiffUtil.calculateDiff(diff)
        list.clear()
        list.addAll(data)
        result.dispatchUpdatesTo(this)

    }

}

interface ClickListener{
    fun click(item: AdvicesUi)
}

class AdviceViewHolder(
    private val itemBinding: AdviceItemBinding,
    private val clickListener: ClickListener
): RecyclerView.ViewHolder(itemBinding.root){

    private val mapper = AdvicesUi.Mapper.ListItemUi(itemBinding)

    fun bind(item: AdvicesUi){
       item.map(mapper)
        itemBinding.root.setOnClickListener {
            clickListener.click(item)
        }
    }
}

class DiffUtilCallback(
    private val newList: List<AdvicesUi>,
    private val oldList: List<AdvicesUi>
): DiffUtil.Callback(){

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return newList[newItemPosition].map(oldList[oldItemPosition])
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return newList[newItemPosition] == oldList[oldItemPosition]
    }

}