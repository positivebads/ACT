package com.automatedcontacttracing.act.utils

import android.content.Context
import com.automatedcontacttracing.act.basicinfo.model.BasicInfo
import com.automatedcontacttracing.act.healthdeclaration.model.HealthDeclaration
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class SharedPrefHelper @Inject constructor(@ActivityContext private val context: Context) {
    private val SHARED_PREF_KEY = "act_shared_pref_key"

    private val LAST_NAME_KEY = "last_name"
    private val FIRST_NAME_KEY = "first_name_key"
    private val MIDDLE_INITIAL_KEY = "middle_initial_key"
    private val AGE_KEY = "age_key"
    private val GENDER_KEY = "gender_key"
    private val EMAIL_KEY = "email_key"
    private val CONTACT_NUMBER_KEY = "contact_number_key"
    private val ADDRESS_KEY = "address_key"
    private val TEMPERATURE_KEY = "temperature_key"

    private val FEVER_KEY = "fever_key"
    private val WORSENING_COUGH_KEY = "worsening_cough_key"
    private val SORE_THROAT_KEY = "sore_throat_key"
    private val DIFFICULTY_OF_BREATHING_KEY = "diffulty_of_breathing_key"
    private val LOSS_OFF_SMELL_KEY = "loss_of_smell_key"
    private val RUNNY_NOSE_KEY = "runny_nose_key"
    private val HEADACHE_KEY = "headache_key"
    private val MUSCLE_PAIN_KEY = "muscle_pain_key"
    private val DIARRHEA_KEY = "diarrhea_key"
    private val CLOSE_CONTACT_KEY = "close_contact_key"
    private val AFFIRMATIVE_KEY = "AFFIRMATIVE_KEY"

    private val IMAGE_PATH_KEY = "image_path_key"

    private val mSharedPref = context.getSharedPreferences(SHARED_PREF_KEY, Context.MODE_PRIVATE)

    fun saveBasicInfo(info: BasicInfo) {
        saveString(LAST_NAME_KEY, info.lastName)
        saveString(FIRST_NAME_KEY, info.firstName)
        saveString(MIDDLE_INITIAL_KEY, info.middleInitial)
        saveString(AGE_KEY, info.age)
        saveString(GENDER_KEY, info.gender)
        saveString(EMAIL_KEY, info.emailAddress)
        saveString(CONTACT_NUMBER_KEY, info.contactNumber)
        saveString(ADDRESS_KEY, info.address)
        saveString(TEMPERATURE_KEY, info.temperature)
    }

    fun saveHealthDeclaration(healthDeclaration: HealthDeclaration) {
        saveBoolean(FEVER_KEY, healthDeclaration.withFever)
        saveBoolean(WORSENING_COUGH_KEY, healthDeclaration.withWoresningCough)
        saveBoolean(SORE_THROAT_KEY, healthDeclaration.withSoreThroat)
        saveBoolean(DIFFICULTY_OF_BREATHING_KEY, healthDeclaration.withDifficultyOfBreathing)
        saveBoolean(LOSS_OFF_SMELL_KEY, healthDeclaration.withLossOfSmell)
        saveBoolean(RUNNY_NOSE_KEY, healthDeclaration.withRunnyNose)
        saveBoolean(HEADACHE_KEY, healthDeclaration.withHeadache)
        saveBoolean(MUSCLE_PAIN_KEY, healthDeclaration.withMusclePain)
        saveBoolean(DIARRHEA_KEY, healthDeclaration.withDiarrhea)
        saveBoolean(CLOSE_CONTACT_KEY, healthDeclaration.withCloseContact)
        saveBoolean(AFFIRMATIVE_KEY, healthDeclaration.affirmative)
    }

    fun setAbsoluteImagePath(path: String) {
        saveString(IMAGE_PATH_KEY, path)
    }


    fun getLastName() = getString(LAST_NAME_KEY)
    fun getFirstName() = getString(FIRST_NAME_KEY)
    fun getMiddleName() = getString(MIDDLE_INITIAL_KEY)
    fun getAge() = getString(AGE_KEY)
    fun getGender() = getString(GENDER_KEY)
    fun getEmail() = getString(EMAIL_KEY)
    fun getContactNumber() = getString(CONTACT_NUMBER_KEY)
    fun getAddress() = getString(ADDRESS_KEY)
    fun getTemperature() = getString(TEMPERATURE_KEY)

    fun getWithFever() = getBoolean(FEVER_KEY)
    fun getWithCough() = getBoolean(WORSENING_COUGH_KEY)
    fun getWithSoreThroat() = getBoolean(SORE_THROAT_KEY)
    fun getWithDifficultyOfBreathing() = getBoolean(DIFFICULTY_OF_BREATHING_KEY)
    fun getWithLossOfSmell() = getBoolean(LOSS_OFF_SMELL_KEY)
    fun getWithRunnyNose() = getBoolean(RUNNY_NOSE_KEY)
    fun getWithHeadache() = getBoolean(HEADACHE_KEY)
    fun getWithMusclePain() = getBoolean(MUSCLE_PAIN_KEY)
    fun getWithDiarrhea() = getBoolean(DIARRHEA_KEY)
    fun getWithContact() = getBoolean(CLOSE_CONTACT_KEY)
    fun getAffirmation() = getBoolean(AFFIRMATIVE_KEY)

    fun getImagePath() = getString(IMAGE_PATH_KEY)


    private fun saveString(key: String, value: String) {
        val sp = mSharedPref.edit()
        sp.putString(key, value)
        sp.apply()
    }

    private fun getString(key: String) = mSharedPref.getString(key, "")

    private fun saveBoolean(key: String, value: Boolean) {
        val sp = mSharedPref.edit()
        sp.putBoolean(key, value)
        sp.apply()
    }

    private fun getBoolean(key: String) = mSharedPref.getBoolean(key, false)

}