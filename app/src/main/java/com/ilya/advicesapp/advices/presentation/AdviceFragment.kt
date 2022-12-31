package com.ilya.advicesapp.advices.presentation


import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ilya.advicesapp.R
import com.ilya.advicesapp.app.App
import com.ilya.advicesapp.advices.di.AdvicesViewModelFactory
import com.ilya.advicesapp.core.EspressoIdlingResource
import com.ilya.advicesapp.databinding.FragmentAdviceBinding
import com.ilya.advicesapp.main.presentation.BaseFragment
import kotlinx.coroutines.launch
import javax.inject.Inject

class AdviceFragment:
    BaseFragment<FragmentAdviceBinding, AdvicesViewModel>
        (R.layout.fragment_advice, FragmentAdviceBinding::inflate) {

    @Inject
    lateinit var vmFactory: AdvicesViewModelFactory

    lateinit var recyclerView: RecyclerView
    lateinit var adapter: AdviceAdapter

    override fun provideViewModel() {
        (context?.applicationContext as App)
            .appComponent.inject(this)

        viewModel = ViewModelProvider(this, vmFactory)
            .get(AdvicesViewModel.Base::class.java)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = binding!!.rcv
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = AdviceAdapter(object : ClickListener{
            override fun click(item: AdvicesUi) {
                viewModel.showDetails(item)
            }
        })
        recyclerView.adapter = adapter

        lifecycleScope.launch {
            viewModel.collectProgress(this@AdviceFragment) {
                    binding!!.progress.visibility = it
            }
        }
        lifecycleScope.launch {
            viewModel.collectState(this@AdviceFragment){
                it.apply(binding!!.edTextLayout, binding!!.edText)
            }
        }

        lifecycleScope.launch{
            viewModel.collectList(this@AdviceFragment){
                adapter.map(it)
                recyclerView.smoothScrollToPosition(0)
            }
        }

        binding!!.btnFind.setOnClickListener {
            EspressoIdlingResource.increment()
            viewModel.findAdvices(binding!!.edText.text.toString())
        }

        binding!!.btnRandom.setOnClickListener {
            EspressoIdlingResource.increment()
            viewModel.randomAdvice()
        }
    }


}


