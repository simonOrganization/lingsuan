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
-keep com.cclx.mobile.permission.** {*;}