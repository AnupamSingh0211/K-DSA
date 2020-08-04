package com.hala.k_dsa.ui.optionlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.hala.k_dsa.R
import com.hala.k_dsa.ui.home.WebViewFragment
import kotlinx.android.synthetic.main.fragment_options_list.*

class OptionsListFragment : Fragment() {

    private lateinit var galleryViewModel: OptionsListViewModel


    companion object {
        val TAG = "OptionsListFragment"


        fun getInstance(optionItemsList: ArrayList<OptionItemModel>): OptionsListFragment {

            val fragment = OptionsListFragment()

            val bundle = Bundle()
            bundle.putParcelableArrayList("listOptions", optionItemsList)
            fragment.arguments = bundle
            return fragment

        }
    }

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
                if (it.value ==null || it.value is String ) {
                    //open the list till now
                    fragment_option_list.visibility = View.GONE
                    action_container.visibility = View.VISIBLE

                    childFragmentManager.beginTransaction()
                        .add(
                            R.id.action_container,
                            WebViewFragment.getInstance("https://androidiots.github.io/K-DSA/"+ it.path + "/"+ it.key+ ".html")
                        )
                        .addToBackStack(TAG)
                        .commit()


                } else if (it.value is Map<*, *>) {
                    // reopen the option list

                    var optionsList = ArrayList<OptionItemModel>()
                    for ((key, value) in it.value) {
                        val path = it.path + "/"+ it.key
                        optionsList.add(OptionItemModel(key as String?, value,path = path))
                    }

                    fragment_option_list.visibility = View.GONE
                    action_container.visibility = View.VISIBLE

                    childFragmentManager.beginTransaction()
                        .add(
                            R.id.action_container,
                            OptionsListFragment.getInstance(optionsList)
                        )
                        .addToBackStack(TAG)
                        .commit()


                } else if (it.value is ArrayList<*>) {
                    // reopen the option list

                    var optionsList = ArrayList<OptionItemModel>()
                    for (key in it.value) {
                        val path = it.path + "/"+it.key

                        optionsList.add(OptionItemModel(key as String?, path = path))
                    }

                    fragment_option_list.visibility = View.GONE
                    action_container.visibility = View.VISIBLE

                    childFragmentManager.beginTransaction()
                        .add(
                            R.id.action_container,
                            OptionsListFragment.getInstance(optionsList)
                        )
                        .addToBackStack(TAG)
                        .commit()


                }
            }

        }
    }
}


