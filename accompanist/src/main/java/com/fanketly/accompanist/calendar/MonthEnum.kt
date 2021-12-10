package com.fanketly.accompanist.calendar

import java.time.DateTimeException

enum class MonthEnum {

    /**
     * The singleton instance for the month of January with 31 days.
     * This has the numeric value of `1`.
     */
    JANUARY,

    /**
     * The singleton instance for the month of February with 28 days, or 29 in a leap year.
     * This has the numeric value of `2`.
     */
    FEBRUARY,

    /**
     * The singleton instance for the month of March with 31 days.
     * This has the numeric value of `3`.
     */
    MARCH,

    /**
     * The singleton instance for the month of April with 30 days.
     * This has the numeric value of `4`.
     */
    APRIL,

    /**
     * The singleton instance for the month of May with 31 days.
     * This has the numeric value of `5`.
     */
    MAY,

    /**
     * The singleton instance for the month of June with 30 days.
     * This has the numeric value of `6`.
     */
    JUNE,

    /**
     * The singleton instance for the month of July with 31 days.
     * This has the numeric value of `7`.
     */
    JULY,

    /**
     * The singleton instance for the month of August with 31 days.
     * This has the numeric value of `8`.
     */
    AUGUST,

    /**
     * The singleton instance for the month of September with 30 days.
     * This has the numeric value of `9`.
     */
    SEPTEMBER,

    /**
     * The singleton instance for the month of October with 31 days.
     * This has the numeric value of `10`.
     */
    OCTOBER,

    /**
     * The singleton instance for the month of November with 30 days.
     * This has the numeric value of `11`.
     */
    NOVEMBER,

    /**
     * The singleton instance for the month of December with 31 days.
     * This has the numeric value of `12`.
     */
    DECEMBER;

    /**
     * Private cache of all the constants.
     */


    //-----------------------------------------------------------------------

    //-----------------------------------------------------------------------
    /**
     * Obtains an instance of `Month` from an `int` value.
     *
     *
     * `Month` is an enum representing the 12 months of the year.
     * This factory allows the enum to be obtained from the `int` value.
     * The `int` value follows the ISO-8601 standard, from 1 (January) to 12 (December).
     *
     * @param month the month-of-year to represent, from 1 (January) to 12 (December)
     * @return the month-of-year, not null
     * @throws DateTimeException if the month-of-year is invalid
     */
    companion object {
        private val enums = values()
        fun of(month: Int): MonthEnum {
            if (month < 1 || month > 12) {
                throw RuntimeException("Invalid value for MonthOfYear: $month")
            }
            return enums[month - 1]
        }
    }

    fun maxLength(): Int {
        return when (this) {
            FEBRUARY -> 29
            APRIL, JUNE, SEPTEMBER, NOVEMBER -> 30
            else -> 31
        }
    }

    fun minLength(): Int {
        return when (this) {
            FEBRUARY -> 28
            APRIL, JUNE, SEPTEMBER, NOVEMBER -> 30
            else -> 31
        }
    }

    fun length(leapYear: Boolean): Int {
        return when (this) {
            FEBRUARY -> if (leapYear) 29 else 28
            APRIL, JUNE, SEPTEMBER, NOVEMBER -> 30
            else -> 31
        }
    }
}