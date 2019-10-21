# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

-dontshrink
 -dontpreverify
 -dontoptimize
 -dontusemixedcaseclassnames

 -flattenpackagehierarchy
 -allowaccessmodification
 -printmapping map.txt

 -optimizationpasses 7
 -dontnote
 -verbose
 -keepattributes Exceptions,InnerClasses
 -dontskipnonpubliclibraryclasses
 -dontskipnonpubliclibraryclassmembers
 -ignorewarnings

 -optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
 -keep public class * extends android.app.Activity
 -keep public class * extends android.app.Application
 -keep public class * extends android.app.Service
 -keep public class * extends android.app.IntentService
 -keep public class * extends android.content.BroadcastReceiver
 -keep public class * extends android.content.ContentProvider
 -keep public class * extends android.app.backup.BackupAgentHelper
 -keep public class * extends android.preference.Preference
 -keep public class com.android.vending.licensing.ILicensingService
 -keep public class * extends java.lang.Throwable {*;}
 -keep public class * extends java.lang.Exception {*;}
 -keep public class com.hugboga.guide.data.entity.** {*;}
 -keep public class com.hugboga.guide.fragment.** {*;}
 -keep public class android.net.http.SslError

 -keep public class com.hugboga.custom.R$*{
     public static final int *;
 }

 -keep class * extends java.lang.annotation.Annotation { *; }

 -keepattributes *Annotation*
 -keepattributes *JavascriptInterface*

 -dontwarn android.webkit.WebView
 -dontwarn android.net.http.SslError
 -dontwarn android.webkit.WebViewClient

-keep class com.ling.suandashi.** {*;}

# android-support-v4
 -dontwarn android.support.**
 -keep class android.support.** { *;}

 # android-support-v7-appcompat
 -dontwarn android.support.v7.**
 -keep class android.support.v7.** { *;}

 # google-play-service
 -dontwarn com.google.**
 -keep class com.google.** { *;}

# 阿里百川反馈
-dontwarn com.taobao.**
-dontwarn com.alibaba.**
-dontwarn com.alipay.**
-dontwarn com.ut.**
-dontwarn com.ta.**
-keep class com.taobao.dp.**{*;}
-keep class com.taobao.securityjni.**{*;}
-keep class com.taobao.wireless.security.**{*;}
-keep class com.taobao.** {*;}
-keep class com.alibaba.** {*;}
-keep class com.alibaba.wireless.security.**{*;}
-keep class com.alibaba.sdk.android.feedback.impl.FeedbackServiceImpl {*;}
-keep class com.alibaba.sdk.android.feedback.impl.FeedbackAPI {*;}
-keep class com.alibaba.sdk.android.feedback.util.IWxCallback {*;}
-keep class com.alibaba.sdk.android.feedback.util.IUnreadCountCallback{*;}
-keep class com.alibaba.sdk.android.feedback.FeedbackService{*;}
-keep public class com.alibaba.mtl.log.model.LogField {public *;}
-keep class com.alipay.** {*;}
-keep class com.ut.secbody.**{*;}
-keep class com.ut.** {*;}
-keep class com.ta.** {*;}
-keep class com.ta.utdid2.device.**{*;}

# 权限组件
-keep public class com.cclx.mobile.permission.** {*;}

 #okhttp
-dontwarn com.squareup.okhttp.**
-keep class com.squareup.okhttp.**{*;}

# okhttp
-keep class okhttp3.** { *; }
-keep interface okhttp3.** { *; }
-dontwarn okhttp3.**

# okio
-keep class sun.misc.Unsafe { *; }
-dontwarn java.nio.file.*
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement
-keep class okio.**{*;}
-dontwarn okio.**


# okhttp start
-dontwarn javax.annotation.**
-dontwarn org.codehaus.mojo.animal_sniffer.*

-keep class okhttp3.internal.** { *;}
-dontwarn okio.**
# okhttp end


#retrofit2  混淆
-dontwarn javax.annotation.**
-dontwarn javax.inject.**

# Retrofit
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
# RxJava RxAndroid
-dontwarn sun.misc.**
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
# Gson
-keep class com.google.gson.stream.** { *; }
-keepattributes EnclosingMethod

#Glide
 -keep public class * implements com.bumptech.glide.module.GlideModule
 -keep public class * extends com.bumptech.glide.module.AppGlideModule
 -keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
   **[] $VALUES;
   public *;
 }

 -keep class butterknife.** { *; }
 -dontwarn butterknife.internal.**
 -keep class **$$ViewBinder { *; }
 -keepclasseswithmembernames class * {
     @butterknife.* <fields>;
 }
 -keepclasseswithmembernames class * {
     @butterknife.* <methods>;
 }