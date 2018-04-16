---
title: BaseButton - base
---

[base](../../index.html) / [com.kevalpatel2106.ci.greenbuild.base.view](../index.html) / [BaseButton](./index.html)

# BaseButton

`class BaseButton : `[`ProgressButton`](../../com.kevalpatel2106.ci.greenbuild.base.progress-button/-progress-button/index.html)

Created by Keval Patel on 04/03/17.
This base class is to extend the functionality of [AppCompatButton](#). Use this class instead
of [android.widget.Button](https://developer.android.com/reference/android/widget/Button.html) through out the application.

**Author**
'https://github.com/kevalpatel2106'

### Constructors

| [&lt;init&gt;](-init-.html) | `BaseButton(context: `[`Context`](https://developer.android.com/reference/android/content/Context.html)`)`<br>`BaseButton(context: `[`Context`](https://developer.android.com/reference/android/content/Context.html)`, attrs: `[`AttributeSet`](https://developer.android.com/reference/android/util/AttributeSet.html)`)`<br>`BaseButton(context: `[`Context`](https://developer.android.com/reference/android/content/Context.html)`, attrs: `[`AttributeSet`](https://developer.android.com/reference/android/util/AttributeSet.html)`, defStyleAttr: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`)` |

### Functions

| [displayLoader](display-loader.html) | `fun displayLoader(display: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [onTouchEvent](on-touch-event.html) | `fun onTouchEvent(event: `[`MotionEvent`](https://developer.android.com/reference/android/view/MotionEvent.html)`?): `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |

### Inherited Functions

| [isLoading](../../com.kevalpatel2106.ci.greenbuild.base.progress-button/-progress-button/is-loading.html) | `open fun isLoading(): `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [revertAnimation](../../com.kevalpatel2106.ci.greenbuild.base.progress-button/-progress-button/revert-animation.html) | `open fun revertAnimation(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [setPaddingProgress](../../com.kevalpatel2106.ci.greenbuild.base.progress-button/-progress-button/set-padding-progress.html) | `open fun setPaddingProgress(padding: `[`Float`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [setSpinningBarColor](../../com.kevalpatel2106.ci.greenbuild.base.progress-button/-progress-button/set-spinning-bar-color.html) | `open fun setSpinningBarColor(color: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [startAnimation](../../com.kevalpatel2106.ci.greenbuild.base.progress-button/-progress-button/start-animation.html) | `open fun startAnimation(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Method called to start the animation. Morphs in to a ball and then starts a loading spinner. |

