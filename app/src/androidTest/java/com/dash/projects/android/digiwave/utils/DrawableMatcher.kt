package com.dash.projects.android.digiwave.utils

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.annotation.VisibleForTesting
import com.google.android.material.textfield.TextInputLayout
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import org.jetbrains.annotations.TestOnly

@VisibleForTesting
class DrawableMatcher private constructor(@DrawableRes drawableId: Int) : TypeSafeMatcher<View>() {
    private val id: Int

    init {
        id = drawableId
    }

    override fun describeTo(description: Description?) {
        description?.appendText("View with drawable from resource id: $id")
    }

    override fun describeMismatchSafely(item: View?, mismatchDescription: Description?) {
        super.describeMismatchSafely(item, mismatchDescription)
        mismatchDescription?.appendText(
            when (item) {
                is TextInputLayout -> "TextInputLayout actual startIconDrawable id was ${item.startIconDrawable?.current}"
                is ImageView -> "Imageview actual drawable id was ${item.drawable}"
                null -> "No matches view!"
                else -> "View actual drawables id <[Left, Top, Right, Bottom]> was ${item.drawableState}"
            }
        )
    }

    override fun matchesSafely(item: View?): Boolean {
        item ?: return false

        return when (item) {
            is TextInputLayout -> {
                // for checking when there is no drawable inside TextInputLayout
                if (id < 0) return item.startIconDrawable == null

                val bitmap = getBitmap(item.startIconDrawable ?: return false)
                val otherBitmap = getBitmap(item.context.getDrawable(id) ?: return false)
                bitmap.sameAs(otherBitmap)
            }
            is ImageView -> {
                // for checking when there is no drawable inside ImageView
                if (id < 0) return item.drawable == null

                val actualBitmap = getBitmap(item.drawable ?: return false)
                val expectedBitmap = getBitmap(item.context.getDrawable(id) ?: return false)
                actualBitmap.sameAs(expectedBitmap)
            }
            else -> false
        }
    }

    private fun getBitmap(d: Drawable): Bitmap {
        val config = Bitmap.Config.ARGB_8888
        return Bitmap.createBitmap(d.intrinsicWidth, d.intrinsicHeight, config).let { bitmap ->
            val canvas = Canvas(bitmap)
            d.setBounds(0, 0, canvas.width, canvas.height)
            d.draw(canvas)

            bitmap
        }
    }

    companion object {
        @TestOnly
        fun withDrawable(@DrawableRes id: Int): Matcher<View> {
            return DrawableMatcher(id)
        }

        @TestOnly
        @Suppress("unused")
        fun isNoDrawable(): Matcher<View> {
            return DrawableMatcher(-1)
        }
    }
}
