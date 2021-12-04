package com.example.nasa.ui.section.apod

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.nasa.R
import com.example.nasa.databinding.FragmentApodBinding
import dagger.hilt.android.AndroidEntryPoint
import jp.wasabeef.glide.transformations.BlurTransformation
import kotlinx.android.synthetic.main.fragment_mars.view.*

@AndroidEntryPoint
class ApodFragment : Fragment() {

    val viewModelApod: ApodViewModel by viewModels()

    private lateinit var viewBinding: FragmentApodBinding
    private val myAdapter by lazy { RecyclerViewApodAdapter(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentApodBinding.inflate(inflater)

        setupRecyclerView()
        loadImageBackApod()

        loadDataApod()

        viewBinding.randomBtnApod.setOnClickListener {
            setupRecyclerView()
            loadDataApod()
        }

        return viewBinding.root
    }

    fun loadDataApod(){
        viewModelApod.getApodListData().observe(viewLifecycleOwner,{
            response->
            response.body()?.let { myAdapter.setData(it) }
        })
    }

    fun loadImageBackApod(){
        Glide
            .with(this)
            .load(R.drawable.gradient)
            .apply(RequestOptions.bitmapTransform(BlurTransformation(5, 1)))
            .into(viewBinding.imageApodPhotoBack)
    }

    fun setupRecyclerView(){
        viewBinding.recyclerApod.adapter = myAdapter
        viewBinding.recyclerApod.layoutManager = LinearLayoutManager(context)
    }
}