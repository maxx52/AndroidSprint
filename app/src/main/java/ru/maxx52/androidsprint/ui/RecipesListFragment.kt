package ru.maxx52.androidsprint.ui

import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ru.maxx52.androidsprint.databinding.FragmentRecipesListBinding
import ru.maxx52.androidsprint.data.NON_RECIPE
import ru.maxx52.androidsprint.data.STUB
import ru.maxx52.androidsprint.model.Recipe
import ru.maxx52.androidsprint.ui.recipes.recipelist.RecipesListViewModel

class RecipesListFragment : Fragment() {
    private var _binding: FragmentRecipesListBinding? = null
    private val binding get() = _binding ?: throw IllegalStateException("View is not initialized")
    private var categoryId: Int? = null
    private var categoryName: String? = null
    private var categoryImageUrl: String? = null
    private val viewModel: RecipesListViewModel by viewModels()
    private val args: RecipesListFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentRecipesListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        args.let {
            categoryId = it.Category.id
            categoryName = it.Category.title
            categoryImageUrl = it.Category.imageUrl
        }

        try {
            val drawable = Drawable.createFromStream(requireContext().assets.open(categoryImageUrl ?: ""), null)
            binding.ivRecipe.setImageDrawable(drawable)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        binding.tvTitleRecipe.text = categoryName
        viewModel.state.observe(viewLifecycleOwner) { newState ->
            initRecycler(newState.recipes)
        }
        categoryId?.let { viewModel.loadRecipesByCategory(it) }
    }

    private fun initRecycler(recipes: List<Recipe>) {
        val recipesAdapter = RecipesListAdapter(recipes)
        binding.rvRecipes.adapter = recipesAdapter
        recipesAdapter.setOnItemClickListener(object : RecipesListAdapter.OnItemClickListener {
            override fun onItemClick(recipeId: Int) {
                openRecipeByRecipeId(recipeId)
            }
        })
    }

    private fun openRecipeByRecipeId(recipeId: Int) {
        val recipe = STUB.getRecipesByCategoryId(categoryId ?: -1).find { it.id == recipeId }
        if (recipe == null) {
            Toast.makeText(requireContext(), NON_RECIPE, Toast.LENGTH_SHORT).show()
            return
        }

        val directions = RecipesListFragmentDirections.actionRecipesListFragmentToRecipeFragment(recipeId)
        findNavController().navigate(directions)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}