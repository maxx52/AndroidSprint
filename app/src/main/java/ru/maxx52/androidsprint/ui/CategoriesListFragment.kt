package ru.maxx52.androidsprint.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.maxx52.androidsprint.R
import ru.maxx52.androidsprint.databinding.FragmentListCategoriesBinding
import ru.maxx52.androidsprint.data.STUB
import ru.maxx52.androidsprint.data.ARG_CATEGORY_ID
import ru.maxx52.androidsprint.data.ARG_CATEGORY_IMAGE_URL
import ru.maxx52.androidsprint.data.ARG_CATEGORY_NAME
import ru.maxx52.androidsprint.model.Category
import ru.maxx52.androidsprint.ui.categories.CategoriesViewModel

class FragmentListCategories : Fragment() {
    private var _binding: FragmentListCategoriesBinding? = null
    private val binding get() = _binding ?: throw IllegalStateException("View is not initialized")

    private val viewModel: CategoriesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListCategoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.state.observe(viewLifecycleOwner) { newState ->
            initRecycler(newState.categories)
        }
        viewModel.loadCategories()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initRecycler(categories: List<Category>?) {
        val categoriesAdapter = CategoriesListAdapter(categories ?: emptyList())
        binding.rvCategories.adapter = categoriesAdapter
        categoriesAdapter.setOnItemClickListener(object : CategoriesListAdapter.OnItemClickListener {
            override fun onItemClick(categoryId: Int) {
                openRecipesByCategoryId(categoryId)
            }
        })
    }

    fun openRecipesByCategoryId(categoryId: Int) {
        val category = STUB.getCategories().find { it.id == categoryId } ?: return
        val bundle = Bundle().apply {
            putInt(ARG_CATEGORY_ID, category.id)
            putString(ARG_CATEGORY_NAME, category.title)
            putString(ARG_CATEGORY_IMAGE_URL, category.imageUrl)
        }
        findNavController().navigate(R.id.recipesListFragment, args = bundle)
    }
}