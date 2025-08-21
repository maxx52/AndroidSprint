package ru.maxx52.androidsprint.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.fragment.app.viewModels
import ru.maxx52.androidsprint.R
import ru.maxx52.androidsprint.databinding.FragmentFavoritesBinding
import ru.maxx52.androidsprint.data.ARG_RECIPE_ID
import ru.maxx52.androidsprint.data.NON_RECIPE
import ru.maxx52.androidsprint.model.Recipe
import ru.maxx52.androidsprint.data.STUB
import ru.maxx52.androidsprint.ui.recipes.favorites.FavoritesViewModel

class FavoritesFragment : Fragment() {
    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding ?: throw RuntimeException("FragmentFavoritesBinding = null")

    private val viewModel: FavoritesViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()

        viewModel.loadFavoriteRecipes()
        viewModel.state.observe(viewLifecycleOwner) { newState ->
            initRecycler(newState.recipes)
        }
    }

    private fun initRecycler(recipes: List<Recipe>? = null) {
        val adapter = RecipesListAdapter(recipes ?: emptyList())
        binding.rvFavorites.adapter = adapter

        adapter.setOnItemClickListener(object : RecipesListAdapter.OnItemClickListener {
            override fun onItemClick(recipeId: Int) {
                openRecipeByRecipeId(recipeId)
            }
        })
    }

    private fun openRecipeByRecipeId(recipeId: Int) {
        val recipe = STUB.getRecipeById(recipeId)
        if (recipe == null) {
            Toast.makeText(requireContext(), NON_RECIPE, Toast.LENGTH_SHORT).show()
            return
        }

        val bundle = Bundle().apply {
            putInt(ARG_RECIPE_ID, recipeId)
        }
        parentFragmentManager.commit {
            replace<RecipeFragment>(R.id.mainContainer, args = bundle)
            addToBackStack(null)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}