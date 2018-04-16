---
title: MockSharedPreference - testutils
---

[testutils](../../index.html) / [com.kevalpatel2106.testutils](../index.html) / [MockSharedPreference](./index.html)

# MockSharedPreference

`class MockSharedPreference : `[`SharedPreferences`](https://developer.android.com/reference/android/content/SharedPreferences.html)

Created by Kevalpatel2106 on 02-Jan-18.
Mock implementation of shared preference, which just saves data in memory using map.

**Author**
kevalpatel2106

### Types

| [MockSharedPreferenceEditor](-mock-shared-preference-editor/index.html) | `class MockSharedPreferenceEditor : `[`Editor`](https://developer.android.com/reference/android/content/SharedPreferences/Editor.html) |

### Constructors

| [&lt;init&gt;](-init-.html) | `MockSharedPreference()`<br>Created by Kevalpatel2106 on 02-Jan-18. Mock implementation of shared preference, which just saves data in memory using map. |

### Functions

| [contains](contains.html) | `fun contains(s: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`): `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [edit](edit.html) | `fun edit(): `[`Editor`](https://developer.android.com/reference/android/content/SharedPreferences/Editor.html) |
| [getAll](get-all.html) | `fun getAll(): `[`Map`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/index.html)`<`[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, *>` |
| [getBoolean](get-boolean.html) | `fun getBoolean(s: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, b: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)`): `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [getFloat](get-float.html) | `fun getFloat(s: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, v: `[`Float`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html)`): `[`Float`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html) |
| [getInt](get-int.html) | `fun getInt(s: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, i: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`): `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [getLong](get-long.html) | `fun getLong(s: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, l: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`): `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html) |
| [getString](get-string.html) | `fun getString(s: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, s1: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`?): `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`?` |
| [getStringSet](get-string-set.html) | `fun getStringSet(s: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, set: `[`Set`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-set/index.html)`<`[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`>?): `[`Set`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-set/index.html)`<`[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`>?` |
| [registerOnSharedPreferenceChangeListener](register-on-shared-preference-change-listener.html) | `fun registerOnSharedPreferenceChangeListener(onSharedPreferenceChangeListener: `[`OnSharedPreferenceChangeListener`](https://developer.android.com/reference/android/content/SharedPreferences/OnSharedPreferenceChangeListener.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [unregisterOnSharedPreferenceChangeListener](unregister-on-shared-preference-change-listener.html) | `fun unregisterOnSharedPreferenceChangeListener(onSharedPreferenceChangeListener: `[`OnSharedPreferenceChangeListener`](https://developer.android.com/reference/android/content/SharedPreferences/OnSharedPreferenceChangeListener.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |

