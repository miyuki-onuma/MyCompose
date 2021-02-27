package com.example.androiddevchallenge

import androidx.annotation.DrawableRes

data class Dog(
    val id: String,
    val name: String,
    val subtitle: String? = null,
    @DrawableRes
    val imageThumbId: Int
)
