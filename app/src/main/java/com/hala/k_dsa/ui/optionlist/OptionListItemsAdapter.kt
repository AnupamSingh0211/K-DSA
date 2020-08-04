package com.hala.k_dsa.ui.optionlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hala.k_dsa.R
import com.hala.k_dsa.ui.optionlist.OptionListItemsAdapter.BulletViewHolder

/**
 * @author ashish
 * @since 12/03/18
 */
class OptionListItemsAdapter(
    val mItems: ArrayList<OptionItemModel>?,
    val optionItemClickListner: ((OptionItemModel?) -> Unit)?
) :
    RecyclerView.Adapter<BulletViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BulletViewHolder {
        return BulletViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.option_item, parent, false)
        )
    }

    override fun onBindViewHolder(
        holder: BulletViewHolder,
        position: Int
    ) {
        holder.updateUi(mItems!![position])
    }

    override fun getItemCount(): Int {
        return mItems?.size ?: 0
    }

    inner class BulletViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        private val tvOptionText: TextView
        fun updateUi(optionItemModel: OptionItemModel) {
            tvOptionText.text = optionItemModel.key
            tvOptionText.setOnClickListener {
                optionItemClickListner?.invoke(optionItemModel)

            }
        }

        init {
            tvOptionText = itemView.findViewById(R.id.tv_option_text)
        }
    }
}