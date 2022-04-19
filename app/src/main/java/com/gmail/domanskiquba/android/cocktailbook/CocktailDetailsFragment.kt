package com.gmail.domanskiquba.android.cocktailbook

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.gmail.domanskiquba.android.cocktailbook.databinding.CocktailDetailsBinding
import com.squareup.picasso.Picasso

class CocktailDetailsFragment : Fragment() {
    private val cocktailBookViewModel: CocktailBookViewModel by activityViewModels()
    private lateinit var binding: CocktailDetailsBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate<CocktailDetailsBinding>(
            inflater,
            R.layout.cocktail_details,
            container,
            false
        )



        return binding.root;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        activity?.actionBar?.hide()

        cocktailBookViewModel.selected.observe(
            viewLifecycleOwner,
            Observer{   cocktail ->
                bind(cocktail)
            }
        )
    }



    override fun onDetach() {
        super.onDetach()
        activity?.actionBar?.show()
    }

    fun bind(cocktail: Cocktail) {
        binding.cocktail = cocktail
        binding.executePendingBindings()

        Picasso.get().load("${cocktail.thumbnail}/preview").into(binding.cocktailImage)
    }

}