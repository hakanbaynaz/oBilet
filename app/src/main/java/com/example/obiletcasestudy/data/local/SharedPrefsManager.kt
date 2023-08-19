package com.example.obiletcasestudy.data.local

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedPrefsManager @Inject constructor(
    @ApplicationContext context: Context
) {

    companion object {
        private const val OBILET_PREFS = "OBILET_PREFS"
        private const val EQUIPMENT_ID_KEY = "LOGIN_RESPONSE_KEY"
    }

    private val mPref = context.getSharedPreferences(OBILET_PREFS, Context.MODE_PRIVATE)

    fun saveEquipmentId(uniqueId: String) {
        mPref.edit().putString(EQUIPMENT_ID_KEY, uniqueId).apply()
    }

    fun getEquipmentId() = mPref.getString(EQUIPMENT_ID_KEY, null)

}