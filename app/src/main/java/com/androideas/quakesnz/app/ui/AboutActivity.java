package com.androideas.quakesnz.app.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.util.Linkify;
import android.widget.TextView;

import com.androideas.quakesnz.app.R;

public class AboutActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        Linkify.addLinks((TextView) findViewById(R.id.about_body), Linkify.ALL);
        Linkify.addLinks((TextView) findViewById(R.id.acknowledgements_body), Linkify.ALL);
    }
}
