package nz.co.codebros.quakesnz.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.app.FragmentTransaction
import android.support.v4.view.ViewPager
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity

import nz.co.codebros.quakesnz.R

/**
 * Created by Leandro on 24/07/2014.
 */
class InfoActivity : AppCompatActivity(), ActionBar.TabListener, ViewPager.OnPageChangeListener {

    private var mViewPager: ViewPager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_info)

        mViewPager = findViewById<ViewPager>(R.id.pager)
        mViewPager?.adapter = object : FragmentStatePagerAdapter(supportFragmentManager) {

            override fun getItem(i: Int): Fragment {
                val title: Int
                val body: Int

                when (i) {
                    0 -> {
                        title = R.string.before_title
                        body = R.string.before_body
                    }
                    1 -> {
                        title = R.string.during_title
                        body = R.string.during_body
                    }
                    2 -> {
                        title = R.string.after_title
                        body = R.string.after_body
                    }
                    else -> {
                        title = R.string.after_title
                        body = R.string.after_body
                    }
                }

                return InfoFragment.newInstance(title, body)
            }

            override fun getCount(): Int = 3
        }

        mViewPager?.addOnPageChangeListener(this)

        supportActionBar?.let {
            it.navigationMode = ActionBar.NAVIGATION_MODE_TABS
            it.addTab(it.newTab().setText(R.string.before).setTabListener(this))
            it.addTab(it.newTab().setText(R.string.during).setTabListener(this))
            it.addTab(it.newTab().setText(R.string.after).setTabListener(this))
        }
    }

    override fun onTabSelected(tab: ActionBar.Tab, fragmentTransaction: FragmentTransaction) {
        mViewPager?.currentItem = tab.position
    }

    override fun onTabUnselected(tab: ActionBar.Tab, fragmentTransaction: FragmentTransaction) {

    }

    override fun onTabReselected(tab: ActionBar.Tab, fragmentTransaction: FragmentTransaction) {

    }

    override fun onPageScrolled(i: Int, v: Float, i2: Int) {

    }

    override fun onPageSelected(i: Int) {
        supportActionBar?.setSelectedNavigationItem(i)
    }

    override fun onPageScrollStateChanged(i: Int) {

    }
}
