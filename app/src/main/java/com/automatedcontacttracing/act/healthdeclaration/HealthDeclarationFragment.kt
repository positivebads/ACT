package com.automatedcontacttracing.act.healthdeclaration

import android.os.Bundle
import android.view.View
import android.widget.RadioGroup
import androidx.navigation.findNavController
import com.automatedcontacttracing.act.R
import com.automatedcontacttracing.act.base.presentation.BaseFragment
import com.automatedcontacttracing.act.healthdeclaration.model.HealthDeclaration
import kotlinx.android.synthetic.main.fragment_basic_info.btnNext
import kotlinx.android.synthetic.main.fragment_health_declaration.*

class HealthDeclarationFragment : BaseFragment() {

    private val mRadioGroupList = mutableListOf<RadioGroup>()
    override fun getLayoutResId(): Int = R.layout.fragment_health_declaration
    override fun shouldShowBackButton(): Boolean = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        collectAllRadioGroups()
        cbAffirmation.setOnCheckedChangeListener { _, isChecked ->
            btnNext.isEnabled = isChecked
        }

        btnNext.setOnClickListener {

            if (hasRadioGroupWithNoSelectedItem()) {
                showError("Please make sure to answer all the questions provided!")
                return@setOnClickListener
            }

            if (!userIsAffirmative()) {
                return@setOnClickListener
            }

            saveDeclaration()
            val action =
                HealthDeclarationFragmentDirections.actionHealthDeclarationFragmentToIdCaptureFragment()
            it.findNavController().navigate(action)
        }
    }

    private fun hasRadioGroupWithNoSelectedItem(): Boolean {
        var foundNoSelectedItem = false
        mRadioGroupList.forEach {
            if (it.checkedRadioButtonId == -1) {
                foundNoSelectedItem = true
                return@forEach
            }
        }
        return foundNoSelectedItem
    }

    private fun userIsAffirmative(): Boolean = cbAffirmation.isChecked

    private fun collectAllRadioGroups() {
        mRadioGroupList.add(rgFever)
        mRadioGroupList.add(rgCough)
        mRadioGroupList.add(rgSoreThroat)
        mRadioGroupList.add(rgDifficultyOfBreathing)
        mRadioGroupList.add(rgLossOfSmell)
        mRadioGroupList.add(rgRunnynose)
        mRadioGroupList.add(rgHeadache)
        mRadioGroupList.add(rgMusclePain)
        mRadioGroupList.add(rgDiarrhea)
        mRadioGroupList.add(rgWithContact)
    }

    private fun saveDeclaration() {
        val healthDeclaration = HealthDeclaration(
            rbFeverYes.isChecked,
            rbCoughYes.isChecked,
            rbSoreThroatYes.isChecked,
            rbDifficultyOfBreathingYes.isChecked,
            rbLossOfSmellYes.isChecked,
            rbRunnyNoseYes.isChecked,
            rbHeadacheYes.isChecked,
            rbMusclePainYes.isChecked,
            rbDiarrheaYes.isChecked,
            rbWithContactYes.isChecked,
            cbAffirmation.isChecked
        )
        mSharedPrefHelper.saveHealthDeclaration(healthDeclaration)
    }
}