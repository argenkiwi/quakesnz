package nz.co.codebros.quakesnz.about

import android.os.Bundle
import android.text.util.Linkify
import androidx.appcompat.app.AppCompatActivity
import nz.co.codebros.quakesnz.databinding.ActivityAboutBinding

class AboutActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAboutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Linkify.addLinks(binding.aboutBody, Linkify.ALL)
        Linkify.addLinks(binding.acknowledgementsBody, Linkify.ALL)
    }
}
