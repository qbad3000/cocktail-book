package com.gmail.domanskiquba.android.cocktailbook

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.gmail.domanskiquba.android.cocktailbook.databinding.CocktailDetailsBinding
import com.gmail.domanskiquba.android.cocktailbook.databinding.IngredientBinding
import com.squareup.picasso.Picasso

class CocktailDetailsFragment : Fragment() {
    private val cocktailBookViewModel: CocktailBookViewModel by activityViewModels()
    private lateinit var binding: CocktailDetailsBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.cocktail_details,
            container,
            false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cocktailBookViewModel.selected.observe(
            viewLifecycleOwner,
            {   cocktail ->
                bind(cocktail)
            }
        )

    }

    fun bind(cocktail: Cocktail) {
        binding.cocktail = cocktail
        binding.executePendingBindings()

        Picasso.get().load("${cocktail.thumbnail}").into(binding.cocktailImage)

        binding.ingredientsList.removeAllViews()

        cocktail.ingredients.forEach {
            val ingredientBinding = DataBindingUtil.inflate<IngredientBinding>(layoutInflater, R.layout.ingredient, binding.ingredientsList, true)
            ingredientBinding.ingredient = it
            ingredientBinding.executePendingBindings()
        }

        binding.favourite.setOnClickListener() {
            cocktailBookViewModel.favouriteSwitch(cocktail)
        }
    }
}