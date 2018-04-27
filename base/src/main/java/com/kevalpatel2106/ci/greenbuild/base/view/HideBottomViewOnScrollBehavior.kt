/*
 * Copyright 2018 Keval Patel.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0.
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.kevalpatel2106.ci.greenbuild.base.view


import android.content.Context
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.CoordinatorLayout.Behavior
import android.support.v4.view.ViewCompat
import android.support.v4.view.animation.FastOutLinearInInterpolator
import android.support.v4.view.animation.LinearOutSlowInInterpolator
import android.util.AttributeSet
import android.view.View

/**
 * The [Behavior] for a View within a [CoordinatorLayout] to hide the view off the
 * bottom of the screen when scrolling down, and show it when scrolling up.
 */
@Suppress("unused")
class HideBottomViewOnScrollBehavior<V : View> : CoordinatorLayout.Behavior<V> {

    private var height = 0

    /**
     * Default constructor for instantiating HideBottomViewOnScrollBehaviors.
     */
    @Suppress("unused")
    constructor()

    /**
     * Default constructor for inflating HideBottomViewOnScrollBehaviors from layout.
     *
     * @param context The [Context].
     * @param attrs The [AttributeSet].
     */
    @Suppress("unused")
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    override fun onLayoutChild(parent: CoordinatorLayout?, child: V?, layoutDirection: Int): Boolean {
        height = child!!.measuredHeight
        return super.onLayoutChild(parent, child, layoutDirection)
    }

    override fun onStartNestedScroll(
            coordinatorLayout: CoordinatorLayout,
            child: V,
            directTargetChild: View,
            target: View,
            nestedScrollAxes: Int): Boolean {
        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL
    }

    override fun onNestedScroll(
            coordinatorLayout: CoordinatorLayout,
            child: V,
            target: View,
            dxConsumed: Int,
            dyConsumed: Int,
            dxUnconsumed: Int,
            dyUnconsumed: Int) {
        if (dyConsumed > 0) {
            slideDown(child)
        } else if (dyConsumed < 0) {
            slideUp(child)
        }
    }

    private fun slideUp(child: V) {
        child.clearAnimation()
        child.animate()
                .translationY(0f)
                .setInterpolator(LinearOutSlowInInterpolator()).duration = ENTER_ANIMATION_DURATION.toLong()
    }

    private fun slideDown(child: V) {
        child.clearAnimation()
        child.animate()
                .translationY(height.toFloat())
                .setInterpolator(FastOutLinearInInterpolator()).duration = EXIT_ANIMATION_DURATION.toLong()
    }

    companion object {
        private const val ENTER_ANIMATION_DURATION = 225
        private const val EXIT_ANIMATION_DURATION = 175
    }
}
