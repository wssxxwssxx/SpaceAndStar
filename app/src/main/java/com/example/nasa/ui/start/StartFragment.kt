package com.example.nasa.ui.start

import android.animation.Animator
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import jp.wasabeef.glide.transformations.BlurTransformation
import android.view.animation.OvershootInterpolator

import android.animation.AnimatorSet
import com.ogaclejapan.arclayout.ArcLayout
import android.animation.AnimatorListenerAdapter

import android.view.animation.AnticipateInterpolator
import android.animation.ObjectAnimator
import android.app.Activity
import android.graphics.Typeface
import com.example.nasa.R
import com.example.nasa.utils.AnimatorUtils
import com.getkeepsafe.taptargetview.TapTarget
import com.getkeepsafe.taptargetview.TapTargetView
import kotlinx.android.synthetic.main.fragment_start.*
import kotlinx.android.synthetic.main.fragment_start.view.*
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.toolbar.*


class StartFragment : Fragment(), View.OnClickListener {

    var menuLayout: View? = null
    var arcLayout: ArcLayout? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val frameLayout = inflater.inflate(R.layout.fragment_start, container, false)
        loadImageGlide(frameLayout)
        bindViews(frameLayout)
        showCaseView(frameLayout)

        return frameLayout
    }

    override fun onResume() {
        super.onResume()
        requireActivity().appBar.visibility = View.GONE
    }

    override fun onStop() {
        super.onStop()
        requireActivity().appBar.visibility = View.VISIBLE
    }

    fun bindViews(frameLayout: View) {
        menuLayout = frameLayout.findViewById(R.id.menu_layout)
        arcLayout = frameLayout.findViewById(R.id.arc)

        frameLayout.fab.setOnClickListener(this)
        frameLayout.sections_btn_start.setOnClickListener(this)
    }

    fun loadImageGlide(frameLayout: View) {
        Glide
            .with(this)
            .load("https://media3.giphy.com/media/Swytr5ngUDfDwtXKOz/giphy.gif?cid=ecf05e47420yvfkkg1np8sxf704qikktwle5eti2nck65kzf&rid=giphy.gif&ct=g")
            .apply(RequestOptions.bitmapTransform(BlurTransformation(5, 1)))
            .into(frameLayout.image_back_start)
    }

    fun showCaseView(frameLayout: View){
        TapTargetView.showFor(activity,
        TapTarget.forView(frameLayout.fab,"Click Sun","Menu")
            .outerCircleColor(R.color.path_orange)
            .outerCircleAlpha(0.70f)
            .targetCircleColor(R.color.path_blue)
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

    override fun onClick(view: View?) {
        var navHostFragment = NavHostFragment.findNavController(this)

        if (view?.getId() == R.id.fab) {
            onFabClick(view)
            return
        }
        if (view?.getId() == R.id.sections_btn_start) {
            navHostFragment.navigate(R.id.marsFragment)
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
        val dx = fab.x - item.x
        val dy = fab.y - item.y
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
        val dx = fab.x - item.x
        val dy = fab.y - item.y
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

}