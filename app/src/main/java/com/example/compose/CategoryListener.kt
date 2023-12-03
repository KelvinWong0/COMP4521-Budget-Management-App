package com.example.compose

import com.example.compose.data.models.Category
import com.google.android.material.card.MaterialCardView

interface CategoryListener {
    fun onCategoryClicked(catGrid: MaterialCardView)
}