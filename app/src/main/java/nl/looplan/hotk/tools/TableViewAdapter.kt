package nl.looplan.hotk.tools

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.evrencoskun.tableview.adapter.AbstractTableAdapter
import com.evrencoskun.tableview.adapter.recyclerview.holder.AbstractViewHolder
import kotlinx.android.synthetic.main.fragment_bank_cell.view.*
import nl.looplan.hotk.R

class CellViewHolder(val view: View) : AbstractViewHolder(view) {
    val textView: TextView
    get() {
        return this.view.fragment_bank_table_cell_text
    }
}

class TableViewAdapter : AbstractTableAdapter<String, String, String>() {
    override fun onCreateColumnHeaderViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AbstractViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.fragment_bank_cell, parent, false)
        return CellViewHolder(layout)
    }

    override fun onBindColumnHeaderViewHolder(
        holder: AbstractViewHolder,
        columnHeaderItemModel: String?,
        columnPosition: Int
    ) {
        if(holder is CellViewHolder) {
            holder.textView.text = columnHeaderItemModel
        }
    }

    override fun onBindRowHeaderViewHolder(
        holder: AbstractViewHolder,
        rowHeaderItemModel: String?,
        rowPosition: Int
    ) {
        if(holder is CellViewHolder) {
            holder.textView.text = rowHeaderItemModel
        }
    }

    override fun onCreateRowHeaderViewHolder(parent: ViewGroup, viewType: Int): AbstractViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.fragment_bank_cell, parent, false)
        return CellViewHolder(layout)
    }

    override fun getCellItemViewType(position: Int): Int {
        return 0
    }

    override fun onCreateCellViewHolder(parent: ViewGroup, viewType: Int): AbstractViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.fragment_bank_cell, parent, false)
        return CellViewHolder(layout)
    }

    override fun onCreateCornerView(parent: ViewGroup): View {
        return LayoutInflater.from(parent.context).inflate(R.layout.fragment_bank_cell, parent, false)
    }

    override fun onBindCellViewHolder(
        holder: AbstractViewHolder,
        cellItemModel: String?,
        columnPosition: Int,
        rowPosition: Int
    ) {
        if(holder is CellViewHolder) {
            holder.textView.text = cellItemModel
        }
    }

    override fun getColumnHeaderItemViewType(position: Int): Int {
        return 0
    }

    override fun getRowHeaderItemViewType(position: Int): Int {
        return 0
    }

}