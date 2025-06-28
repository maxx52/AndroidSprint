package ru.maxx52.androidsprint.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import ru.maxx52.androidsprint.R
import ru.maxx52.androidsprint.databinding.FragmentFavoritesBinding
import ru.maxx52.androidsprint.data.ARG_RECIPE_ID
import ru.maxx52.androidsprint.data.FAVORITES_KEY
import ru.maxx52.androidsprint.data.NON_RECIPE
import ru.maxx52.androidsprint.data.PREFS_NAME
import ru.maxx52.androidsprint.model.Recipe
import ru.maxx52.androidsprint.data.STUB

class FavoritesFragment : Fragment() {
    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding ?: throw RuntimeException("FragmentFavoritesBinding = null")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
    }

    private fun initRecycler() {
        val adapter = RecipesListAdapter(getFavoriteRecipes())
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

    private fun getFavoriteRecipes(): List<Recipe> {
        val favoritesSet = getFavorites()
        val favoritesIds = favoritesSet.mapNotNull { it.toIntOrNull() }.toSet()
        return STUB.getRecipesByIds(favoritesIds)
    }

    private fun getFavorites(): Set<String> {
        val sharedPrefs = requireActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return sharedPrefs.getStringSet(FAVORITES_KEY, emptySet()) ?: emptySet()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}