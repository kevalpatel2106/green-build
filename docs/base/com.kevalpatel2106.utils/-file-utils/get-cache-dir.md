---
title: FileUtils.getCacheDir - base
---

[base](../../index.html) / [com.kevalpatel2106.utils](../index.html) / [FileUtils](index.html) / [getCacheDir](./get-cache-dir.html)

# getCacheDir

`@JvmStatic fun getCacheDir(context: `[`Context`](https://developer.android.com/reference/android/content/Context.html)`): `[`File`](https://developer.android.com/reference/java/io/File.html)

Get the cache directory for the application. If external cache directory is not available,
ciServers will return internal (data/data) cache directory.

### Parameters

`context` - Instance of the caller.