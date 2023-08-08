package org.bessonov.cocktailbar.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.bessonov.cocktailbar.R
import org.bessonov.cocktailbar.databinding.FragmentDialogAddIngredientBinding
import org.bessonov.cocktailbar.state.AddIngredientDialogEvent
import org.bessonov.cocktailbar.state.AddIngredientDialogState
import org.bessonov.cocktailbar.util.setWidthPercent
import org.bessonov.cocktailbar.viewmodel.AddIngredientDialogViewModel

@AndroidEntryPoint
class AddIngredientDialogFragment : DialogFragment() {

    private var _binding: FragmentDialogAddIngredientBinding? = null
    private val binding: FragmentDialogAddIngredientBinding
        get() = _binding ?: throw RuntimeException("FragmentDialogAddIngredientBinding == null")

    private val viewModel by lazy {
        ViewModelProvider(this)[AddIngredientDialogViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDialogAddIngredientBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setWidthPercent(90)
        dialog?.window?.setBackgroundDrawableResource(R.drawable.dialog_bg)
        observeViewModelState()
        observeViewModelEvent()
        setOnAddClickListener()
        setOnCancelClickListener()
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

    private fun handleState(state: AddIngredientDialogState) {
        setTitleTextChanged()
    }

    private fun setTitleTextChanged() {
        binding.titleEdt.doOnTextChanged { text, _, _, _ ->
            viewModel.updateTitle(value = text)
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

    private fun handleEvent(event: AddIngredientDialogEvent?) {
        when (event) {
            is AddIngredientDialogEvent.EmptyTitle -> reduce(event = event)
            is AddIngredientDialogEvent.Save -> reduce(event = event)
            null -> hideTitleError()
        }
    }

    private fun reduce(event: AddIngredientDialogEvent.EmptyTitle) {
        binding.titleTil.error = getString(R.string.add_title)
    }

    private fun reduce(event: AddIngredientDialogEvent.Save) {
        setFragmentResult(
            requestKey = REQUEST_KEY,
            result = bundleOf(RESPONSE_KEY to viewModel.uiState.value.title)
        )
        dismiss()
    }

    private fun hideTitleError() {
        binding.titleTil.error = null
    }

    private fun setOnAddClickListener() {
        binding.addBtn.setOnClickListener {
            viewModel.clickedAdd()
        }
    }

    private fun setOnCancelClickListener() {
        binding.cancelBtn.setOnClickListener {
            dismiss()
        }
    }

    companion object {

        private val TAG: String = AddIngredientDialogFragment::class.java.simpleName
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