package ru.maxx52.androidsprint

import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import ru.maxx52.androidsprint.databinding.FragmentRecipesListBinding
import ru.maxx52.androidsprint.entities.ARG_CATEGORY_ID
import ru.maxx52.androidsprint.entities.ARG_CATEGORY_IMAGE_URL
import ru.maxx52.androidsprint.entities.ARG_CATEGORY_NAME
import ru.maxx52.androidsprint.entities.ARG_RECIPE_ID
import ru.maxx52.androidsprint.entities.NON_RECIPE
import ru.maxx52.androidsprint.entities.STUB

class RecipesListFragment : Fragment() {
    private var _binding: FragmentRecipesListBinding? = null
    private val binding get() = _binding ?: throw IllegalStateException("View is not initialized")

    private var categoryId: Int? = null
    private var categoryName: String? = null
    private var categoryImageUrl: String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentRecipesListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            categoryId = it.getInt(ARG_CATEGORY_ID, -1)
            categoryName = it.getString(ARG_CATEGORY_NAME) ?: "Без названия"
            categoryImageUrl = it.getString(ARG_CATEGORY_IMAGE_URL) ?: ""
        }
        val drawable = Drawable.createFromStream(categoryImageUrl?.let {
            binding.ivRecipe.context.assets.open(it)
        }, null)
        binding.tvTitleRecipe.text = categoryName
        binding.ivRecipe.setImageDrawable(drawable)
        try {
            val drawable = Drawable.createFromStream(requireContext().assets
                .open(categoryImageUrl ?: ""), null)
            binding.ivRecipe.setImageDrawable(drawable)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        initRecycler()
    }

    private fun initRecycler() {
        val recipesAdapter = RecipesListAdapter(STUB.getRecipesByCategoryId(categoryId ?: -1))
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

        val bundle = Bundle().apply {
            putInt(ARG_RECIPE_ID, recipe.id)
        }

        parentFragmentManager.commit {
            setReorderingAllowed(true)
            replace<RecipeFragment>(R.id.mainContainer, args = bundle)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}