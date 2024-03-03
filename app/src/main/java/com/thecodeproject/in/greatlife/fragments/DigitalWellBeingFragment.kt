package com.thecodeproject.`in`.greatlife.fragments

import android.app.AppOpsManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.text.format.DateUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import com.thecodeproject.`in`.greatlife.R
import com.thecodeproject.`in`.greatlife.databinding.FragmentDigitalWellBeingBinding

class DigitalWellBeingFragment : Fragment() {

    //view binding
    private lateinit var binding: FragmentDigitalWellBeingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDigitalWellBeingBinding.inflate(layoutInflater)

        if (hasUsageAccessPermission()) {
            // Get and display screen time
            val screenTime = getScreenTime()
            binding.screenTimeTextView.text = formatTime(screenTime)

            // Get and display app usage
            val appUsageList = getAppUsageList()
            val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, appUsageList)
            binding.appUsageListView.adapter = adapter
        } else {
            requestUsageAccessPermission()
        }

        return binding.root
    }


    private fun hasUsageAccessPermission(): Boolean {
        val appOps = requireContext().getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
        val mode = appOps.checkOpNoThrow(
            AppOpsManager.OPSTR_GET_USAGE_STATS,
            android.os.Process.myUid(),
            requireContext().packageName
        )
        return mode == AppOpsManager.MODE_ALLOWED
    }


    private fun requestUsageAccessPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            startActivity(Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS))
        } else {
            Toast.makeText(requireContext(), "Usage access permission required", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getScreenTime(): Long {
        val usageStatsManager = requireContext().getSystemService(Context.USAGE_STATS_SERVICE) as android.app.usage.UsageStatsManager
        val endTime = System.currentTimeMillis()
        val startTime = endTime - DateUtils.DAY_IN_MILLIS // You can adjust the time range as needed
        return usageStatsManager.queryUsageStats(
            android.app.usage.UsageStatsManager.INTERVAL_DAILY,
            startTime,
            endTime
        ).sumBy { it.totalTimeInForeground.toInt() }.toLong()
    }
    private fun getAppUsageList(): List<String> {
        val usageStatsManager = requireContext().getSystemService(Context.USAGE_STATS_SERVICE) as android.app.usage.UsageStatsManager
        val endTime = System.currentTimeMillis()
        val startTime = endTime - DateUtils.DAY_IN_MILLIS // You can adjust the time range as needed
        val packageManager = requireContext().packageManager

        val usageStats = usageStatsManager.queryUsageStats(
            android.app.usage.UsageStatsManager.INTERVAL_DAILY,
            startTime,
            endTime
        )

        val filteredUsageStats = usageStats
            .filter { it.totalTimeInForeground > 0 }
            .sortedByDescending { it.totalTimeInForeground }

        return filteredUsageStats.map { usageStat ->
            try {
                val appName = packageManager.getApplicationLabel(packageManager.getApplicationInfo(usageStat.packageName, PackageManager.GET_META_DATA)).toString()
                "$appName: ${formatTime(usageStat.totalTimeInForeground)}"
            } catch (e: PackageManager.NameNotFoundException) {
                // Log or handle the exception
                "${usageStat.packageName}: ${formatTime(usageStat.totalTimeInForeground)}"
            }
        }
    }

    private fun formatTime(milliseconds: Long): String {
        return DateUtils.formatElapsedTime(milliseconds / 1000)
    }

}