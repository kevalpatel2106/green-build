/*
 *  Copyright 2018 Keval Patel.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 */

package com.kevalpatel2106.ci.greenbuild.base.progressButton;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;

import com.kevalpatel2106.ci.greenbuild.base.R;


/**
 * Modified from : https://github.com/leandroBorgesFerreira/LoadingButtonAndroid
 */
public class ProgressButton extends AppCompatButton implements CustomizableByCode {
    private State mState;
    private CircularAnimatedDrawable mAnimatedDrawable;
    private Params mParams;

    public ProgressButton(Context context) {
        super(context);

        init(context, null, 0, 0);
    }

    public ProgressButton(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(context, attrs, 0, 0);
    }

    public ProgressButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context, attrs, defStyleAttr, 0);
    }

    @TargetApi(23)
    public ProgressButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr);

        init(context, attrs, defStyleAttr, defStyleRes);
    }

    /**
     * Commom initializer method.
     *
     * @param context Context
     * @param attrs   Atributes passed in the XML
     */
    private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        mParams = new Params();

        mParams.mPaddingProgress = 0f;

        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ProgressButton, defStyleAttr, defStyleRes);

            mParams.mSpinningBarColor = typedArray.getColor(R.styleable.ProgressButton_spinning_bar_color,
                    ContextCompat.getColor(context, android.R.color.black));
            mParams.mPaddingProgress = typedArray.getDimension(R.styleable.ProgressButton_spinning_bar_padding, 0);

            typedArray.recycle();
        }

        mState = State.IDLE;

        mParams.mText = this.getText().toString();
        mParams.mDrawables = this.getCompoundDrawablesRelative();
    }

    @Override
    public void setSpinningBarColor(@ColorInt int color) {
        mParams.mSpinningBarColor = color;
        if (mAnimatedDrawable != null) {
            mAnimatedDrawable.setLoadingBarColor(color);
        }
    }

    @Override
    public void setPaddingProgress(float padding) {
        mParams.mPaddingProgress = padding;
    }

    /**
     * This method is called when the button and its dependencies are going to draw it selves.
     *
     * @param canvas Canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mState == State.PROGRESS) {
            drawIndeterminateProgress(canvas);
        }
    }

    /**
     * If the mAnimatedDrawable is null or its not running, it get created. Otherwise its draw method is
     * called here.
     *
     * @param canvas Canvas
     */
    private void drawIndeterminateProgress(Canvas canvas) {
        if (mAnimatedDrawable == null || !mAnimatedDrawable.isRunning()) {
            mAnimatedDrawable = new CircularAnimatedDrawable(this, 10, mParams.mSpinningBarColor);

            int offset = (getWidth() - getHeight()) / 2;

            int left = offset + mParams.mPaddingProgress.intValue();
            int right = getWidth() - offset - mParams.mPaddingProgress.intValue();
            int bottom = getHeight() - mParams.mPaddingProgress.intValue();
            int top = mParams.mPaddingProgress.intValue();

            mAnimatedDrawable.setBounds(left, top, right, bottom);
            mAnimatedDrawable.setCallback(this);
            mAnimatedDrawable.start();
        } else {
            mAnimatedDrawable.draw(canvas);
        }
    }

    public boolean isLoading() {
        return mState == State.PROGRESS;
    }

    public void revertAnimation() {
        mState = State.IDLE;

        this.setText(mParams.mText);
        this.setCompoundDrawablesRelative(mParams.mDrawables[0], mParams.mDrawables[1], mParams.mDrawables[2], mParams.mDrawables[3]);
    }

    /**
     * Method called to start the animation. Morphs in to a ball and then starts a loading spinner.
     */
    public void startAnimation() {
        mState = State.PROGRESS;

        mParams.mText = this.getText().toString();
        mParams.mDrawables = this.getCompoundDrawablesRelative();

        this.setText(null);
        this.setCompoundDrawables(null, null, null, null);
    }

    private enum State {
        PROGRESS, IDLE
    }

    /**
     * Class with all the params to configure the button.
     */
    private class Params {
        private int mSpinningBarColor;
        private Float mPaddingProgress;
        private String mText;
        private Drawable[] mDrawables;
    }
}
