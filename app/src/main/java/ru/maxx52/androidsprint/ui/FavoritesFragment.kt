package ru.maxx52.androidsprint.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import ru.maxx52.androidsprint.databinding.FragmentFavoritesBinding
import ru.maxx52.androidsprint.model.Recipe
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
                viewModel.openRecipeByRecipeId(recipeId, requireActivity() as AppCompatActivity, this@FavoritesFragment)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}