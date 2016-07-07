package nz.co.codebros.quakesnz.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.util.Linkify;
import android.widget.TextView;

import nz.co.codebros.quakesnz.R;

public class AboutActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        Linkify.addLinks((TextView) findViewById(R.id.about_body), Linkify.ALL);
        Linkify.addLinks((TextView) findViewById(R.id.acknowledgements_body), Linkify.ALL);
    }
}
