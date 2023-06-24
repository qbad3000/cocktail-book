package com.gmail.domanskiquba.android.cocktailbook

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gmail.domanskiquba.android.cocktailbook.databinding.CocktailListFragmentBinding
import com.gmail.domanskiquba.android.cocktailbook.databinding.CocktailListItemBinding
import com.google.android.material.tabs.TabLayout
import com.squareup.picasso.Picasso

class CocktailListFragment : Fragment() {

    interface Callbacks {
        fun onCocktailSelected()
    }

    private var callbacks: Callbacks? = null
    private val cocktailBookViewModel: CocktailBookViewModel by activityViewModels()
    private lateinit var binding: CocktailListFragmentBinding
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
        binding = CocktailListFragmentBinding.inflate(inflater)

        binding.cocktailBookViewModel = cocktailBookViewModel

        tabLayout = binding.tabLayout
        cocktailsListRecyclerView = binding.cocktailsListRecyclerView
        cocktailsListRecyclerView.layoutManager = LinearLayoutManager(context)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

            override fun onTabSelected(tab: TabLayout.Tab?) {
                cocktailBookViewModel.selectedTab.set(tab!!.position)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })

        tabLayout.selectTab(tabLayout.getTabAt(cocktailBookViewModel.selectedTab.get()))

        cocktailBookViewModel.exposedList.observe(
            viewLifecycleOwner,
            { value -> cocktailsListRecyclerView.adapter = CocktailAdapter(value) }
        )

        val searchView = binding.menuItemSearch

        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {
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
            holder.bind(cocktailsList[position])
        }
    }

}