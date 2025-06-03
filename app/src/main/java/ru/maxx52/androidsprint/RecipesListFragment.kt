package ru.maxx52.androidsprint

import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.commit
import ru.maxx52.androidsprint.databinding.FragmentRecipesListBinding
import ru.maxx52.androidsprint.entities.ARG_CATEGORY_ID
import ru.maxx52.androidsprint.entities.ARG_CATEGORY_IMAGE_URL
import ru.maxx52.androidsprint.entities.ARG_CATEGORY_NAME
import ru.maxx52.androidsprint.entities.ARG_RECIPE_ID
import ru.maxx52.androidsprint.entities.STUB

class RecipesListFragment : Fragment() {
    private var _binding: FragmentRecipesListBinding? = null
    private val binding get() = _binding ?: throw IllegalStateException("View is not initialized")

    private var categoryName: String? = null
    private var categoryImageUrl: String? = null
    private var categoryId: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipesListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            categoryImageUrl = it.getString(ARG_CATEGORY_IMAGE_URL) ?: ""
            categoryId = it.getInt(ARG_CATEGORY_ID)
            categoryName = it.getString(ARG_CATEGORY_NAME) ?: "Без названия"
        }
        val drawable = Drawable.createFromStream(categoryImageUrl?.let {
            binding.ivRecipe.context.assets.open(it)
        }, null)
        binding.tvTitleRecipe.text = categoryName
        binding.ivRecipe.setImageDrawable(drawable)
        initRecycler()
    }

    private fun initRecycler() {
        val recipesAdapter = RecipesListAdapter(STUB.getRecipesByCategoryId(categoryId))
        binding.rvRecipes.adapter = recipesAdapter

        recipesAdapter.setOnItemClickListener(object : RecipesListAdapter.OnItemClickListener {
            override fun onItemClick(recipeId: Int) {
                openRecipeByRecipeId(recipeId)
            }
        })
    }

    fun openRecipeByRecipeId(recipeId: Int) {
        val bundle = Bundle()
        bundle.putInt(ARG_RECIPE_ID, recipeId)
        parentFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.mainContainer, RecipeFragment())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}