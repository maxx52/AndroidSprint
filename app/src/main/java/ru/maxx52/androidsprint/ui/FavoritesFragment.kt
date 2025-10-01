package ru.maxx52.androidsprint.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.maxx52.androidsprint.databinding.FragmentFavoritesBinding
import ru.maxx52.androidsprint.model.Recipe
import ru.maxx52.androidsprint.ui.recipes.favorites.FavoritesViewModel

class FavoritesFragment : Fragment() {
    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding ?: throw IllegalStateException("View is not initialized")
    private val viewModel: FavoritesViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loadFavoriteRecipes()
        viewModel.state.observe(viewLifecycleOwner) { newState ->
            if (newState.recipes.isNotEmpty()) {
                initRecycler(newState.recipes)
            }

            if (newState.error != null) {
                Toast.makeText(requireContext(), newState.error, Toast.LENGTH_SHORT).show()
            }

            if (newState.navigateToRecipe && newState.recipeId != null) {
                val directions = FavoritesFragmentDirections.actionFavoritesFragmentToRecipeFragment(newState.recipeId)
                findNavController().navigate(directions)
            }
        }
    }

    private fun initRecycler(recipes: List<Recipe>) {
        val adapter = RecipesListAdapter(recipes)
        binding.rvFavorites.adapter = adapter

        adapter.setOnItemClickListener(object : RecipesListAdapter.OnItemClickListener {
            override fun onItemClick(recipeId: Int) {
                viewModel.openRecipeByRecipeId(recipeId)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}