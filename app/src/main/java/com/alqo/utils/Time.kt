package com.alqo.utils

import java.util.Calendar
import java.util.Date


object Time {
    fun timeAgo(postTimestamp: Date): String {
        val currentTime = Calendar.getInstance().time
        val timeDifferenceMillis = currentTime.time - postTimestamp.time

        val timeDifferenceMinutes = timeDifferenceMillis / (60 * 1000)
        val timeDifferenceHours = timeDifferenceMillis / (60 * 60 * 1000)
        val timeDifferenceDays = timeDifferenceMillis / (24 * 60 * 60 * 1000)

        return when {
            timeDifferenceMinutes < 60 -> "${timeDifferenceMinutes}min"
            timeDifferenceHours < 24 -> "${timeDifferenceHours}h"
            else -> "${timeDifferenceDays}d"
        }
    }
}