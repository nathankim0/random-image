package com.jinyeob.nathanks

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.os.PowerManager
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.provider.Settings
import android.util.Log
import android.view.View
import android.view.WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
import android.view.WindowManager
import android.widget.Toast
import androidx.core.view.WindowCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.play.core.review.ReviewManagerFactory
import dagger.hilt.android.HiltAndroidApp
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date

@HiltAndroidApp
class MainApplication : Application() {

    init {
        instance = this
    }

    companion object {
        const val TAG = "tag_default"
        const val TAG_LIFECYCLE = "tag_lifecycle"

        private var sToast: Toast? = null

        private lateinit var instance: MainApplication

        fun applicationContext(): Context {
            return instance.applicationContext
        }

        fun getCurrentCountry(): String = applicationContext().resources.configuration.locales.get(0).country

        @SuppressLint("BatteryLife")
        fun checkBatteryOptimizationsAndGetPermission(context: Context) {
            if (!isIgnoringBatteryOptimizations(context)) {
                val intent = Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS)
                intent.data = Uri.parse("package:${context.packageName}")
                context.startActivity(intent)
            }
        }

        private fun isIgnoringBatteryOptimizations(context: Context): Boolean =
            (context.getSystemService(Context.POWER_SERVICE) as PowerManager).isIgnoringBatteryOptimizations(
                context.packageName
            )

        fun isOnline(): Boolean {
            val connectivityManager =
                applicationContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                    return true
                }
            }
            return false
        }

        fun showToast(context: Context?, message: String?, duration: Int = Toast.LENGTH_SHORT) {
            if (sToast == null) {
                sToast = Toast.makeText(context, message, duration)
            } else {
                sToast!!.setText(message)
            }
            sToast!!.show()
        }

        fun showInAppReview() {
            try {
                val manager = ReviewManagerFactory.create(applicationContext())
                val request = manager.requestReviewFlow()
                request.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "in app review success: ${task.result}")
                    } else {
                        Log.d(TAG, "in app review failed")
                    }
                }
            }
            // NOTE: ReviewManager 는 play store 에 배포되고 UI 검증을 할 수 있다.(빌드 환경에서는 로그로만 확인 가능)
            // 중요한 기능은 아니지만 터지면 안되니 일단 catch 로 잡음.
            catch (e: Exception) {
                Log.e(TAG, "Error occurred at InAppReview", e)
            }
        }

        fun Activity.initStatusBar() {
            setStatusBarTransparent()
            setStatusBarIconColorBlack()
        }

        fun Activity.setStatusBarTransparent() {
            window.apply {
                @Suppress("DEPRECATION")
                decorView.systemUiVisibility =
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                statusBarColor = Color.TRANSPARENT
            }
        }

        fun Activity.cutStatusBar() {
            window.run {
                setFlags(
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
                )
                statusBarColor = Color.TRANSPARENT
            }
            if (Build.VERSION.SDK_INT >= 30) { // API 30 에 적용
                WindowCompat.setDecorFitsSystemWindows(window, false)
            }
        }

        fun Activity.setStatusBarIconColorWhite() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                window.insetsController?.setSystemBarsAppearance(0, APPEARANCE_LIGHT_STATUS_BARS)
            } else {
                @Suppress("DEPRECATION")
                window?.decorView?.let { it.systemUiVisibility = it.systemUiVisibility and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv() }
            }
        }

        fun Activity.setStatusBarIconColorBlack() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                window.insetsController?.setSystemBarsAppearance(APPEARANCE_LIGHT_STATUS_BARS, APPEARANCE_LIGHT_STATUS_BARS)
            } else {
                @Suppress("DEPRECATION")
                window?.decorView?.let { it.systemUiVisibility = it.systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR }
            }
        }

        fun getStatusBarHeight(): Int {
            val resourceId = applicationContext().resources.getIdentifier(
                "status_bar_height",
                "dimen",
                "android"
            )
            return if (resourceId > 0) applicationContext().resources.getDimensionPixelSize(
                resourceId
            )
            else 0
        }

        fun getNavigationHeight(): Int {
            val resourceId = applicationContext().resources.getIdentifier(
                "navigation_bar_height",
                "dimen",
                "android"
            )
            return if (resourceId > 0) applicationContext().resources.getDimensionPixelSize(
                resourceId
            )
            else 0
        }

        @SuppressLint("UseCompatLoadingForDrawables")
        @Throws(PackageManager.NameNotFoundException::class)
        private fun getResource(resName: String): Drawable? {
            val resContext =
                applicationContext().createPackageContext(applicationContext().packageName, 0)
            val res = resContext.resources
            val id = res.getIdentifier(resName, "drawable", applicationContext().packageName)
            return res.getDrawable(id, null)
        }

        @Throws(IOException::class)
        fun createImageFile(): File? {
            val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
            val imageFileName = BuildConfig.APPLICATION_ID.replace(".", "_") + timeStamp + "_"
            val storageDir =
                applicationContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            return File.createTempFile(
                imageFileName, /* prefix */
                ".jpg", /* suffix */
                storageDir /* directory */
            )
        }

        fun vibrateClick() {
            val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                (applicationContext().getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager).defaultVibrator
            } else {
                @Suppress("DEPRECATION")
                applicationContext().getSystemService(VIBRATOR_SERVICE) as Vibrator
            }

            vibrator.vibrate(VibrationEffect.createOneShot(60, 150))
        }

        fun vibrateHeavyClick() {
            val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                (applicationContext().getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager).defaultVibrator
            } else {
                @Suppress("DEPRECATION")
                applicationContext().getSystemService(VIBRATOR_SERVICE) as Vibrator
            }

            val timings = longArrayOf(0, 70, 150, 70)
            val amplitudes = intArrayOf(0, 255, 0, 255)
            vibrator.vibrate(VibrationEffect.createWaveform(timings, amplitudes, -1))
        }

        fun getFragmentName(fragment: Fragment): String? {
            return fragment.javaClass.simpleName
        }

        fun getFragmentName(fragment: FragmentActivity): String? {
            return fragment.javaClass.simpleName
        }

        fun getFragmentName(fragment: DialogFragment): String? {
            return fragment.javaClass.simpleName
        }

        fun getFragmentName(fragment: BottomSheetDialogFragment): String? {
            return fragment.javaClass.simpleName
        }

        fun getFragmentName(fragment: BottomSheetDialog): String? {
            return fragment.javaClass.simpleName
        }

        fun getActivityName(activity: Activity): String? {
            return activity.javaClass.simpleName
        }
    }
}
