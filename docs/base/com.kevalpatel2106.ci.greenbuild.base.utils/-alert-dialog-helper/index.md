---
title: AlertDialogHelper - base
---

[base](../../index.html) / [com.kevalpatel2106.ci.greenbuild.base.utils](../index.html) / [AlertDialogHelper](./index.html)

# AlertDialogHelper

`class AlertDialogHelper`

### Constructors

| [&lt;init&gt;](-init-.html) | `AlertDialogHelper(context: `[`Context`](https://developer.android.com/reference/android/content/Context.html)`, title: `[`CharSequence`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-char-sequence/index.html)`? = null, message: `[`CharSequence`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-char-sequence/index.html)`? = null)` |

### Properties

| [cancelable](cancelable.html) | `var cancelable: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |

### Functions

| [create](create.html) | `fun create(): `[`AlertDialog`](https://developer.android.com/reference/android/app/AlertDialog.html) |
| [negativeButton](negative-button.html) | `fun negativeButton(textResource: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`, func: () -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)` = null): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>`fun negativeButton(text: `[`CharSequence`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-char-sequence/index.html)`, func: () -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)` = null): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [positiveButton](positive-button.html) | `fun positiveButton(textResource: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`, func: () -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)` = null): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>`fun positiveButton(text: `[`CharSequence`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-char-sequence/index.html)`, func: () -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)` = null): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |

### Extension Functions

| [defaultDialogButton](../default-dialog-button.html) | `fun `[`AlertDialogHelper`](./index.md)`.defaultDialogButton(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Created by Kevalpatel2106 on 29-Jan-18. |

