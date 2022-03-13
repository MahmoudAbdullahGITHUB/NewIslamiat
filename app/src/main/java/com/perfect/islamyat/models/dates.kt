package com.perfect.islamyat.models

data class dates (
    var readable: String = "",
    var gregorian: Gregorian = Gregorian(),
    var hijri: Hijri = Hijri()
)

data class Gregorian (
    var date: String = "",
    var format: String = "",
    var day: String = "",
    var year: String = "",
    var weekday : WeekDay = WeekDay(),
    var month : monthData = monthData()
)

data class Hijri (
    var date: String = "",
    var format: String = "",
    var day: String = "",
    var year: String = "",
    var weekday : WeekDay = WeekDay(),
    var month : monthData = monthData()
)
data class WeekDay (
    var en: String = "",
    var ar: String = ""
)

data class monthData (
    var en: String = "",
    var ar: String = "",
    var number: String = ""
)