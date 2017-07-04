package nz.co.codebros.quakesnz.settings

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import dagger.android.support.DaggerAppCompatActivity

import nz.co.codebros.quakesnz.R

/**
 * Created by leandro on 9/08/15.
 */
class SettingsActivity : DaggerAppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
    }
}
