package nz.co.codebros.quakesnz.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.util.Linkify
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import nz.co.codebros.quakesnz.R


/**
 * Created by Leandro on 24/07/2014.
 */
class InfoFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater?,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater?.inflate(R.layout.fragment_info, container, false)

        view?.let {
            val txtTitle = it.findViewById<TextView>(R.id.title)
            txtTitle.setText(arguments.getInt(ARG_TITLE))

            val bodyView = it.findViewById<TextView>(R.id.body)
            bodyView.setText(arguments.getInt(ARG_BODY))
            Linkify.addLinks(bodyView, Linkify.ALL)
        }

        return view
    }

    companion object {

        val ARG_TITLE = "arg_title"
        val ARG_BODY = "arg_body"

        fun newInstance(title: Int, body: Int): InfoFragment {
            val bundle = Bundle()
            bundle.putInt(ARG_TITLE, title)
            bundle.putInt(ARG_BODY, body)

            val fragment = InfoFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}
