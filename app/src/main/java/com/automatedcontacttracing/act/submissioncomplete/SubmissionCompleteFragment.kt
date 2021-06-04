package com.automatedcontacttracing.act.submissioncomplete

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import com.automatedcontacttracing.act.R
import com.automatedcontacttracing.act.base.presentation.BaseFragment
import com.automatedcontacttracing.act.utils.QRCodeGenerator
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.WithFragmentBindings
import kotlinx.android.synthetic.main.fragment_submission_complete.*
import javax.inject.Inject

@AndroidEntryPoint
class SubmissionCompleteFragment : BaseFragment() {

    private val mArgs: SubmissionCompleteFragmentArgs by navArgs()
    override fun getLayoutResId(): Int = R.layout.fragment_submission_complete
    override fun shouldShowBackButton(): Boolean = false

    @Inject
    lateinit var mGson: Gson

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        imgQrCode.setImageBitmap(activity?.let {
            QRCodeGenerator.generateQrCode(
                it,
                mGson.toJson(mArgs.data)
            )
        })

        btnExit.setOnClickListener {
            requireActivity().finish()
        }
    }
}