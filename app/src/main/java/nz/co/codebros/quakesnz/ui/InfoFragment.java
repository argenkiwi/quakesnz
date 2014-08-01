package nz.co.codebros.quakesnz.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import nz.co.codebros.quakesnz.R;


/**
 * Created by Leandro on 24/07/2014.
 */
public class InfoFragment extends Fragment {

    public static final String ARG_TITLE = "arg_title";
    public static final String ARG_BODY = "arg_body";

    public static InfoFragment newInstance(int title, int body) {
        InfoFragment f = new InfoFragment();

        Bundle args = new Bundle();
        args.putInt(ARG_TITLE, title);
        args.putInt(ARG_BODY, body);
        f.setArguments(args);

        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_info, container, false);
        ((TextView) v.findViewById(R.id.title)).setText(getArguments().getInt(ARG_TITLE));
        TextView bodyView = (TextView) v.findViewById(R.id.body);
        bodyView.setText(getArguments().getInt(ARG_BODY));
        Linkify.addLinks(bodyView, Linkify.ALL);
        return v;
    }
}
