---
title: MockServerManager - testutils
---

[testutils](../../index.html) / [com.kevalpatel2106.testutils](../index.html) / [MockServerManager](./index.html)

# MockServerManager

`class MockServerManager : `[`Closeable`](https://developer.android.com/reference/java/io/Closeable.html)

Created by Keval on 05/12/17.

**Author**
kevalpatel2106

### Constructors

| [&lt;init&gt;](-init-.html) | `MockServerManager()`<br>Created by Keval on 05/12/17. |

### Properties

| [mockWebServer](mock-web-server.html) | `lateinit var mockWebServer: MockWebServer` |

### Functions

| [close](close.html) | `fun close(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [enqueueResponse](enqueue-response.html) | `fun enqueueResponse(response: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, type: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)` = "application/json"): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>`fun enqueueResponse(response: `[`File`](https://developer.android.com/reference/java/io/File.html)`, type: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)` = "application/json"): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>`fun enqueueResponse(context: `[`Context`](https://developer.android.com/reference/android/content/Context.html)`, rawFile: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`, type: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)` = "application/json"): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Enqueue the next response in [mockWebServer](mock-web-server.html). |
| [getBaseUrl](get-base-url.html) | `fun getBaseUrl(): `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [getResponsesPath](get-responses-path.html) | `fun getResponsesPath(): `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [startMockWebServer](start-mock-web-server.html) | `fun startMockWebServer(): MockWebServer`<br>Start mock web server for the wikipedia api. |

