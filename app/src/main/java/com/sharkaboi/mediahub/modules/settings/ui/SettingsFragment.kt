package com.sharkaboi.mediahub.modules.settings.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate.*
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import com.github.javiersantos.appupdater.AppUpdater
import com.github.javiersantos.appupdater.enums.Display
import com.github.javiersantos.appupdater.enums.UpdateFrom
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.sharkaboi.mediahub.BuildConfig
import com.sharkaboi.mediahub.R
import com.sharkaboi.mediahub.common.constants.AppConstants
import com.sharkaboi.mediahub.common.extensions.observe
import com.sharkaboi.mediahub.common.extensions.showNoActionOkDialog
import com.sharkaboi.mediahub.common.extensions.showToast
import com.sharkaboi.mediahub.common.util.openUrl
import com.sharkaboi.mediahub.data.sharedpref.SharedPreferencesKeys
import com.sharkaboi.mediahub.modules.auth.ui.OAuthActivity
import com.sharkaboi.mediahub.modules.settings.vm.SettingsStates
import com.sharkaboi.mediahub.modules.settings.vm.SettingsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : PreferenceFragmentCompat() {

    private val settingsViewModel by viewModels<SettingsViewModel>()
    private val navController by lazy { findNavController() }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setData()
        setListeners()
        setObservers()
    }

    private fun setData() {
        findPreference<Preference>(SharedPreferencesKeys.ABOUT)?.summary =
            getString(R.string.app_version_template, BuildConfig.VERSION_NAME)
        findPreference<MaterialToolBarPreference>(SharedPreferencesKeys.TOOLBAR)?.setNavigationIconListener {
            navController.navigateUp()
        }
    }

    private fun setListeners() {
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
        findPreference<Preference>(SharedPreferencesKeys.ABOUT)?.setOnPreferenceClickListener { _ ->
            showAboutDialog()
            true
        }
        findPreference<Preference>(SharedPreferencesKeys.LOG_OUT)?.setOnPreferenceClickListener { _ ->
            showLogOutDialog()
            true
        }
        findPreference<Preference>(SharedPreferencesKeys.ANIME_NOTIFS)?.setOnPreferenceClickListener { _ ->
            showAnimeNotificationsDialog()
            true
        }
        findPreference<Preference>(SharedPreferencesKeys.UPDATES)?.setOnPreferenceClickListener { _ ->
            checkForUpdates()
            true
        }
    }

    private fun setObservers() {
        observe(settingsViewModel.uiState) { uiState ->
            when (uiState) {
                is SettingsStates.LogOutFailure -> showToast(uiState.message)
                SettingsStates.LogOutSuccess -> moveToOAuthScreen()
                else -> Unit
            }
        }
    }

    private fun checkForUpdates() {
        val appUpdater = AppUpdater(activity)
            .setUpdateFrom(UpdateFrom.GITHUB)
            .setGitHubUserAndRepo(AppConstants.githubUsername, AppConstants.githubRepoName)
            .showAppUpdated(true)
            .setDisplay(Display.DIALOG)
        appUpdater.start()
    }

    private fun showAnimeNotificationsDialog() =
        requireContext().showNoActionOkDialog(
            R.string.anime_notifs_hint,
            getString(R.string.coming_soon_hint)
        )

    private fun showLogOutDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.log_out_hint)
            .setMessage(R.string.log_out_message)
            .setPositiveButton(R.string.log_out_positive_hint) { _, _ ->
                settingsViewModel.logOutUser()
            }
            .setNegativeButton(R.string.log_out_negative_hint) { dialog, _ ->
                dialog.dismiss()
            }.show()
    }

    private fun showAboutDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.about)
            .setMessage(AppConstants.description)
            .setNeutralButton(R.string.view_licenses_hint) { _, _ ->
                openLicenses()
            }.setNegativeButton(R.string.github) { _, _ ->
                openUrl(AppConstants.githubLink)
                activity?.overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
            }.setPositiveButton(R.string.ok) { dialog, _ ->
                dialog.dismiss()
            }.show()
    }

    private fun openLicenses() {
        startActivity(Intent(context, OssLicensesMenuActivity::class.java))
        activity?.overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }

    private fun moveToOAuthScreen() {
        startActivity(Intent(context, OAuthActivity::class.java))
        activity?.overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        activity?.finishAffinity()
    }
}
