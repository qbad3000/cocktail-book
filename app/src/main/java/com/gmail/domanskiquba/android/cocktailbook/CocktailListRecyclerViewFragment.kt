package com.gmail.domanskiquba.android.cocktailbook

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.databinding.BaseObservable
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.liveData
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gmail.domanskiquba.android.cocktailbook.Cocktail
import com.gmail.domanskiquba.android.cocktailbook.databinding.CocktailListItemBinding
import com.google.android.material.tabs.TabLayout
import com.squareup.picasso.Picasso

class CocktailListRecyclerViewFragment : Fragment() {

    interface Callbacks {
        fun onCocktailSelected()
    }

    private var callbacks: Callbacks? = null
    private val cocktailBookViewModel: CocktailBookViewModel by activityViewModels()
    private lateinit var cocktailsList: LiveData<List<Cocktail>>
    private lateinit var cocktailsListRecyclerView: RecyclerView
    private lateinit var tabLayout: TabLayout

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks?
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.cocktail_list_recycler_view_fragment, container, false)

        tabLayout = view.findViewById(R.id.tabLayout)

        cocktailsListRecyclerView = view.findViewById(R.id.cocktails_list_recycler_view)
        cocktailsListRecyclerView.layoutManager = LinearLayoutManager(context)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

            override fun onTabSelected(tab: TabLayout.Tab?) {
                cocktailBookViewModel.selectedTab = tab!!.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
        cocktailBookViewModel.exposedList.observe(
            viewLifecycleOwner,
            Observer{   value ->
                cocktailsListRecyclerView.adapter = CocktailAdapter(value)
            }
        )


    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.cocktail_list, menu)

        val searchView = menu.findItem(R.id.menu_item_search).actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(queryText: String): Boolean {
                    cocktailBookViewModel.searchPhrase = queryText
                    return true
                }

                override fun onQueryTextChange(queryText: String): Boolean {
                    cocktailBookViewModel.searchPhrase = queryText
                    return false
                }
            })
    }


    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    private inner class CocktailHolder(private val binding: CocktailListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init{
            binding.cocktail = Cocktail()
        }


        fun bind(cocktail: Cocktail) {
            binding.cocktail = cocktail
            binding.executePendingBindings()

            Picasso.get().load("${cocktail.thumbnail}/preview").into(binding.thumbnail)

            binding.item.setOnClickListener() {
                cocktailBookViewModel.selected.value = cocktail
                callbacks?.onCocktailSelected()
            }

            binding.favourite.setOnClickListener {
                cocktailBookViewModel.favouriteSwitch(cocktail)
            }
        }
    }

    private inner class CocktailAdapter(private val cocktailsList: List<Cocktail>)
        : RecyclerView.Adapter<CocktailHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
                CocktailHolder {
            val binding = DataBindingUtil.inflate<CocktailListItemBinding>(
                LayoutInflater.from(parent.context),
                R.layout.cocktail_list_item,
                parent,
                false
            )
            return CocktailHolder(binding)
        }
        override fun getItemCount(): Int = cocktailsList.size

        override fun onBindViewHolder(holder: CocktailHolder, position: Int) {
            holder.bind(cocktailsList[position]);
        }
    }


}