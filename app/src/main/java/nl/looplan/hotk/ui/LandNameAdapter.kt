package nl.looplan.hotk.ui

import android.content.Context
import android.widget.ArrayAdapter
import android.widget.Filter
import java.util.*

class LandNameAdapter(context: Context, items: MutableList<String>, val normalizedItems: MutableList<NormalizedLandName>) : ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, items) {

    data class NormalizedLandName(val originalName: String, val normalizedName: String)

    companion object {
        fun normalizeString(landName: String): String {
            return landName.toLowerCase(Locale.ROOT).replace(" ", "")
        }

        fun normalizeListOfStrings(landNames: List<String>): MutableList<NormalizedLandName> {
            return landNames.map {
                landName -> NormalizedLandName(landName, normalizeString(landName))
            }.toMutableList()
        }
    }

    override fun getFilter(): Filter {
        return nameFilter
    }

    private val nameFilter: Filter = object : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            if(constraint != null) {
                val constraintNormalized = normalizeString(constraint.toString())

                val list = mutableListOf<String>()
                for(item in normalizedItems) {
                    if(item.normalizedName.contains(constraintNormalized)) {
                        list.add(item.originalName)
                    }
                }

                val results = FilterResults()
                results.values = list
                results.count = list.size
                return results
            }
            return FilterResults()
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            if(results != null && results.count > 0) {
                clear()
                addAll(results.values as MutableList<String>)
                notifyDataSetChanged()
            }
        }

    }
}