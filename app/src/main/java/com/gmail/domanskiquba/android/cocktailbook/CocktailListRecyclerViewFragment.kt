package com.gmail.domanskiquba.android.cocktailbook

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gmail.domanskiquba.android.cocktailbook.Cocktail

class CocktailListRecyclerViewFragment : Fragment() {
    private lateinit var cocktailsListRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.cocktail_list_recycler_view_fragment, container, false)

        cocktailsListRecyclerView = view.findViewById(R.id.cocktails_list_recycler_view)
        cocktailsListRecyclerView.layoutManager = GridLayoutManager(context, 3)

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

    private class CocktailHolder(itemTextView: TextView) : RecyclerView.ViewHolder(itemTextView) {
        val bindTitle: (CharSequence) -> Unit = itemTextView::setText
    }

    private class CocktailAdapter(private val cocktailsList: List<Cocktail>)
        : RecyclerView.Adapter<CocktailHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CocktailHolder {
            val textView = TextView(parent.context)
            return CocktailHolder(textView)
        }

        override fun getItemCount(): Int = cocktailsList.size

        override fun onBindViewHolder(holder: CocktailHolder, position: Int) {
            val cocktail = cocktailsList[position]
            holder.bindTitle(cocktail.id.toString())
        }
    }

}