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

class RecipesListFragment : Fragment() {
    private var _binding: FragmentRecipesListBinding? = null
    private val binding get() = _binding ?: throw IllegalStateException("View is not initialized")

    private var categoryId: Int? = null
    private var categoryName: String? = null
    private var categoryImageUrl: String? = null

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
            categoryName = it.getString(ARG_CATEGORY_NAME) ?: "Без названия"
            categoryImageUrl = it.getString(ARG_CATEGORY_IMAGE_URL) ?: ""
        }
        initRecycler()
    }

    private fun initRecycler() {
        val categoriesAdapter = CategoriesListAdapter(STUB.getCategories())
        binding.rvRecipes.adapter = categoriesAdapter

        categoriesAdapter.setOnItemClickListener(object : CategoriesListAdapter.OnItemClickListener {
            override fun onItemClick(categoryId: Int) {
                openRecipeByRecipeId(categoryId)
            }
        })
    }

    fun openRecipeByRecipeId(recipeId: Int) {
        val recipeFragment = RecipeFragment().apply {
            arguments = Bundle().apply {
                putInt(ARG_RECIPE_ID, recipeId)
            }
        }
        parentFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.mainContainer, recipeFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}