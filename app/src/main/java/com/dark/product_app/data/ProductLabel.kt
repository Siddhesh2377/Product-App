package com.dark.product_app.data

import com.dark.product_app.R

enum class ProductLabel(
    val displayName: String,
    val icon: Int = R.drawable.material
) {
    MATERIAL("Material", R.drawable.material),
    GENDER("Gender", icon = R.drawable.gender),
    CONDITION("Condition", icon = R.drawable.condition),
    TYPE("Type", icon = R.drawable.type),
    PACKAGING("Packaging", icon = R.drawable.packaging),
    QUANTITY("quantity", icon = R.drawable.quantity),
    SIZE("size", icon = R.drawable.size),

    SUPER_ABSORBENT("Super Absorbent Core", icon = R.drawable.super_absorbent),
    BREATHABLE("Breathable Material", icon = R.drawable.breathable),
    SOFT_GENTLE("Soft & Gentle on Skin", icon = R.drawable.soft_gentle),
    LEAK_PROTECTION("Advanced Leak Protection", icon = R.drawable.leak_protection),
    EASY_PULL_UP("Easy Pull-Up Design", icon = R.drawable.pull_up),
    HYPOALLERGENIC("Hypoallergenic", icon = R.drawable.hypoallergenic);

    companion object {
        fun fromDisplayName(name: String): ProductLabel? {
            return ProductLabel.entries.find { it.displayName.equals(name, ignoreCase = true) }
        }
    }
}
