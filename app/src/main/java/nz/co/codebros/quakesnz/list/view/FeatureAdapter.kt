package nz.co.codebros.quakesnz.list.view

import android.view.View
import android.view.ViewGroup
import nz.co.codebros.quakesnz.core.data.Feature
import nz.co.codebros.quakesnz.util.BaseAdapter
import nz.co.codebros.quakesnz.util.RecyclableViewHolder

class FeatureAdapter(
        private val onItemClicked: (view: View, feature: Feature) -> Unit
) : BaseAdapter() {

    override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
    ) = RecyclableViewHolder(FeatureViewHolder(parent, onItemClicked))

    override fun onBindViewHolder(holder: RecyclableViewHolder<*>, position: Int) {
        when(val item = getItem(position)){
          is  FeatureViewHolder.Properties -> (holder as RecyclableViewHolder<FeatureViewHolder.Properties>).bind(item)
        }
    }
}
