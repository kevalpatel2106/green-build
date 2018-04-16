---
title: ProgressButton - base
---

[base](../../index.html) / [com.kevalpatel2106.ci.greenbuild.base.progressButton](../index.html) / [ProgressButton](./index.html)

# ProgressButton

`open class ProgressButton : AppCompatButton, CustomizableByCode`

Modified from : https://github.com/leandroBorgesFerreira/LoadingButtonAndroid

### Constructors

| [&lt;init&gt;](-init-.html) | `ProgressButton(context: `[`Context`](https://developer.android.com/reference/android/content/Context.html)`)`<br>`ProgressButton(context: `[`Context`](https://developer.android.com/reference/android/content/Context.html)`, attrs: `[`AttributeSet`](https://developer.android.com/reference/android/util/AttributeSet.html)`)`<br>`ProgressButton(context: `[`Context`](https://developer.android.com/reference/android/content/Context.html)`, attrs: `[`AttributeSet`](https://developer.android.com/reference/android/util/AttributeSet.html)`, defStyleAttr: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`)`<br>`ProgressButton(context: `[`Context`](https://developer.android.com/reference/android/content/Context.html)`, attrs: `[`AttributeSet`](https://developer.android.com/reference/android/util/AttributeSet.html)`, defStyleAttr: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`, defStyleRes: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`)` |

### Functions

| [isLoading](is-loading.html) | `open fun isLoading(): `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [revertAnimation](revert-animation.html) | `open fun revertAnimation(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [setPaddingProgress](set-padding-progress.html) | `open fun setPaddingProgress(padding: `[`Float`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [setSpinningBarColor](set-spinning-bar-color.html) | `open fun setSpinningBarColor(color: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [startAnimation](start-animation.html) | `open fun startAnimation(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Method called to start the animation. Morphs in to a ball and then starts a loading spinner. |

### Inheritors

| [BaseButton](../../com.kevalpatel2106.ci.greenbuild.base.view/-base-button/index.html) | `class BaseButton : `[`ProgressButton`](./index.md)<br>Created by Keval Patel on 04/03/17. This base class is to extend the functionality of [AppCompatButton](#). Use this class instead of [android.widget.Button](https://developer.android.com/reference/android/widget/Button.html) through out the application. |

