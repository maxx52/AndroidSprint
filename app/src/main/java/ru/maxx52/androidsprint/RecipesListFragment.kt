package ru.maxx52.androidsprint

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.commit
import ru.maxx52.androidsprint.databinding.FragmentRecipesListBinding
import ru.maxx52.androidsprint.entities.STUB

const val ARG_RECIPE_ID = "recipeId"
const val ARG_RECIPE_NAME = "recipeName"
const val ARG_RECIPE_IMAGE_URL = "recipeImageUrl"

class RecipesListFragment : Fragment() {
    private var _binding: FragmentRecipesListBinding? = null
    private val binding get() = _binding ?: throw IllegalStateException("View is not initialized")

    private var recipeName: String? = null
    private var recipeImageUrl: String? = null
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
            categoryId = it.getInt(ARG_CATEGORY_ID, -1)
            recipeName = it.getString(ARG_RECIPE_NAME) ?: "Без названия"
            recipeImageUrl = it.getString(ARG_RECIPE_IMAGE_URL) ?: ""
        }
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
        bundle.putString(ARG_RECIPE_NAME, recipeName)
        bundle.putString(ARG_RECIPE_IMAGE_URL, recipeImageUrl)
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