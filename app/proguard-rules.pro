# proguard-rules.pro

# General ProGuard Rules
-keepclassmembers class * {
    @androidx.annotation.Keep <fields>;
    @androidx.annotation.Keep <methods>;
}

# Prevent obfuscation of classes extending specific Android components
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider

# Parcelize
-keepclassmembers class **$$Parcelable* {
    static *** CREATOR;
}

# Room
-keep class androidx.room.** { *; }
-keep interface androidx.room.** { *; }

# Gson
-keep class com.google.gson.** { *; }
-keep class org.**.R$* { *; }

# Retrofit
-keep class retrofit2.** { *; }
-keep class okhttp3.** { *; }
