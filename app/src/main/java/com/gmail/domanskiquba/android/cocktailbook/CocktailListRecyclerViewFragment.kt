package com.gmail.domanskiquba.android.cocktailbook

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.BaseObservable
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gmail.domanskiquba.android.cocktailbook.Cocktail
import com.gmail.domanskiquba.android.cocktailbook.databinding.CocktailListItemBinding
import com.squareup.picasso.Picasso

class CocktailListRecyclerViewFragment : Fragment() {
    private lateinit var cocktailsListRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.cocktail_list_recycler_view_fragment, container, false)

        cocktailsListRecyclerView = view.findViewById(R.id.cocktails_list_recycler_view)
        cocktailsListRecyclerView.layoutManager = LinearLayoutManager(context)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val cocktailsListLiveData = TheCocktailDBFetcher().fetchCocktailsList()
        cocktailsListLiveData.observe(
            viewLifecycleOwner,
            Observer{   cocktailsList ->
                cocktailsListRecyclerView.adapter = CocktailAdapter(cocktailsList)
            }
        )
    }

    companion object {
        fun newInstance() = CocktailListRecyclerViewFragment()
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

            binding.favourite.setOnClickListener {
                cocktail.favourite = !cocktail.favourite
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