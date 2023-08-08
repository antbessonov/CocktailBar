package org.bessonov.cocktailbar.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.*
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.bessonov.cocktailbar.R
import org.bessonov.cocktailbar.adapter.IngredientListAdapter
import org.bessonov.cocktailbar.databinding.FragmentAddCocktailBinding
import org.bessonov.cocktailbar.model.IngredientUi
import org.bessonov.cocktailbar.state.AddCocktailEvent
import org.bessonov.cocktailbar.state.AddCocktailState
import org.bessonov.cocktailbar.viewmodel.AddCocktailViewModel
import javax.inject.Inject

@AndroidEntryPoint
class AddCocktailFragment : Fragment() {

    private var _binding: FragmentAddCocktailBinding? = null
    private val binding: FragmentAddCocktailBinding
        get() = _binding ?: throw RuntimeException("FragmentAddCocktailBinding == null")

    private val viewModel by lazy {
        ViewModelProvider(this)[AddCocktailViewModel::class.java]
    }

    @Inject
    lateinit var ingredientListAdapter: IngredientListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddCocktailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModelState()
        observeViewModelEvent()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observeViewModelState() {
        viewModel.uiState
            .flowWithLifecycle(
                lifecycle = viewLifecycleOwner.lifecycle,
                minActiveState = Lifecycle.State.CREATED
            )
            .onEach { state -> handleState(state = state) }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun handleState(state: AddCocktailState) {
        setTitleTextChanged()
        setIngredientList(state = state)
        setIngredientAddClickListener()
        setOnSaveClickListener()
        setOnCancelClickListener()
        setupAddIngredientDialogListener()
    }

    private fun setTitleTextChanged() {
        binding.titleEdt.doOnTextChanged { text, _, _, _ ->
            viewModel.updateTitle(value = text)
        }
    }

    private fun setIngredientList(state: AddCocktailState) {
        binding.ingredientListRv.adapter = ingredientListAdapter
        ingredientListAdapter.submitList(state.ingredientList)
    }

    private fun setIngredientAddClickListener() {
        ingredientListAdapter.onAddIngredientClick = {
            findNavController()
                .navigate(R.id.action_navigation_add_cocktail_to_navigation_add_ingredient)
        }
    }

    private fun setOnSaveClickListener() {
        binding.saveBtn.setOnClickListener {
            viewModel.clickedSave()
        }
    }

    private fun setOnCancelClickListener() {
        binding.cancelBtn.setOnClickListener {
            viewModel.clickedCancel()
        }
    }

    private fun observeViewModelEvent() {
        viewModel.event
            .flowWithLifecycle(
                lifecycle = viewLifecycleOwner.lifecycle,
                minActiveState = Lifecycle.State.CREATED
            )
            .onEach { event -> handleEvent(event = event) }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun handleEvent(event: AddCocktailEvent?) {
        when (event) {
            is AddCocktailEvent.ClickCancel -> reduce(event = event)
            is AddCocktailEvent.EmptyTitle -> reduce(event = event)
            is AddCocktailEvent.EmptyIngredientList -> reduce(event = event)
            is AddCocktailEvent.Save -> reduce(event = event)
            null -> hideTitleError()
        }
    }

    private fun reduce(event: AddCocktailEvent.ClickCancel) {
        findNavController().navigate(R.id.action_navigation_add_cocktail_to_navigation_my_cocktails)
    }

    private fun reduce(event: AddCocktailEvent.EmptyTitle) {
        binding.titleTil.error = getString(R.string.add_title)
    }

    private fun reduce(event: AddCocktailEvent.EmptyIngredientList) {
        Toast.makeText(
            requireContext(),
            getString(R.string.empty_ingredient_list_error_description),
            Toast.LENGTH_SHORT
        ).show()
        viewModel.hideEvent()
    }

    private fun reduce(event: AddCocktailEvent.Save) {
        setFragmentResult(
            requestKey = REQUEST_KEY,
            result = bundleOf(RESPONSE_KEY to SAVE)
        )
        findNavController().navigate(R.id.action_navigation_add_cocktail_to_navigation_my_cocktails)
    }

    private fun hideTitleError() {
        binding.titleTil.error = null
    }

    private fun setupAddIngredientDialogListener() {
        AddIngredientDialogFragment
            .setupListener(parentFragmentManager, this) { ingredientTitle ->
                if (ingredientTitle != null) {
                    viewModel.updateIngredientList(ingredient = IngredientUi(title = ingredientTitle))
                }
            }
    }

    companion object {

        const val SAVE = "Save"

        private val TAG: String = AddCocktailFragment::class.java.simpleName
        private val REQUEST_KEY = "$TAG:defaultRequestKey"
        private const val RESPONSE_KEY = "RESPONSE"

        fun setupListener(
            manager: FragmentManager,
            lifecycleOwner: LifecycleOwner,
            saveListener: (String?) -> Unit
        ) {
            manager.setFragmentResultListener(REQUEST_KEY, lifecycleOwner) { _, result ->
                saveListener.invoke(result.getString(RESPONSE_KEY))
            }
        }
    }
}