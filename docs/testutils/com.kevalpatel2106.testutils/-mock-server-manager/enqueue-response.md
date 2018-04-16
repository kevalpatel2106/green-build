---
title: MockServerManager.enqueueResponse - testutils
---

[testutils](../../index.html) / [com.kevalpatel2106.testutils](../index.html) / [MockServerManager](index.html) / [enqueueResponse](./enqueue-response.html)

# enqueueResponse

`@JvmOverloads fun enqueueResponse(response: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, type: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)` = "application/json"): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)
`@JvmOverloads fun enqueueResponse(response: `[`File`](https://developer.android.com/reference/java/io/File.html)`, type: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)` = "application/json"): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)
`@JvmOverloads fun enqueueResponse(context: `[`Context`](https://developer.android.com/reference/android/content/Context.html)`, rawFile: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`, type: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)` = "application/json"): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)

Enqueue the next response in [mockWebServer](mock-web-server.html).

