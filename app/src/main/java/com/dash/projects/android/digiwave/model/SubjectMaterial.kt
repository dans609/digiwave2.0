package com.dash.projects.android.digiwave.model

import android.os.Parcelable
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import kotlinx.parcelize.Parcelize

@Parcelize
data class SubjectMaterial(
    @DrawableRes val subjectImage: Int,
    @StringRes val subjectTitle: Int,
    @StringRes val subjectDesc: Int,
    val folderUrlLink: String,
) : Parcelable
