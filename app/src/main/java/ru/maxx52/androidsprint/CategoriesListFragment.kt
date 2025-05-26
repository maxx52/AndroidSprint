package ru.maxx52.androidsprint

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.commit
import ru.maxx52.androidsprint.databinding.FragmentListCategoriesBinding
import ru.maxx52.androidsprint.entities.STUB

const val ARG_CATEGORY_IMAGE_URL = "categoryImageUrl"
const val ARG_CATEGORY_NAME = "categoryName"
const val ARG_CATEGORY_ID = "categoryId"

class FragmentListCategories : Fragment() {
    private var _binding: FragmentListCategoriesBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException("View is not initialized")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListCategoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initRecycler() {
        val categoriesAdapter = CategoriesListAdapter(STUB.getCategories())
        binding.rvCategories.adapter = categoriesAdapter

        categoriesAdapter.setOnItemClickListener(object : CategoriesListAdapter.OnItemClickListener {
            override fun onItemClick(categoryId: Int) = openRecipesByCategoryId()
        })
    }

    fun openRecipesByCategoryId() {
        val categoryId = STUB.getCategories()[0].id
        val categoryName = STUB.getCategories()[0].title
        val categoryImageUrl = STUB.getCategories()[0].imageUrl
        val bundle = Bundle()
        bundle.putInt(ARG_CATEGORY_ID, categoryId)
        bundle.putString(ARG_CATEGORY_NAME, categoryName)
        bundle.putString(ARG_CATEGORY_IMAGE_URL, categoryImageUrl)
        parentFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.mainContainer, RecipesListFragment())
        }
    }
}