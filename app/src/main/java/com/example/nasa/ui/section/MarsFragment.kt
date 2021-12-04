package com.example.nasa.ui.section

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.graphics.Typeface
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnticipateInterpolator
import android.view.animation.OvershootInterpolator
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.nasa.R
import com.example.nasa.databinding.FragmentMarsBinding
import com.example.nasa.service.mars.PhotosMarsListVO
import com.example.nasa.utils.AnimatorUtils
import com.getkeepsafe.taptargetview.TapTarget
import com.getkeepsafe.taptargetview.TapTargetView
import com.ogaclejapan.arclayout.ArcLayout
import dagger.hilt.android.AndroidEntryPoint
import jp.wasabeef.glide.transformations.BlurTransformation
import kotlinx.android.synthetic.main.fragment_mars.*
import kotlinx.android.synthetic.main.fragment_mars.view.*

@AndroidEntryPoint
class MarsFragment : Fragment(), View.OnClickListener {
    val marsViewModel: MarsViewModel by viewModels()

    lateinit var recyclerAdapter: RecyclerViewAdapter
    var arcLayout: ArcLayout? = null
    var menuLayout: View? = null
    var binding: FragmentMarsBinding?=null

    private var sol = 1
    private var roverName = "curiosity"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMarsBinding.inflate(inflater)

        bindViews(binding!!)
        showCaseView(binding!!)
        initViewModel(binding!!)
        initViewModel()
        loadImageGlide(binding!!.imageMarsPhotoBack)

        return binding!!.root
    }

    fun loadImageGlide(frameLayout: View) {
        Glide
            .with(this)
            .load(R.drawable.gradient)
            .apply(RequestOptions.bitmapTransform(BlurTransformation(5, 1)))
            .into(frameLayout.image_mars_photo_back)
    }

    fun initViewModel(binding: FragmentMarsBinding) {
        binding.avi.setIndicator("BallTrianglePathIndicator")
        binding.avi.show()
        val recycler = binding.recyclerMarsPhoto
        recycler.layoutManager = GridLayoutManager(activity, 2)
        val decortion = DividerItemDecoration(activity, DividerItemDecoration.VERTICAL)
        recycler.addItemDecoration(decortion)
        recyclerAdapter = context?.let { RecyclerViewAdapter(it) }!!
        recycler.adapter = recyclerAdapter
    }

    @SuppressLint("SetTextI18n")
    fun initViewModel() {
        marsViewModel.getRecyclerListObserver(roverName, sol.toString())
            .observe(viewLifecycleOwner, Observer<PhotosMarsListVO> {
                if (it != null) {
                    Log.e("SIZELIST",it.photosMarsList.size.toString())
                    if(it.photosMarsList.size == 0){
                        binding!!.noSol.visibility = View.VISIBLE
                        binding!!.solInfo.text = "No photos were found for this SOL($sol)"
                    } else {
                        binding!!.noSol.visibility = View.GONE
                    }
                    recyclerAdapter.setUpdateData(it.photosMarsList)
                    avi.hide()
                }
            })
    }

    @SuppressLint("ClickableViewAccessibility")
    fun bindViews(binding: FragmentMarsBinding) {
        menuLayout = binding.menuLayoutMars
        arcLayout = binding.arc
        binding.fabMars.setOnClickListener(this)
        binding.btnCuriosity.setOnClickListener(this)
        binding.btnOpportunity.setOnClickListener(this)
        binding.btnSpirit.setOnClickListener(this)

        binding.solAdd.setOnClickListener(this)
        binding.solDelete.setOnClickListener(this)
        binding.btnPerseverance.setOnClickListener(this)
    }


    override fun onClick(view: View?) {

        if (view?.getId() == R.id.sol_add) {
            sol = sol + 1
            initViewModel()
            Log.e("SPIRIT", roverName)
        }

        if (view?.getId() == R.id.sol_delete) {
            if (sol >= 1) {
                sol--
                initViewModel()
            }
            if(sol <= 1){
                sol = 1
            }
        }

        if (view?.getId() == R.id.fab_mars) {
            onFabClick(view)
            return
        }
        if (view?.getId() == R.id.btn_curiosity) {
            roverName = "curiosity"
            initViewModel()
            binding!!.solAdd.visibility = View.VISIBLE
            binding!!.solDelete.visibility = View.VISIBLE
        }

        if (view?.getId() == R.id.btn_opportunity) {
            roverName = "opportunity"
            initViewModel()
            binding!!.solAdd.visibility = View.VISIBLE
            binding!!.solDelete.visibility = View.VISIBLE
        }

        if (view?.getId() == R.id.btn_spirit) {
            roverName = "spirit"
            initViewModel()
            binding!!.solAdd.visibility = View.VISIBLE
            binding!!.solDelete.visibility = View.VISIBLE
        }

        if(view?.getId() == R.id.btn_perseverance){
            marsViewModel.getRecyclerListObserver("perseverance", "0")
                .observe(viewLifecycleOwner, Observer<PhotosMarsListVO> {
                    if (it != null) {
                        recyclerAdapter.setUpdateData(it.photosMarsList)
                        avi.hide()
                    }
                })
            binding!!.solAdd.visibility = View.GONE
            binding!!.solDelete.visibility = View.GONE
        }

    }

    private fun onFabClick(v: View) {
        if (v.isSelected) {
            hideMenu()
        } else {
            showMenu()
        }
        v.isSelected = !v.isSelected
    }

    private fun showMenu() {
        menuLayout?.setVisibility(View.VISIBLE)
        val animList: MutableList<Animator> = ArrayList()
        var i = 0
        val len: Int = arcLayout?.getChildCount()!!
        while (i < len) {
            createShowItemAnimator(arcLayout!!.getChildAt(i))?.let { animList.add(it) }
            i++
        }
        val animSet = AnimatorSet()
        animSet.duration = 400
        animSet.interpolator = OvershootInterpolator()
        animSet.playTogether(animList)
        animSet.start()
    }

    private fun hideMenu() {
        val animList: MutableList<Animator> = ArrayList()
        for (i in arcLayout!!.childCount - 1 downTo 0) {
            createHideItemAnimator(arcLayout!!.getChildAt(i))?.let { animList.add(it) }
        }
        val animSet = AnimatorSet()
        animSet.duration = 400
        animSet.interpolator = AnticipateInterpolator()
        animSet.playTogether(animList)
        animSet.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                menuLayout!!.visibility = View.INVISIBLE
            }
        })
        animSet.start()
    }

    private fun createShowItemAnimator(item: View): Animator? {
        val dx = fab_mars.x - item.x
        val dy = fab_mars.y - item.y
        item.rotation = 0f
        item.translationX = dx
        item.translationY = dy
        return ObjectAnimator.ofPropertyValuesHolder(
            item,
            AnimatorUtils.rotation(0f, 720f),
            AnimatorUtils.translationX(dx, 0f),
            AnimatorUtils.translationY(dy, 0f)
        )
    }

    private fun createHideItemAnimator(item: View): Animator? {
        val dx = fab_mars.x - item.x
        val dy = fab_mars.y - item.y
        val anim: Animator = ObjectAnimator.ofPropertyValuesHolder(
            item,
            AnimatorUtils.rotation(720f, 0f),
            AnimatorUtils.translationX(0f, dx),
            AnimatorUtils.translationY(0f, dy)
        )
        anim.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                item.translationX = 0f
                item.translationY = 0f
            }
        })
        return anim
    }

    fun showCaseView(binding: FragmentMarsBinding){
        TapTargetView.showFor(activity,
            TapTarget.forView(binding.fabMars,"Click Sun",
                "Rovers menu\n" +
                        "1 - curiosity (16,19 mile)\n" +
                        "2 - opportunity (28,06 mile)\n" +
                        "3 - spirit (4,8 mile)\n" +
                        "4 - perseverance (0,62 mile)")
                .outerCircleColor(R.color.purple_500)
                .outerCircleAlpha(0.70f)
                .targetCircleColor(R.color.black)
                .titleTextSize(20)
                .titleTextColor(R.color.white)
                .descriptionTextSize(20)
                .descriptionTextColor(R.color.white)
                .textColor(R.color.white)
                .textTypeface(Typeface.SANS_SERIF)
                .dimColor(R.color.white)
                .drawShadow(true)
                .cancelable(true)
                .tintTarget(true)
                .transparentTarget(true)
                .targetRadius(60)
        )
    }

}
