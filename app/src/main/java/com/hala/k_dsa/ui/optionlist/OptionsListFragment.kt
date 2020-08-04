package com.hala.k_dsa.ui.optionlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.hala.k_dsa.R
import kotlinx.android.synthetic.main.fragment_options_list.*

class OptionsListFragment : Fragment() {

    private lateinit var galleryViewModel: OptionsListViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        galleryViewModel =
            ViewModelProviders.of(this).get(OptionsListViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_options_list, container, false)

//        galleryViewModel.text.observe(viewLifecycleOwner, Observer {
//
//        })

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val optionItemList = arguments?.getParcelableArrayList<OptionItemModel>("listOptions")
        fragment_option_list.layoutManager = LinearLayoutManager(activity)
        fragment_option_list.adapter = OptionListItemsAdapter(optionItemList) {

            if (it != null) {
                if (it.value is String) {
                    //open the list till now

                } else if (it.value is Map<*, *>) {
                    // reopen the option list


                }
            }

        }
    }}


