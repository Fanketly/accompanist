package com.fanketly.accompanist.picker

data class Date(var year:Int,var month:Int,var day:Int){
    override fun toString(): String {
        return "$year-$month-$day"
    }
}