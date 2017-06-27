package nz.co.codebros.quakesnz.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import nz.co.codebros.quakesnz.detail.QuakeDetailFragment

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (supportActionBar != null) supportActionBar!!.setHomeButtonEnabled(true)
        if (savedInstanceState == null) {
            val fragment: Fragment = if (intent.data != null) {
                QuakeDetailFragment.newInstance(intent.data.lastPathSegment)
            } else QuakeDetailFragment.newInstance()

            supportFragmentManager.beginTransaction()
                    .add(android.R.id.content, fragment)
                    .commit()
        }
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, DetailActivity::class.java)
        }
    }
}
