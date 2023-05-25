package com.dash.projects.android.digiwave.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.StateListDrawable
import android.view.View
import android.widget.ImageView
import androidx.annotation.ArrayRes
import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.espresso.Espresso
import androidx.test.espresso.ViewAction
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers
import com.google.android.material.textfield.TextInputLayout
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.hamcrest.TypeSafeMatcher
import org.jetbrains.annotations.TestOnly

object TestUtils {
    private val context by lazy(LazyThreadSafetyMode.NONE) {
        getApplicationContext<Context>()
    }

    @TestOnly
    fun atPosition(position: Int, itemMatcher: Matcher<View?>): Matcher<View?> {
        return object : BoundedMatcher<View?, RecyclerView>(RecyclerView::class.java) {
            override fun describeTo(description: Description) {
                description.appendText(position.toString())
                itemMatcher.describeTo(description)
            }

            override fun matchesSafely(view: RecyclerView): Boolean {
                val viewHolder = view.findViewHolderForAdapterPosition(position)
                    ?: return false
                return itemMatcher.matches(viewHolder.itemView)
            }
        }
    }

    @TestOnly
    fun withError(message: String?): Matcher<View> = object : TypeSafeMatcher<View>() {
        override fun describeTo(description: Description?) {
            description?.appendText("Edit Text with error: $message")
        }

        override fun matchesSafely(item: View?) = when (item) {
            is TextInputLayout -> message == item.error
            else -> false
        }
    }

    @TestOnly
    fun <T> withError(@StringRes message: Int, formatArgs: Array<T>): Matcher<View> =
        object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description?) {
                description?.appendText("Edit Text error with string resource code: $message")
            }

            override fun matchesSafely(item: View?) = when (item) {
                is TextInputLayout -> item.resources.getString(message, *formatArgs) == item.error
                else -> false
            }
        }

    @TestOnly
    fun withTextInputHint(text: String?): Matcher<View> = object : TypeSafeMatcher<View>() {
        override fun describeTo(description: Description?) {
            description?.appendText("TextInputLayout hint with string: $text")
        }

        override fun matchesSafely(item: View?): Boolean {
            item ?: return false
            if (item !is TextInputLayout) return false

            return item.hint == text
        }
    }

    @TestOnly
    fun withStartIconDrawable(@DrawableRes id: Int): Matcher<View> =
        object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description?) {
                description
                    ?.appendText("TextInputLayout startIconDrawable with drawable from resource id: ")
                    ?.appendValue(id)

            }

            override fun matchesSafely(item: View?): Boolean {
                item ?: return false
                if (item !is TextInputLayout) return false

                val actualDrawable = item.startIconDrawable ?: return false
                val expectedDrawable = item.context.getDrawable(id) ?: return false

                val bitmap: Bitmap = getBitmap(actualDrawable)
                val otherBitmap: Bitmap = getBitmap(expectedDrawable)
                return bitmap.sameAs(otherBitmap)
            }

            private fun getBitmap(drawable: Drawable): Bitmap {
                return Bitmap.createBitmap(
                    drawable.intrinsicWidth,
                    drawable.intrinsicHeight,
                    Bitmap.Config.ARGB_8888
                ).let { bitmap ->
                    val canvas = Canvas(bitmap)
                    drawable.setBounds(0, 0, canvas.width, canvas.height)
                    drawable.draw(canvas)

                    bitmap
                }
            }
        }

    @TestOnly
    fun pressKey(times: Int, keyCode: Int): Array<ViewAction> {
        if (times <= 0) return arrayOf()

        return Array(times) {
            ViewActions.pressKey(keyCode)
        }
    }

    @TestOnly
    fun getStringArray(@ArrayRes id: Int): Array<String> =
        context.resources.getStringArray(id)

    @TestOnly
    fun onViewWithParent(@IdRes childId: Int, @IdRes parentId: Int): ViewInteraction {
        return Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(childId),
                ViewMatchers.withParent(ViewMatchers.withId(parentId))
            )
        )
    }

    @TestOnly
    fun withImageDrawable(@DrawableRes sameWith: Int): Matcher<View> =
        object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description?) {
                description?.appendText("has image drawable resource: $sameWith")
            }

            override fun matchesSafely(item: View?): Boolean {
                item ?: return false
                if (item !is ImageView) return false

                return sameBitmap(item.context, item.drawable, sameWith)
            }

            private fun sameBitmap(
                mContext: Context,
                drawable: Drawable,
                drawableRes: Int
            ): Boolean {
                val otherDrawable = mContext.getDrawable(drawableRes) ?: return false

                if (drawable is StateListDrawable && otherDrawable is StateListDrawable) {
                    return drawable.current == otherDrawable.current
                }

                if (drawable is BitmapDrawable && otherDrawable is BitmapDrawable) {
                    val bitmap = drawable.bitmap
                    val otherBitmap = otherDrawable.bitmap
                    return bitmap.sameAs(otherBitmap)
                }

                return false
            }
        }
}
