package com.example.nasa.utils

import android.animation.PropertyValuesHolder

object AnimatorUtils {

    const val ROTATION = "rotation"
    const val SCALE_X = "scaleX"
    const val SCALE_Y = "scaleY"
    const val TRANSLATION_X = "translationX"
    const val TRANSLATION_Y = "translationY"

    fun rotation(vararg values: Float): PropertyValuesHolder {
        return PropertyValuesHolder.ofFloat(ROTATION, *values)
    }

    fun translationX(vararg values: Float): PropertyValuesHolder {
        return PropertyValuesHolder.ofFloat(TRANSLATION_X, *values)
    }

    fun translationY(vararg values: Float): PropertyValuesHolder {
        return PropertyValuesHolder.ofFloat(TRANSLATION_Y, *values)
    }

    fun scaleX(vararg values: Float): PropertyValuesHolder {
        return PropertyValuesHolder.ofFloat(SCALE_X, *values)
    }

    fun scaleY(vararg values: Float): PropertyValuesHolder {
        return PropertyValuesHolder.ofFloat(SCALE_Y, *values)
    }
}