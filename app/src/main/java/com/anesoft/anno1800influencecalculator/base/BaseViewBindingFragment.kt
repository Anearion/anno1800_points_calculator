package com.anesoft.anno1800influencecalculator.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.BuildConfig
import androidx.viewbinding.ViewBinding
import java.lang.reflect.ParameterizedType


abstract class BaseViewBindingFragment<VB : ViewBinding, VM : BaseViewModel>() : Fragment() {

    open var useSharedViewModel: Boolean = false

    private var _binding: VB? = null
    val binding: VB get() = _binding!!

    private var _viewModel: VM? = null
    val viewModel: VM get() = _viewModel!!
    protected abstract fun getViewModelClass(): Class<VM>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViewModel()
        viewModel.onCreate()
    }

    override fun onResume() {
        super.onResume()
        viewModel.onResume()
        onResumeUpdate()
    }

    private fun setupViewModel() {
        _viewModel = if (useSharedViewModel) {
            ViewModelProvider(requireActivity()).get(
                getViewModelClass()
            )
        } else {
            ViewModelProvider(this).get(getViewModelClass())
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setupViewBinding(container)
        if (BuildConfig.DEBUG)
            Toast.makeText(context, this.javaClass.name, Toast.LENGTH_SHORT).show()
        return _binding!!.root
    }

    private fun setupViewBinding(container: ViewGroup?) {
        val type = javaClass.genericSuperclass
        val clazz = (type as ParameterizedType).actualTypeArguments[0] as Class<VB>
        val method = clazz.getMethod(
            "inflate",
            LayoutInflater::class.java,
            ViewGroup::class.java,
            Boolean::class.java
        )
        _binding = method.invoke(null, layoutInflater, container, false) as VB
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onViewCreation(savedInstanceState)
    }

    abstract fun onViewCreation(savedInstanceState: Bundle?)
    open fun onResumeUpdate(){}

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}