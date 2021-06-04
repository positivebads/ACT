package com.automatedcontacttracing.act.base.presentation

import android.app.Dialog
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.automatedcontacttracing.act.MainViewModel
import com.automatedcontacttracing.act.R
import com.automatedcontacttracing.act.ToolbarData
import com.automatedcontacttracing.act.utils.SharedPrefHelper
import dagger.hilt.android.AndroidEntryPoint
import java.util.regex.Pattern
import javax.inject.Inject


@AndroidEntryPoint
abstract class BaseFragment : Fragment() {
    private var mLoadingDialog: Dialog? = null

    @Inject
    lateinit var mSharedPrefHelper: SharedPrefHelper

    private val mMainViewModel: MainViewModel by activityViewModels()

    abstract fun getLayoutResId(): Int
    abstract fun shouldShowBackButton(): Boolean

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(getLayoutResId(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLoadingDialog()
        mMainViewModel.toolbarLiveData.value = ToolbarData(shouldShowBackButton())
    }

    fun showError(errorMessage: String) {
        showDialog(getString(R.string.error), errorMessage)
    }

    fun CharSequence?.isValidEmail() =
        !isNullOrEmpty() && Pattern.compile(
            "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                    + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                    + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                    + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                    + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                    + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$"
        ).matcher(this).matches()


    fun CharSequence?.isValidMobileNumber() =
        !isNullOrEmpty() && this?.length == 13 && this.startsWith("+639")

    private fun showDialog(title: String, message: String) {
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.error_dialog)

        val tvTitle = dialog.findViewById<TextView>(R.id.tvTitle)
        val tvMessage = dialog.findViewById<TextView>(R.id.tvMessage)
        val tvOk = dialog.findViewById<TextView>(R.id.tvOk)

        tvTitle.text = title
        tvMessage.text = message

        tvOk.setOnClickListener {
            dialog.dismiss()
        }

        dialog.setCancelable(false)
        dialog.show()
    }

    fun showLoading() {
        if (mLoadingDialog != null && !mLoadingDialog!!.isShowing)
            mLoadingDialog!!.show()
    }

    fun hideLoading() {
        if (mLoadingDialog != null && mLoadingDialog!!.isShowing)
            mLoadingDialog!!.dismiss()
    }

    private fun initLoadingDialog() {
        mLoadingDialog = Dialog(requireContext())
        mLoadingDialog!!.setContentView(R.layout.loading_dialog)
        mLoadingDialog!!.setCancelable(false)
        mLoadingDialog!!.window!!.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
    }
}