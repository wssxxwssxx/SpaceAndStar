package com.example.nasa

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.yalantis.contextmenu.lib.ContextMenuDialogFragment
import com.yalantis.contextmenu.lib.MenuObject
import com.yalantis.contextmenu.lib.MenuParams
import dagger.hilt.android.AndroidEntryPoint
import jp.wasabeef.glide.transformations.BlurTransformation
import kotlinx.android.synthetic.main.fragment_start.view.*
import kotlinx.android.synthetic.main.toolbar.*

//https://api.nasa.gov/planetary/apod?api_key=EokjtqGQruggybwmxwsS8g5jQrmisUXv7OCROBo8
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var contextMenuDialogFragment: ContextMenuDialogFragment
    var titleToolbar: String = "Space"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initToolbar()
        initMenuFragment()
    }

    private fun initMenuFragment() {
        val menuParams = MenuParams(
            actionBarSize = resources.getDimension(R.dimen.tool_bar_height).toInt(),
            menuObjects = getMenuObject(),
            isClosableOutside = false,
        )

        contextMenuDialogFragment = ContextMenuDialogFragment.newInstance(menuParams).apply {
            menuItemClickListener = {
                view, position ->
                when(position){
                    1 -> {
                        val navHostFragment = NavHostFragment.findNavController(this)
                        navHostFragment.navigate(R.id.marsFragment)
                    }
                    2 -> {
                        val navHostFragment = NavHostFragment.findNavController(this)
                        navHostFragment.navigate(R.id.apodFragment)
                    }
                    3 -> {
                        val navHostFragment = NavHostFragment.findNavController(this)
                        navHostFragment.navigate(R.id.satelliteFragment)
                    }
                }
            }
        }
    }

    private fun getMenuObject() = mutableListOf<MenuObject>().apply{
        val close = MenuObject().apply { setResourceValue(R.drawable.ic_baseline_close_24) }
        val mars = MenuObject("Mars Photo").apply { setResourceValue(R.drawable.curiosity) }
        val apod = MenuObject("Apod").apply { setResourceValue(R.drawable.galaxy) }
        val satellite = MenuObject("Satellite").apply { setResourceValue(R.drawable.satellite) }
        add(close)
        add(mars)
        add(apod)
        add(satellite)
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setHomeButtonEnabled(true)
        }

        supportActionBar!!.title = ""


        tvToolbarTitle.setText(titleToolbar)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
            when (item.itemId) {
                R.id.context_menu -> {
                    showContextMenuDialogFragment()
                }
            }

        return super.onOptionsItemSelected(item)
    }

    private fun showContextMenuDialogFragment() {
        if (supportFragmentManager.findFragmentByTag(ContextMenuDialogFragment.TAG) == null) {
            contextMenuDialogFragment.show(supportFragmentManager, ContextMenuDialogFragment.TAG)
        }
    }

    override fun onBackPressed() {
        if (::contextMenuDialogFragment.isInitialized && contextMenuDialogFragment.isAdded) {
            contextMenuDialogFragment.dismiss()
        } else {
            finish()
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) hideSystemUI()
    }

    private fun hideSystemUI() {
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN)
    }

    private fun showSystemUI() {
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
    }
}