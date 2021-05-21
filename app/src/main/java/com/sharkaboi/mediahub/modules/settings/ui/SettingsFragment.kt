package com.sharkaboi.mediahub.modules.settings.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate.*
import androidx.lifecycle.viewModelScope
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.sharkaboi.mediahub.R
import com.sharkaboi.mediahub.common.constants.AppConstants
import com.sharkaboi.mediahub.common.data.sharedpref.SharedPreferencesKeys
import com.sharkaboi.mediahub.common.data.wrappers.MHTaskState
import com.sharkaboi.mediahub.modules.auth.OAuthActivity
import com.sharkaboi.mediahub.modules.profile.vm.setLoading
import com.sharkaboi.mediahub.modules.profile.vm.setProfileFailure
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        findPreference<SwitchPreferenceCompat>(SharedPreferencesKeys.DARK_MODE)?.setOnPreferenceChangeListener { _, newValue ->

            setDefaultNightMode(
                if (newValue as Boolean) {
                    MODE_NIGHT_YES
                } else {
                    MODE_NIGHT_NO
                }
            )
            true
        }
    }
//
//    private val showLogOutDialog = View.OnClickListener {
//        MaterialAlertDialogBuilder(requireContext())
//            .setTitle("Log out?")
//            .setMessage("This is permanent and you have to log in again after to use MediaHub.")
//            .setPositiveButton("Yes, Log me out") { _, _ ->
//                profileViewModel.logOutUser()
//            }
//            .setNegativeButton("No, take me back") { dialog, _ ->
//                dialog.dismiss()
//            }.show()
//    }
//
//    private val showAboutDialog = View.OnClickListener {
//        MaterialAlertDialogBuilder(requireContext())
//            .setTitle("About")
//            .setMessage(AppConstants.description)
//            .setPositiveButton(android.R.string.ok) { dialog, _ ->
//                dialog.dismiss()
//            }
//            .setNegativeButton("Github") { _, _ ->
//                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(AppConstants.githubLink)))
//                activity?.overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
//            }
//            .setNeutralButton("View licenses") { _, _ ->
//                startActivity(Intent(context, OssLicensesMenuActivity::class.java))
//                activity?.overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
//            }.show()
//    }
//
//    is ProfileStates.LogOutSuccess -> {
//        moveToOAuthScreen()
//    }
//
//    fun logOutUser() {
//        viewModelScope.launch {
//            _uiState.setLoading()
//            val result: MHTaskState<Unit> = profileRepository.logOutUser()
//            if (result.isSuccess) {
//                _uiState.setLogOutSuccess()
//            } else {
//                _uiState.setProfileFailure(result.error.errorMessage)
//            }
//        }
//    }
//    private fun moveToOAuthScreen() {
//        startActivity(Intent(context, OAuthActivity::class.java))
//        activity?.overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
//        activity?.finishAffinity()
//    }
}