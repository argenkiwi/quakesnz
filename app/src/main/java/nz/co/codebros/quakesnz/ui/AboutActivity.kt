package nz.co.codebros.quakesnz.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.util.Linkify
import android.widget.TextView

import nz.co.codebros.quakesnz.R

class AboutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        Linkify.addLinks(findViewById<TextView>(R.id.about_body), Linkify.ALL)
        Linkify.addLinks(findViewById<TextView>(R.id.acknowledgements_body), Linkify.ALL)
    }
}
