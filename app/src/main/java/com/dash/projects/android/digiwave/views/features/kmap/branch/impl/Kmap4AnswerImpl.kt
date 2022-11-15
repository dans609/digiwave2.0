package com.dash.projects.android.digiwave.views.features.kmap.branch.impl

import android.content.Context
import com.dash.projects.android.digiwave.R
import com.dash.projects.android.digiwave.`object`.utils.Utils.adds
import com.dash.projects.android.digiwave.`object`.utils.Utils.eachIsNotEmpty
import com.dash.projects.android.digiwave.`object`.utils.Utils.notContainedIn
import com.dash.projects.android.digiwave.`object`.utils.Utils.strIntRes
import com.dash.projects.android.digiwave.`object`.utils.Utils.stringRes

object Kmap4AnswerImpl {
    @Suppress("UNCHECKED_CAST")
    fun <T : CharSequence> answer(
        c: Context,
        state: T,
    ): (T, T, T, T, T, T, T, T, T, T, T, T, T, T, T, T) -> T =
        { r0, r1, r3, r2, r4, r5, r7, r6, r12, r13, r15, r14, r8, r9, r11, r10 ->
            when {
                state.notContainedIn(
                    r0, r1, r3, r2, r4, r5, r7, r6, r12, r13, r15, r14, r8, r9, r11, r10
                ) -> c.strIntRes(R.integer.low)
                state.eachIsNotEmpty(r10) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r12, r13, r15, r14, r8, r9, r11)
                } -> "A.!B.C.!D"
                state.eachIsNotEmpty(r11) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r12, r13, r15, r14, r8, r9, r10)
                } -> "A.!B.C.D"
                state.eachIsNotEmpty(r11, r10) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r12, r13, r15, r14, r8, r9)
                } -> "A.!B.C"
                state.eachIsNotEmpty(r9) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r12, r13, r15, r14, r8, r11, r10)
                } -> "A.!B.!C.D"
                state.eachIsNotEmpty(r9, r10) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r12, r13, r15, r14, r8, r11)
                } -> "(A.!B.!C.D) + (A.!B.C.!D)"
                state.eachIsNotEmpty(r9, r11) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r12, r13, r15, r14, r8, r10)
                } -> "A.!B.D"
                state.eachIsNotEmpty(r9, r11, r10) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r12, r13, r15, r14, r8)
                } -> "(A.!B.D) + (A.!B.C)"
                state.eachIsNotEmpty(r8) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r12, r13, r15, r14, r9, r11, r10)
                } -> "A.!B.!C.!D"
                state.eachIsNotEmpty(r8, r10) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r12, r13, r15, r14, r9, r11)
                } -> "A.!B.!D"
                state.eachIsNotEmpty(r8, r11) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r12, r13, r15, r14, r9, r10)
                } -> "(A.!B.!C.!D) + (A.!B.C.D)"
                state.eachIsNotEmpty(r8, r11, r10) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r12, r13, r15, r14, r9)
                } -> "(A.!B.!D) + (A.!B.C)"
                state.eachIsNotEmpty(r8, r9) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r12, r13, r15, r14, r11, r10)
                } -> "A.!B.!C"
                state.eachIsNotEmpty(r8, r9, r10) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r12, r13, r15, r14, r11)
                } -> "(A.!B.!C) + (A.!B.!D)"
                state.eachIsNotEmpty(r8, r9, r11) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r12, r13, r15, r14, r10)
                } -> "(A.!B.!C) + (A.!B.D)"
                state.eachIsNotEmpty(r8, r9, r11, r10) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r12, r13, r15, r14)
                } -> "A.!B"
                state.eachIsNotEmpty(r14) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r12, r13, r15, r8, r9, r11, r10)
                } -> "A.B.C.!D"
                state.eachIsNotEmpty(r14, r10) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r12, r13, r15, r8, r9, r11)
                } -> "A.C.!D"
                state.eachIsNotEmpty(r14, r11) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r12, r13, r15, r8, r9, r10)
                } -> "(A.!B.C.D) + (A.B.C.!D)"
                state.eachIsNotEmpty(r14, r11, r10) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r12, r13, r15, r8, r9)
                } -> "(A.!B.C) + (A.C.!D)"
                state.eachIsNotEmpty(r14, r9) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r12, r13, r15, r8, r11, r10)
                } -> "(A.!B.!C.D) + (A.B.C.!D)"
                state.eachIsNotEmpty(r14, r9, r10) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r12, r13, r15, r8, r11)
                } -> "(A.!B.!C.D) + (A.C.!D)"
                state.eachIsNotEmpty(r14, r9, r11) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r12, r13, r15, r8, r10)
                } -> "(A.B.C.!D) + (A.!B.D)"
                state.eachIsNotEmpty(r14, r9, r11, r10) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r12, r13, r15, r8)
                } -> "(A.!B.D) + (A.C.!D)"
                state.eachIsNotEmpty(r14, r8) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r12, r13, r15, r9, r11, r10)
                } -> "(A.!B.!C.!D) + (A.B.C.!D)"
                state.eachIsNotEmpty(r14, r8, r10) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r12, r13, r15, r9, r11)
                } -> "(A.!B.!D) + (A.C.!D)"
                state.eachIsNotEmpty(r14, r8, r11) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r12, r13, r15, r9, r10)
                } -> "(A.!B.!C.!D) + (A.!B.C.D) + (A.B.C.!D)"
                state.eachIsNotEmpty(r14, r8, r11, r10) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r12, r13, r15, r9)
                } -> "(A.!B.!D) + (A.!B.C) + (A.C.!D)"
                state.eachIsNotEmpty(r14, r8, r9) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r12, r13, r15, r11, r10)
                } -> "(A.B.C.!D) + (A.!B.!C)"
                state.eachIsNotEmpty(r14, r8, r9, r10) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r12, r13, r15, r11)
                } -> "(A.!B.!C) + (A.C.!D)"
                state.eachIsNotEmpty(r14, r8, r9, r11) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r12, r13, r15, r10)
                } -> "(A.B.C.!D) + (A.!B.!C) + (A.!B.D)"
                state.eachIsNotEmpty(r14, r8, r9, r11, r10) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r12, r13, r15)
                } -> "(A.C.!D) + (A.!B)"
                state.eachIsNotEmpty(r15) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r12, r13, r14, r8, r9, r11, r10)
                } -> "A.B.C.D"
                state.eachIsNotEmpty(r15, r10) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r12, r13, r14, r8, r9, r11)
                } -> "(A.!B.C.!D) + (A.B.C.D)"
                state.eachIsNotEmpty(r15, r11) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r12, r13, r14, r8, r9, r10)
                } -> "A.C.D"
                state.eachIsNotEmpty(r15, r11, r10) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r12, r13, r14, r8, r9)
                } -> "(A.!B.C) + (A.C.D)"
                state.eachIsNotEmpty(r15, r9) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r12, r13, r14, r8, r11, r10)
                } -> "(A.!B.!C.D) + (A.B.C.D)"
                state.eachIsNotEmpty(r15, r9, r10) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r12, r13, r14, r8, r11)
                } -> "(A.!B.!C.D) + (A.!B.C.!D) + (A.B.C.D)"
                state.eachIsNotEmpty(r15, r9, r11) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r12, r13, r14, r8, r10)
                } -> "(A.!B.D) + (A.C.D)"
                state.eachIsNotEmpty(r15, r9, r11, r10) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r12, r13, r14, r8)
                } -> "(A.!B.D) + (A.!B.C) + (A.C.D)"
                state.eachIsNotEmpty(r15, r8) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r12, r13, r14, r9, r11, r10)
                } -> "(A.!B.!C.!D) + (A.B.C.D)"
                state.eachIsNotEmpty(r15, r8, r10) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r12, r13, r14, r9, r11)
                } -> "(A.B.C.D) + (A.!B.!D)"
                state.eachIsNotEmpty(r15, r8, r11) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r12, r13, r14, r9, r10)
                } -> "(A.!B.!C.!D) + (A.C.D)"
                state.eachIsNotEmpty(r15, r8, r11, r10) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r12, r13, r14, r9)
                } -> "(A.!B.!D) + (A.C.D)"
                state.eachIsNotEmpty(r15, r8, r9) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r12, r13, r14, r11, r10)
                } -> "(A.B.C.D) + (A.!B.!C)"
                state.eachIsNotEmpty(r15, r8, r9, r10) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r12, r13, r14, r11)
                } -> "(A.B.C.D) + (A.!B.!C) + (A.!B.!D)"
                state.eachIsNotEmpty(r15, r8, r9, r11) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r12, r13, r14, r10)
                } -> "(A.!B.!C) + (A.C.D)"
                state.eachIsNotEmpty(r15, r8, r9, r11, r10) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r12, r13, r14)
                } -> "(A.C.D) + (A.!B)"
                state.eachIsNotEmpty(r15, r14) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r12, r13, r8, r9, r11, r10)
                } -> "A.B.C"
                state.eachIsNotEmpty(r15, r14, r10) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r12, r13, r8, r9, r11)
                } -> "(A.C.!D) + (A.B.C)"
                state.eachIsNotEmpty(r15, r14, r11) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r12, r13, r8, r9, r10)
                } -> "(A.C.D) + (A.B.C)"
                state.eachIsNotEmpty(r15, r14, r11, r10) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r12, r13, r8, r9)
                } -> "A.C"
                state.eachIsNotEmpty(r15, r14, r9) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r12, r13, r8, r11, r10)
                } -> "(A.!B.!C.D) + (A.B.C)"
                state.eachIsNotEmpty(r15, r14, r9, r10) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r12, r13, r8, r11)
                } -> "(A.!B.!C.D) + (A.C.!D) + (A.B.C)"
                state.eachIsNotEmpty(r15, r14, r9, r11) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r12, r13, r8, r10)
                } -> "(A.!B.D) + (A.B.C)"
                state.eachIsNotEmpty(r15, r14, r9, r11, r10) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r12, r13, r8)
                } -> "(A.!B.D) + (A.C)"
                state.eachIsNotEmpty(r15, r14, r8) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r12, r13, r9, r11, r10)
                } -> "(A.!B.!C.!D) + (A.B.C)"
                state.eachIsNotEmpty(r15, r14, r8, r10) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r12, r13, r9, r11)
                } -> "(A.!B.!D) + (A.B.C)"
                state.eachIsNotEmpty(r15, r14, r8, r11) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r12, r13, r9, r10)
                } -> "(A.!B.!C.!D) + (A.C.D) + (A.B.C)"
                state.eachIsNotEmpty(r15, r14, r8, r11, r10) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r12, r13, r9)
                } -> "(A.!B.!D) + (A.C)"
                state.eachIsNotEmpty(r15, r14, r8, r9) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r12, r13, r11, r10)
                } -> "(A.!B.!C) + (A.B.C)"
                state.eachIsNotEmpty(r15, r14, r8, r9, r10) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r12, r13, r11)
                } -> "(A.!B.!C) + (A.C.!D) + (A.B.C)"
                state.eachIsNotEmpty(r15, r14, r8, r9, r11) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r12, r13, r10)
                } -> "(A.!B.!C) + (A.C.D) + (A.B.C)"
                state.eachIsNotEmpty(r15, r14, r8, r9, r11, r10) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r12, r13)
                } -> "(A.!B) + (A.C)"
                state.eachIsNotEmpty(r13) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r12, r15, r14, r8, r9, r11, r10)
                } -> "A.B.!C.D"
                state.eachIsNotEmpty(r13, r10) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r12, r15, r14, r8, r9, r11)
                } -> "(A.!B.C.!D) + (A.B.!C.D)"
                state.eachIsNotEmpty(r13, r11) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r12, r15, r14, r8, r9, r10)
                } -> "(A.!B.C.D) + (A.B.!C.D)"
                state.eachIsNotEmpty(r13, r11, r10) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r12, r15, r14, r8, r9)
                } -> "(A.B.!C.D) + (A.!B.C)"
                state.eachIsNotEmpty(r13, r9) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r12, r15, r14, r8, r11, r10)
                } -> "A.!C.D"
                state.eachIsNotEmpty(r13, r9, r10) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r12, r15, r14, r8, r11)
                } -> "(A.!B.C.!D) + (A.!C.D)"
                state.eachIsNotEmpty(r13, r9, r11) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r12, r15, r14, r8, r10)
                } -> "(A.!B.D) + (A.!C.D)"
                state.eachIsNotEmpty(r13, r9, r11, r10) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r12, r15, r14, r8)
                } -> "(A.!C.D) + (A.!B.C)"
                state.eachIsNotEmpty(r13, r8) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r12, r15, r14, r9, r11, r10)
                } -> "(A.!B.!C.!D) + (A.B.!C.D)"
                state.eachIsNotEmpty(r13, r8, r10) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r12, r15, r14, r9, r11)
                } -> "(A.B.!C.D) + (A.!B.!D)"
                state.eachIsNotEmpty(r13, r8, r11) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r12, r15, r14, r9, r10)
                } -> "(A.!B.!C.!D) + (A.!B.C.D) + (A.B.!C.D)"
                state.eachIsNotEmpty(r13, r8, r11, r10) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r12, r15, r14, r9)
                } -> "(A.B.!C.D) + (A.!B.!D) + (A.!B.C)"
                state.eachIsNotEmpty(r13, r8, r9) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r12, r15, r14, r11, r10)
                } -> "(A.!B.!C) + (A.!C.D)"
                state.eachIsNotEmpty(r13, r8, r9, r10) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r12, r15, r14, r11)
                } -> "(A.!B.!D) + (A.!C.D)"
                state.eachIsNotEmpty(r13, r8, r9, r11) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r12, r15, r14, r10)
                } -> "(A.!B.!C) + (A.!B.D) + (A.!C.D)"
                state.eachIsNotEmpty(r13, r8, r9, r11, r10) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r12, r15, r14)
                } -> "(A.!C.D) + (A.!B)"
                state.eachIsNotEmpty(r13, r14) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r12, r15, r8, r9, r11, r10)
                } -> "(A.B.!C.D) + (A.B.C.!D)"
                state.eachIsNotEmpty(r13, r14, r10) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r12, r15, r8, r9, r11)
                } -> "(A.B.!C.D) + (A.C.!D)"
                state.eachIsNotEmpty(r13, r14, r11) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r12, r15, r8, r9, r10)
                } -> "(A.!B.C.D) + (A.B.!C.D) + (A.B.C.!D)"
                state.eachIsNotEmpty(r13, r14, r11, r10) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r12, r15, r8, r9)
                } -> "(A.B.!C.D) + (A.!B.C) + (A.C.!D)"
                state.eachIsNotEmpty(r13, r14, r9) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r12, r15, r8, r11, r10)
                } -> "(A.B.C.!D) + (A.!C.D)"
                state.eachIsNotEmpty(r13, r14, r9, r10) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r12, r15, r8, r11)
                } -> "(A.!C.D) + (A.C.!D)"
                state.eachIsNotEmpty(r13, r14, r9, r11) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r12, r15, r8, r10)
                } -> "(A.B.C.!D) + (A.!B.D) + (A.!C.D)"
                state.eachIsNotEmpty(r13, r14, r9, r11, r10) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r12, r15, r8)
                } -> "(A.!C.D) + (A.!B.C) + (A.C.!D)"
                state.eachIsNotEmpty(r13, r14, r8) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r12, r15, r9, r11, r10)
                } -> "(A.!B.!C.!D) + (A.B.!C.D) + (A.B.C.!D)"
                state.eachIsNotEmpty(r13, r14, r8, r10) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r12, r15, r9, r11)
                } -> "(A.B.!C.D) + (A.!B.!D) + (A.C.!D)"
                state.eachIsNotEmpty(r13, r14, r8, r11) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r12, r15, r9, r10)
                } -> "(A.!B.!C.!D) + (A.!B.C.D) + (A.B.!C.D) + (A.B.C.!D)"
                state.eachIsNotEmpty(r13, r14, r8, r11, r10) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r12, r15, r9)
                } -> "(A.B.!C.D) + (A.!B.!D) + (A.!B.C) + (A.C.!D)"
                state.eachIsNotEmpty(r13, r14, r8, r9) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r12, r15, r11, r10)
                } -> "(A.B.C.!D) + (A.!B.!C) + (A.!C.D)"
                state.eachIsNotEmpty(r13, r14, r8, r9, r10) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r12, r15, r11)
                } -> "(A.!B.!D) + (A.!C.D) + (A.C.!D)"
                state.eachIsNotEmpty(r13, r14, r8, r9, r11) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r12, r15, r10)
                } -> "(A.B.C.!D) + (A.!B.!C) + (A.!B.D) + (A.!C.D)"
                state.eachIsNotEmpty(r13, r14, r8, r9, r11, r10) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r12, r15)
                } -> "(A.!C.D) + (A.C.!D) + (A.!B)"
                state.eachIsNotEmpty(r13, r15) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r12, r14, r8, r9, r11, r10)
                } -> "A.B.D"
                state.eachIsNotEmpty(r13, r15, r10) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r12, r14, r8, r9, r11)
                } -> "(A.!B.C.!D) + (A.B.D)"
                state.eachIsNotEmpty(r13, r15, r11) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r12, r14, r8, r9, r10)
                } -> "(A.C.D) + (A.B.D)"
                state.eachIsNotEmpty(r13, r15, r11, r10) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r12, r14, r8, r9)
                } -> "(A.!B.C) + (A.B.D)"
                state.eachIsNotEmpty(r13, r15, r9) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r12, r14, r8, r11, r10)
                } -> "(A.!C.D) + (A.B.D)"
                state.eachIsNotEmpty(r13, r15, r9, r10) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r12, r14, r8, r11)
                } -> "(A.!B.C.!D) + (A.!C.D) + (A.B.D)"
                state.eachIsNotEmpty(r13, r15, r9, r11) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r12, r14, r8, r10)
                } -> "A.D"
                state.eachIsNotEmpty(r13, r15, r9, r11, r10) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r12, r14, r8)
                } -> "(A.!B.C) + (A.D)"
                state.eachIsNotEmpty(r13, r15, r8) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r12, r14, r9, r11, r10)
                } -> "(A.!B.!C.!D) + (A.B.D)"
                state.eachIsNotEmpty(r13, r15, r8, r10) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r12, r14, r9, r11)
                } -> "(A.!B.!D) + (A.B.D)"
                state.eachIsNotEmpty(r13, r15, r8, r11) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r12, r14, r9, r10)
                } -> "(A.!B.!C.!D) + (A.C.D) + (A.B.D)"
                state.eachIsNotEmpty(r13, r15, r8, r11, r10) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r12, r14, r9)
                } -> "(A.!B.!D) + (A.C.D) + (A.B.D)"
                state.eachIsNotEmpty(r13, r15, r8, r9) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r12, r14, r11, r10)
                } -> "(A.!B.!C) + (A.B.D)"
                state.eachIsNotEmpty(r13, r15, r8, r9, r10) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r12, r14, r11)
                } -> "(A.!B.!D) + (A.!C.D) + (A.B.D)"
                state.eachIsNotEmpty(r13, r15, r8, r9, r11) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r12, r14, r10)
                } -> "(A.!B.!C) + (A.D)"
                state.eachIsNotEmpty(r13, r15, r8, r9, r11, r10) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r12, r14)
                } -> "(A.!B) + (A.D)"
                state.eachIsNotEmpty(r13, r15, r14) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r12, r8, r9, r11, r10)
                } -> "(A.B.D) + (A.B.C)"
                state.eachIsNotEmpty(r13, r15, r14, r10) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r12, r8, r9, r11)
                } -> "(A.C.!D) + (A.B.D)"
                state.eachIsNotEmpty(r13, r15, r14, r11) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r12, r8, r9, r10)
                } -> "(A.C.D) + (A.B.D) + (A.B.C)"
                state.eachIsNotEmpty(r13, r15, r14, r11, r10) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r12, r8, r9)
                } -> "(A.B.D) + (A.C)"
                state.eachIsNotEmpty(r13, r15, r14, r9) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r12, r8, r11, r10)
                } -> "(A.!C.D) + (A.B.C)"
                state.eachIsNotEmpty(r13, r15, r14, r9, r10) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r12, r8, r11)
                } -> "(A.!C.D) + (A.C.!D) + (A.B.C)"
                state.eachIsNotEmpty(r13, r15, r14, r9, r11) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r12, r8, r10)
                } -> "(A.B.C) + (A.D)"
                state.eachIsNotEmpty(r13, r15, r14, r9, r11, r10) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r12, r8)
                } -> "(A.D) + (A.C)"
                state.eachIsNotEmpty(r13, r15, r14, r8) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r12, r9, r11, r10)
                } -> "(A.!B.!C.!D) + (A.B.D) + (A.B.C)"
                state.eachIsNotEmpty(r13, r15, r14, r8, r10) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r12, r9, r11)
                } -> "(A.!B.!D) + (A.B.D) + (A.B.C)"
                state.eachIsNotEmpty(r13, r15, r14, r8, r11) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r12, r9, r10)
                } -> "(A.!B.!C.!D) + (A.C.D) + (A.B.D) + (A.B.C)"
                state.eachIsNotEmpty(r13, r15, r14, r8, r11, r10) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r12, r9)
                } -> "(A.!B.!D) + (A.B.D) + (A.C)"
                state.eachIsNotEmpty(r13, r15, r14, r8, r9) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r12, r11, r10)
                } -> "(A.!B.!C) + (A.B.D) + (A.B.C)"
                state.eachIsNotEmpty(r13, r15, r14, r8, r9, r10) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r12, r11)
                } -> "(A.!B.!D) + (A.!C.D) + (A.B.C)"
                state.eachIsNotEmpty(r13, r15, r14, r8, r9, r11) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r12, r10)
                } -> "(A.!B.!C) + (A.B.C) + (A.D)"
                state.eachIsNotEmpty(r13, r15, r14, r8, r9, r11, r10) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r12)
                } -> "(A.!B) + (A.D) + (A.C)"
                state.eachIsNotEmpty(r12) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r13, r15, r14, r8, r9, r11, r10)
                } -> "A.B.!C.!D"
                state.eachIsNotEmpty(r12, r10) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r13, r15, r14, r8, r9, r11)
                } -> "(A.!B.C.!D) + (A.B.!C.!D)"
                state.eachIsNotEmpty(r12, r11) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r13, r15, r14, r8, r9, r10)
                } -> "(A.B.!C.!D) + (A.!B.C.D)"
                state.eachIsNotEmpty(r12, r11, r10) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r13, r15, r14, r8, r9)
                } -> "(A.B.!C.!D) + (A.!B.C)"
                state.eachIsNotEmpty(r12, r9) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r13, r15, r14, r8, r11, r10)
                } -> "(A.!B.!C.D) + (A.B.!C.!D)"
                state.eachIsNotEmpty(r12, r9, r10) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r13, r15, r14, r8, r11)
                } -> "(A.!B.!C.D) + (A.!B.C.!D) + (A.B.!C.!D)"
                state.eachIsNotEmpty(r12, r9, r11) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r13, r15, r14, r8, r10)
                } -> "(A.B.!C.!D) + (A.!B.D)"
                state.eachIsNotEmpty(r12, r9, r11, r10) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r13, r15, r14, r8)
                } -> "(A.B.!C.!D) + (A.!B.D) + (A.!B.C)"
                state.eachIsNotEmpty(r12, r8) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r13, r15, r14, r9, r11, r10)
                } -> "A.!C.!D"
                state.eachIsNotEmpty(r12, r8, r10) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r13, r15, r14, r9, r11)
                } -> "(A.!B.!D) + (A.!C.!D)"
                state.eachIsNotEmpty(r12, r8, r11) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r13, r15, r14, r9, r10)
                } -> "(A.!B.C.D) + (A.!C.!D)"
                state.eachIsNotEmpty(r12, r8, r11, r10) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r13, r15, r14, r9)
                } -> "(A.!C.!D) + (A.!B.C)"
                state.eachIsNotEmpty(r12, r8, r9) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r13, r15, r14, r11, r10)
                } -> "(A.!B.!C) + (A.!C.!D)"
                state.eachIsNotEmpty(r12, r8, r9, r10) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r13, r15, r14, r11)
                } -> "(A.!B.!C) + (A.!B.!D) + (A.!C.!D)"
                state.eachIsNotEmpty(r12, r8, r9, r11) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r13, r15, r14, r10)
                } -> "(A.!C.!D) + (A.!B.D)"
                state.eachIsNotEmpty(r12, r8, r9, r11, r10) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r13, r15, r14)
                } -> "(A.!C.!D) + (A.!B)"
                state.eachIsNotEmpty(r12, r14) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r13, r15, r8, r9, r11, r10)
                } -> "A.B.!D"
                state.eachIsNotEmpty(r12, r14, r10) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r13, r15, r8, r9, r11)
                } -> "(A.C.!D) + (A.B.!D)"
                state.eachIsNotEmpty(r12, r14, r11) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r13, r15, r8, r9, r10)
                } -> "(A.!B.C.D) + (A.B.!D)"
                state.eachIsNotEmpty(r12, r14, r11, r10) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r13, r15, r8, r9)
                } -> "(A.!B.C) + (A.B.!D)"
                state.eachIsNotEmpty(r12, r14, r9) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r13, r15, r8, r11, r10)
                } -> "(A.!B.!C.D) + (A.B.!D)"
                state.eachIsNotEmpty(r12, r14, r9, r10) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r13, r15, r8, r11)
                } -> "(A.!B.!C.D) + (A.C.!D) + (A.B.!D)"
                state.eachIsNotEmpty(r12, r14, r9, r11) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r13, r15, r8, r10)
                } -> "(A.!B.D) + (A.B.!D)"
                state.eachIsNotEmpty(r12, r14, r9, r11, r10) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r13, r15, r8)
                } -> "(A.!B.D) + (A.C.!D) + (A.B.!D)"
                state.eachIsNotEmpty(r12, r14, r8) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r13, r15, r9, r11, r10)
                } -> "(A.!C.!D) + (A.B.!D)"
                state.eachIsNotEmpty(r12, r14, r8, r10) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r13, r15, r9, r11)
                } -> "A.!D"
                state.eachIsNotEmpty(r12, r14, r8, r11) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r13, r15, r9, r10)
                } -> "(A.!B.C.D) + (A.!C.!D) + (A.B.!D)"
                state.eachIsNotEmpty(r12, r14, r8, r11, r10) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r13, r15, r9)
                } -> "(A.!B.C) + (A.!D)"
                state.eachIsNotEmpty(r12, r14, r8, r9) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r13, r15, r11, r10)
                } -> "(A.!B.!C) + (A.B.!D)"
                state.eachIsNotEmpty(r12, r14, r8, r9, r10) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r13, r15, r11)
                } -> "(A.!B.!C) + (A.!D)"
                state.eachIsNotEmpty(r12, r14, r8, r9, r11) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r13, r15, r10)
                } -> "(A.!C.!D) + (A.!B.D) + (A.B.!D)"
                state.eachIsNotEmpty(r12, r14, r8, r9, r11, r10) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r13, r15)
                } -> "(A.!B) + (A.!D)"
                state.eachIsNotEmpty(r12, r15) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r13, r14, r8, r9, r11, r10)
                } -> "(A.B.!C.!D) + (A.B.C.D)"
                state.eachIsNotEmpty(r12, r15, r10) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r13, r14, r8, r9, r11)
                } -> "(A.!B.C.!D) + (A.B.!C.!D) + (A.B.C.D)"
                state.eachIsNotEmpty(r12, r15, r11) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r13, r14, r8, r9, r10)
                } -> "(A.B.!C.!D) + (A.C.D)"
                state.eachIsNotEmpty(r12, r15, r11, r10) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r13, r14, r8, r9)
                } -> "(A.B.!C.!D) + (A.!B.C) + (A.C.D)"
                state.eachIsNotEmpty(r12, r15, r9) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r13, r14, r8, r11, r10)
                } -> "(A.!B.!C.D) + (A.B.!C.!D) + (A.B.C.D)"
                state.eachIsNotEmpty(r12, r15, r9, r10) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r13, r14, r8, r11)
                } -> "(A.!B.!C.D) + (A.!B.C.!D) + (A.B.!C.!D) + (A.B.C.D)"
                state.eachIsNotEmpty(r12, r15, r9, r11) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r13, r14, r8, r10)
                } -> "(A.B.!C.!D) + (A.!B.D) + (A.C.D)"
                state.eachIsNotEmpty(r12, r15, r9, r11, r10) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r13, r14, r8)
                } -> "(A.B.!C.!D) + (A.!B.D) + (A.!B.C) + (A.C.D)"
                state.eachIsNotEmpty(r12, r15, r8) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r13, r14, r9, r11, r10)
                } -> "(A.B.C.D) + (A.!C.!D)"
                state.eachIsNotEmpty(r12, r15, r8, r10) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r13, r14, r9, r11)
                } -> "(A.B.C.D) + (A.!B.!D) + (A.!C.!D)"
                state.eachIsNotEmpty(r12, r15, r8, r11) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r13, r14, r9, r10)
                } -> "(A.!C.!D) + (A.C.D)"
                state.eachIsNotEmpty(r12, r15, r8, r11, r10) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r13, r14, r9)
                } -> "(A.!C.!D) + (A.!B.C) + (A.C.D)"
                state.eachIsNotEmpty(r12, r15, r8, r9) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r13, r14, r11, r10)
                } -> "(A.B.C.D) + (A.!B.!C) + (A.!C.!D)"
                state.eachIsNotEmpty(r12, r15, r8, r9, r10) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r13, r14, r11)
                } -> "(A.B.C.D) + (A.!B.!C) + (A.!B.!D) + (A.!C.!D)"
                state.eachIsNotEmpty(r12, r15, r8, r9, r11) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r13, r14, r10)
                } -> "(A.!C.!D) + (A.!B.D) + (A.C.D)"
                state.eachIsNotEmpty(r12, r15, r8, r9, r11, r10) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r13, r14)
                } -> "(A.!C.!D) + (A.C.D) + (A.!B)"
                state.eachIsNotEmpty(r12, r15, r14) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r13, r8, r9, r11, r10)
                } -> "(A.B.!D) + (A.B.C)"
                state.eachIsNotEmpty(r12, r15, r14, r10) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r13, r8, r9, r11)
                } -> "(A.C.!D) + (A.B.!D) + (A.B.C)"
                state.eachIsNotEmpty(r12, r15, r14, r11) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r13, r8, r9, r10)
                } -> "(A.B.!D) + (A.C.D)"
                state.eachIsNotEmpty(r12, r15, r14, r11, r10) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r13, r8, r9)
                } -> "(A.B.!D) + (A.C)"
                state.eachIsNotEmpty(r12, r15, r14, r9) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r13, r8, r11, r10)
                } -> "(A.!B.!C.D) + (A.B.!D) + (A.B.C)"
                state.eachIsNotEmpty(r12, r15, r14, r9, r10) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r13, r8, r11)
                } -> "(A.!B.!C.D) + (A.C.!D) + (A.B.!D) + (A.B.C)"
                state.eachIsNotEmpty(r12, r15, r14, r9, r11) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r13, r8, r10)
                } -> "(A.!B.D) + (A.B.!D) + (A.B.C)"
                state.eachIsNotEmpty(r12, r15, r14, r9, r11, r10) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r13, r8)
                } -> "(A.!B.D) + (A.B.!D) + (A.C)"
                state.eachIsNotEmpty(r12, r15, r14, r8) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r13, r9, r11, r10)
                } -> "(A.!C.!D) + (A.B.C)"
                state.eachIsNotEmpty(r12, r15, r14, r8, r10) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r13, r9, r11)
                } -> "(A.B.C) + (A.!D)"
                state.eachIsNotEmpty(r12, r15, r14, r8, r11) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r13, r9, r10)
                } -> "(A.!C.!D) + (A.C.D) + (A.B.C)"
                state.eachIsNotEmpty(r12, r15, r14, r8, r11, r10) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r13, r9)
                } -> "(A.!D) + (A.C)"
                state.eachIsNotEmpty(r12, r15, r14, r8, r9) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r13, r11, r10)
                } -> "(A.!B.!C) + (A.B.!D) + (A.B.C)"
                state.eachIsNotEmpty(r12, r15, r14, r8, r9, r10) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r13, r11)
                } -> "(A.!B.!C) + (A.B.C) + (A.!D)"
                state.eachIsNotEmpty(r12, r15, r14, r8, r9, r11) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r13, r10)
                } -> "(A.!C.!D) + (A.!B.D) + (A.B.C)"
                state.eachIsNotEmpty(r12, r15, r14, r8, r9, r11, r10) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r13)
                } -> "(A.!B) + (A.!D) + (A.C)"
                state.eachIsNotEmpty(r12, r13) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r15, r14, r8, r9, r11, r10)
                } -> "A.B.!C"
                state.eachIsNotEmpty(r12, r13, r10) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r15, r14, r8, r9, r11)
                } -> "(A.!B.C.!D) + (A.B.!C)"
                state.eachIsNotEmpty(r12, r13, r11) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r15, r14, r8, r9, r10)
                } -> "(A.!B.C.D) + (A.B.!C)"
                state.eachIsNotEmpty(r12, r13, r11, r10) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r15, r14, r8, r9)
                } -> "(A.!B.C) + (A.B.!C)"
                state.eachIsNotEmpty(r12, r13, r9) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r15, r14, r8, r11, r10)
                } -> "(A.!C.D) + (A.B.!C)"
                state.eachIsNotEmpty(r12, r13, r9, r10) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r15, r14, r8, r11)
                } -> "(A.!B.C.!D) + (A.!C.D) + (A.B.!C)"
                state.eachIsNotEmpty(r12, r13, r9, r11) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r15, r14, r8, r10)
                } -> "(A.!B.D) + (A.B.!C)"
                state.eachIsNotEmpty(r12, r13, r9, r11, r10) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r15, r14, r8)
                } -> "(A.!C.D) + (A.!B.C) + (A.B.!C)"
                state.eachIsNotEmpty(r12, r13, r8) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r15, r14, r9, r11, r10)
                } -> "(A.!C.!D) + (A.B.!C)"
                state.eachIsNotEmpty(r12, r13, r8, r10) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r15, r14, r9, r11)
                } -> "(A.!B.!D) + (A.B.!C)"
                state.eachIsNotEmpty(r12, r13, r8, r11) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r15, r14, r9, r10)
                } -> "(A.!B.C.D) + (A.!C.!D) + (A.B.!C)"
                state.eachIsNotEmpty(r12, r13, r8, r11, r10) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r15, r14, r9)
                } -> "(A.!C.!D) + (A.!B.C) + (A.B.!C)"
                state.eachIsNotEmpty(r12, r13, r8, r9) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r15, r14, r11, r10)
                } -> "A.!C"
                state.eachIsNotEmpty(r12, r13, r8, r9, r10) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r15, r14, r11)
                } -> "(A.!B.!D) + (A.!C)"
                state.eachIsNotEmpty(r12, r13, r8, r9, r11) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r15, r14, r10)
                } -> "(A.!B.D) + (A.!C)"
                state.eachIsNotEmpty(r12, r13, r8, r9, r11, r10) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r15, r14)
                } -> "(A.!B) + (A.!C)"
                state.eachIsNotEmpty(r12, r13, r14) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r15, r8, r9, r11, r10)
                } -> "(A.B.!C) + (A.B.!D)"
                state.eachIsNotEmpty(r12, r13, r14, r10) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r15, r8, r9, r11)
                } -> "(A.C.!D) + (A.B.!C)"
                state.eachIsNotEmpty(r12, r13, r14, r11) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r15, r8, r9, r10)
                } -> "(A.!B.C.D) + (A.B.!C) + (A.B.!D)"
                state.eachIsNotEmpty(r12, r13, r14, r11, r10) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r15, r8, r9)
                } -> "(A.!B.C) + (A.B.!C) + (A.B.!D)"
                state.eachIsNotEmpty(r12, r13, r14, r9) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r15, r8, r11, r10)
                } -> "(A.!C.D) + (A.B.!D)"
                state.eachIsNotEmpty(r12, r13, r14, r9, r10) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r15, r8, r11)
                } -> "(A.!C.D) + (A.C.!D) + (A.B.!D)"
                state.eachIsNotEmpty(r12, r13, r14, r9, r11) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r15, r8, r10)
                } -> "(A.!B.D) + (A.B.!C) + (A.B.!D)"
                state.eachIsNotEmpty(r12, r13, r14, r9, r11, r10) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r15, r8)
                } -> "(A.!C.D) + (A.!B.C) + (A.B.!D)"
                state.eachIsNotEmpty(r12, r13, r14, r8) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r15, r9, r11, r10)
                } -> "(A.!C.!D) + (A.B.!C) + (A.B.!D)"
                state.eachIsNotEmpty(r12, r13, r14, r8, r10) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r15, r9, r11)
                } -> "(A.B.!C) + (A.!D)"
                state.eachIsNotEmpty(r12, r13, r14, r8, r11) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r15, r9, r10)
                } -> "(A.!B.C.D) + (A.!C.!D) + (A.B.!C) + (A.B.!D)"
                state.eachIsNotEmpty(r12, r13, r14, r8, r11, r10) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r15, r9)
                } -> "(A.!B.C) + (A.B.!C) + (A.!D)"
                state.eachIsNotEmpty(r12, r13, r14, r8, r9) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r15, r11, r10)
                } -> "(A.B.!D) + (A.!C)"
                state.eachIsNotEmpty(r12, r13, r14, r8, r9, r10) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r15, r11)
                } -> "(A.!C) + (A.!D)"
                state.eachIsNotEmpty(r12, r13, r14, r8, r9, r11) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r15, r10)
                } -> "(A.!B.D) + (A.B.!D) + (A.!C)"
                state.eachIsNotEmpty(r12, r13, r14, r8, r9, r11, r10) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r15)
                } -> "(A.!B) + (A.!C) + (A.!D)"
                state.eachIsNotEmpty(r12, r13, r15) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r14, r8, r9, r11, r10)
                } -> "(A.B.!C) + (A.B.D)"
                state.eachIsNotEmpty(r12, r13, r15, r10) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r14, r8, r9, r11)
                } -> "(A.!B.C.!D) + (A.B.!C) + (A.B.D)"
                state.eachIsNotEmpty(r12, r13, r15, r11) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r14, r8, r9, r10)
                } -> "(A.B.!C) + (A.C.D)"
                state.eachIsNotEmpty(r12, r13, r15, r11, r10) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r14, r8, r9)
                } -> "(A.!B.C) + (A.B.!C) + (A.B.D)"
                state.eachIsNotEmpty(r12, r13, r15, r9) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r14, r8, r11, r10)
                } -> "(A.!C.D) + (A.B.!C) + (A.B.D)"
                state.eachIsNotEmpty(r12, r13, r15, r9, r10) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r14, r8, r11)
                } -> "(A.!B.C.!D) + (A.!C.D) + (A.B.!C) + (A.B.D)"
                state.eachIsNotEmpty(r12, r13, r15, r9, r11) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r14, r8, r10)
                } -> "(A.B.!C) + (A.D)"
                state.eachIsNotEmpty(r12, r13, r15, r9, r11, r10) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r14, r8)
                } -> "(A.!B.C) + (A.B.!C) + (A.D)"
                state.eachIsNotEmpty(r12, r13, r15, r8) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r14, r9, r11, r10)
                } -> "(A.!C.!D) + (A.B.D)"
                state.eachIsNotEmpty(r12, r13, r15, r8, r10) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r14, r9, r11)
                } -> "(A.!B.!D) + (A.B.!C) + (A.B.D)"
                state.eachIsNotEmpty(r12, r13, r15, r8, r11) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r14, r9, r10)
                } -> "(A.!C.!D) + (A.C.D) + (A.B.D)"
                state.eachIsNotEmpty(r12, r13, r15, r8, r11, r10) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r14, r9)
                } -> "(A.!C.!D) + (A.!B.C) + (A.B.D)"
                state.eachIsNotEmpty(r12, r13, r15, r8, r9) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r14, r11, r10)
                } -> "(A.B.D) + (A.!C)"
                state.eachIsNotEmpty(r12, r13, r15, r8, r9, r10) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r14, r11)
                } -> "(A.!B.!D) + (A.B.D) + (A.!C)"
                state.eachIsNotEmpty(r12, r13, r15, r8, r9, r11) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r14, r10)
                } -> "(A.!C) + (A.D)"
                state.eachIsNotEmpty(r12, r13, r15, r8, r9, r11, r10) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r14)
                } -> "(A.!B) + (A.!C) + (A.D)"
                state.eachIsNotEmpty(r12, r13, r15, r14) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r8, r9, r11, r10)
                } -> "A.B"
                state.eachIsNotEmpty(r12, r13, r15, r14, r10) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r8, r9, r11)
                } -> "(A.C.!D) + (A.B)"
                state.eachIsNotEmpty(r12, r13, r15, r14, r11) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r8, r9, r10)
                } -> "(A.C.D) + (A.B)"
                state.eachIsNotEmpty(r12, r13, r15, r14, r11, r10) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r8, r9)
                } -> "(A.C) + (A.B)"
                state.eachIsNotEmpty(r12, r13, r15, r14, r9) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r8, r11, r10)
                } -> "(A.!C.D) + (A.B)"
                state.eachIsNotEmpty(r12, r13, r15, r14, r9, r10) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r8, r11)
                } -> "(A.!C.D) + (A.C.!D) + (A.B)"
                state.eachIsNotEmpty(r12, r13, r15, r14, r9, r11) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r8, r10)
                } -> "(A.D) + (A.B)"
                state.eachIsNotEmpty(r12, r13, r15, r14, r9, r11, r10) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r8)
                } -> "(A.D) + (A.C) + (A.B)"
                state.eachIsNotEmpty(r12, r13, r15, r14, r8) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r9, r11, r10)
                } -> "(A.!C.!D) + (A.B)"
                state.eachIsNotEmpty(r12, r13, r15, r14, r8, r10) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r9, r11)
                } -> "(A.!D) + (A.B)"
                state.eachIsNotEmpty(r12, r13, r15, r14, r8, r11) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r9, r10)
                } -> "(A.!C.!D) + (A.C.D) + (A.B)"
                state.eachIsNotEmpty(r12, r13, r15, r14, r8, r11, r10) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r9)
                } -> "(A.!D) + (A.C) + (A.B)"
                state.eachIsNotEmpty(r12, r13, r15, r14, r8, r9) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r11, r10)
                } -> "(A.!C) + (A.B)"
                state.eachIsNotEmpty(r12, r13, r15, r14, r8, r9, r10) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r11)
                } -> "(A.!C) + (A.!D) + (A.B)"
                state.eachIsNotEmpty(r12, r13, r15, r14, r8, r9, r11) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6, r10)
                } -> "(A.!C) + (A.D) + (A.B)"
                state.eachIsNotEmpty(r12, r13, r15, r14, r8, r9, r11, r10) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r6)
                } -> "A"
                state.eachIsNotEmpty(r6) {
                    adds(r0, r1, r3, r2, r4, r5, r7, r12, r13, r15, r14, r8, r9, r11, r10)
                } -> "!A.B.C.!D"

                /* ...................................................................... */
                /* IGNORE SOME STATE..................................................... */

                state.eachIsNotEmpty(r7) {
                    adds(r0, r1, r3, r2, r4, r5, r6, r12, r13, r15, r14, r8, r9, r11, r10)
                } -> "!A.B.C.D"
                state.eachIsNotEmpty(r5) {
                    adds(r0, r1, r3, r2, r4, r7, r6, r12, r13, r15, r14, r8, r9, r11, r10)
                } -> "!A.B.!C.D"
                state.eachIsNotEmpty(r4) {
                    adds(r0, r1, r3, r2, r5, r7, r6, r12, r13, r15, r14, r8, r9, r11, r10)
                } -> "!A.B.!C.!D"
                state.eachIsNotEmpty(r2) {
                    adds(r0, r1, r3, r4, r5, r7, r6, r12, r13, r15, r14, r8, r9, r11, r10)
                } -> "!A.!B.C.!D"
                state.eachIsNotEmpty(r3) {
                    adds(r0, r1, r2, r4, r5, r7, r6, r12, r13, r15, r14, r8, r9, r11, r10)
                } -> "!A.!B.C.D"
                state.eachIsNotEmpty(r1) {
                    adds(r0, r3, r2, r4, r5, r7, r6, r12, r13, r15, r14, r8, r9, r11, r10)
                } -> "!A.!B.!C.D"
                state.eachIsNotEmpty(r0) {
                    adds(r1, r3, r2, r4, r5, r7, r6, r12, r13, r15, r14, r8, r9, r11, r10)
                } -> "!A.!B.!C.!D"
                eachIsNotEmpty(
                    r0, r1, r3, r2, r4, r5, r7, r6, r12, r13, r15, r14, r8, r9, r11, r10
                ) -> state
                else -> c.stringRes(R.string.kmap_answer_not_defined)
            }.toString() as T
        }
}
