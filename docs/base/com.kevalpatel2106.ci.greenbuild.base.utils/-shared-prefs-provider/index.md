---
title: SharedPrefsProvider - base
---

[base](../../index.html) / [com.kevalpatel2106.ci.greenbuild.base.utils](../index.html) / [SharedPrefsProvider](./index.html)

# SharedPrefsProvider

`class SharedPrefsProvider`

Created by Keval on 20-Aug-16.
Class contains all the helper functions to deal with shared prefs. You need to call [SharedPrefsProvider.init](#)
to initialize the shared preferences in your application class.

**Author**
kevalpatel2106

### Constructors

| [&lt;init&gt;](-init-.html) | `SharedPrefsProvider(context: `[`Context`](https://developer.android.com/reference/android/content/Context.html)`)``SharedPrefsProvider(sharedPreference: `[`SharedPreferences`](https://developer.android.com/reference/android/content/SharedPreferences.html)`)`<br>Created by Keval on 20-Aug-16. Class contains all the helper functions to deal with shared prefs. You need to call [SharedPrefsProvider.init](#) to initialize the shared preferences in your application class. |

### Functions

| [getBoolFromPreferences](get-bool-from-preferences.html) | `fun getBoolFromPreferences(key: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, defVal: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)` = false): `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Read string from shared preference file |
| [getIntFromPreference](get-int-from-preference.html) | `fun getIntFromPreference(key: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, defVal: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)` = -1): `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)<br>Read string from shared preference file |
| [getLongFromPreference](get-long-from-preference.html) | `fun getLongFromPreference(key: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, defVal: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)` = -1): `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)<br>Read string from shared preference file |
| [getStringFromPreferences](get-string-from-preferences.html) | `fun getStringFromPreferences(key: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, defVal: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`? = null): `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`?`<br>Read string from shared preference file |
| [nukePrefrance](nuke-prefrance.html) | `fun nukePrefrance(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [removePreferences](remove-preferences.html) | `fun removePreferences(key: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Remove and clear data from preferences for given field |
| [savePreferences](save-preferences.html) | `fun savePreferences(key: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, value: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`?): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>`fun savePreferences(key: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, value: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>`fun savePreferences(key: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, value: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>`fun savePreferences(key: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, value: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Save value to shared preference |

### Companion Object Properties

| [USER_PREF_FILE](-u-s-e-r_-p-r-e-f_-f-i-l-e.html) | `val USER_PREF_FILE: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>Name of the shared preference file. |

