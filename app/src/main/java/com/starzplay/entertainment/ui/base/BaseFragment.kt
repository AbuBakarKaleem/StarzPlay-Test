package com.starzplay.entertainment.ui.base

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.starzplay.entertainment.R.string

typealias Inflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T

abstract class BaseFragment<VB : ViewBinding>(private val inflate: Inflate<VB>) : Fragment() {

    private var _binding: VB? = null
    protected val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = inflate.invoke(inflater, container, false)
        return requireNotNull(_binding).root
    }

    fun showAlertDialog(
        title: String = "",
        messages: String = "",
        posText: String = getString(string.text_ok),
        negText: String = getString(string.text_close),
        onPosClick: DialogInterface.OnClickListener? = null,
        onNegClick: DialogInterface.OnClickListener? = null,
        cancelable: Boolean = false
    ) {

        val alert =
            MaterialAlertDialogBuilder(requireContext()).setTitle(title).setMessage(messages)

        if (onPosClick != null) {
            alert.setPositiveButton(posText, onPosClick)
        }
        if (onNegClick != null) {
            alert.setNegativeButton(negText, onNegClick)
        }
        alert.setCancelable(cancelable)

        alert.show()

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }

}