# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/kevalpatel/Library/Android/sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
-dontskipnonpubliclibraryclasses
-forceprocessing
-optimizationpasses 5
-verbose
-ignorewarnings

# Keep name of all the classes
#-keepnames class ** { *; } #Remove if you don't have to stake trace

#Keep anotations there
-keepattributes *Annotation*

##---------------Begin: proguard configuration for removing logs  ----------
#remove log class
-assumenosideeffects class android.util.Log {
    public static *** d(...);
    public static *** v(...);
    public static *** i(...);
    public static *** w(...);
    public static *** e(...);
}
# Remove sout
-assumenosideeffects class java.io.PrintStream {
     public void println(%);
     public void println(**);
 }
##---------------End: proguard configuration for removing logs  ----------

##---------------Begin: proguard configuration for Crashlytics 2.+  ----------
-keep class com.crashlytics.** { *; }
-keep class com.crashlytics.android.**
-keepattributes SourceFile, LineNumberTable, *Annotation*

# If you are using custom exceptions, add this line so that custom exception types are skipped during obfuscation:
-keep public class * extends java.lang.Exception

# For Fabric to properly de-obfuscate your crash reports, you need to remove this line from your ProGuard config:
# -printmapping mapping.txt
##---------------End: proguard configuration for Crashlytics 2.+  ----------

##---------------Begin: proguard configuration for Glide  ----------
# Glide specific rules #
# https://github.com/bumptech/glide

-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
    **[] $VALUES;
    public *;
}
##---------------End: proguard configuration for Glide  ----------


##---------------Begin: proguard configuration for GSON  ----------
# Gson uses generic type information stored in a class file when working with fields. Proguard
# removes such information by default, so configure it to keep all of it.
-keepattributes Signature

# For using GSON @Expose annotation
-keepattributes *Annotation*

-keepattributes EnclosingMethod

# Gson specific classes
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.stream.** { *; }
##---------------End: proguard configuration for GSON  ----------


##---------------Begin: proguard configuration for OkHttp ----------
# OkHttp
-keepattributes Signature
-keepattributes *Annotation*
-keep class okhttp3.** { *; }
-keep interface okhttp3.** { *; }
-dontwarn okhttp3.**
##---------------End: proguard configuration for OkHttp ----------


##---------------Begin: proguard configuration for Retrofit2 ----------
# Retrofit 2.X
## https://square.github.io/retrofit/ ##

-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions

-keepclasseswithmembers class * {
    @retrofit2.http.* <methods>;
}
##---------------End: proguard configuration for Retrofit2 ----------


##---------------Begin: proguard configuration for supprot lib  ----------
-dontwarn android.support.design.**
-keep class android.support.design.** { *; }
-keep interface android.support.design.** { *; }
-keep public class android.support.design.R$* { *; }
##---------------End: proguard configuration for support lib  ----------


##---------------Begin: proguard configuration for appcompat lib  ----------
-keep public class android.support.v7.widget.** { *; }
-keep public class android.support.v7.internal.widget.** { *; }
-keep public class android.support.v7.internal.view.menu.** { *; }

-keep public class * extends android.support.v4.view.ActionProvider {
    public <init>(android.content.Context);
}
##---------------End: proguard configuration for appcompat lib  ----------

##---------------Begin: proguard configuration for cardview  ----------
# http://stackoverflow.com/questions/29679177/cardview-shadow-not-appearing-in-lollipop-after-obfuscate-with-proguard/29698051
-keep class android.support.v7.widget.RoundRectDrawable { *; }
##---------------End: proguard configuration for cardview  ----------


##---------------Begin: proguard configuration for about libraries ----------
-keep class .R
-keep class **.R$* {
    <fields>;
}
##---------------End: proguard configuration for about libraries ----------
