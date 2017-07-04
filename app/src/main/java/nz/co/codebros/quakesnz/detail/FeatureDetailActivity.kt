package nz.co.codebros.quakesnz.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import dagger.android.support.DaggerAppCompatActivity

class FeatureDetailActivity : DaggerAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
            return Intent(context, FeatureDetailActivity::class.java)
        }
    }
}
