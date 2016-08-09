# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in C:\Program Files (x86)\Android\android-sdk/tools/proguard/proguard-android.txt
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
-verbose

-dontwarn android.net.http.SslError

-dontwarn java.nio.file.Files
-dontwarn java.nio.file.Path
-dontwarn java.nio.file.OpenOption
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement
-dontwarn com.google.android.gms.**

# OkHttp rules
-dontwarn com.squareup.okhttp**
-dontwarn okio.**

-renamesourcefileattribute SourceFile
-keepattributes SourceFile,LineNumberTable
-keepclasseswithmembernames class * {
    native <methods>;
}

-keep class com.google.android.gms.** { *; }
-dontwarn java.lang.invoke**
-dontwarn java.lang.reflect.Method

# Add the gson class
-keep public class com.google.gson
# Gson specific classes
-keep class sun.misc.Unsafe { *; }
-dontwarn sun.misc.Unsafe
#-keep class com.google.gson.stream.** { *; }

# Application classes that will be serialized/deserialized over Gson
-keep class com.maxmakeychik.itunes_player.data.model.** { *; }

# For using GSON @Expose annotation
-keepattributes *Annotation*

#Rx
-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
   long producerIndex;
   long consumerIndex;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode producerNode;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode consumerNode;
}

# Crashlytics
-keep class com.crashlytics.** { *; }
-keep class com.crashlytics.android.**

# Butterknife
-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewBinder { *; }
-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}
-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}

# Retrofit2
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions

# SimpleXml
-keep class org.simpleframework.xml.** { *; }
-dontwarn org.simpleframework.xml.**

# Logs
-assumenosideeffects class android.util.Log {
    public static *** d(...);
    public static *** v(...);
}

# Otto bus
-keepattributes *Annotation*
-keepclassmembers class ** {
    @com.squareup.otto.Subscribe public *;
    @com.squareup.otto.Produce public *;
}

#SearchView
-keep class android.support.v7.widget.SearchView { *; }

#Androidsvg
-dontwarn com.caverock.**
-keepnames class * extends com.caverock.androidsvg.SVG$SvgElementBase

#android-gif-drawable
-keep public class pl.droidsonroids.gif.GifIOException{<init>(int);}
-keep class pl.droidsonroids.gif.GifInfoHandle{<init>(long,int,int,int);}

# Parcel library
-keep class **$$Parcelable { *; }