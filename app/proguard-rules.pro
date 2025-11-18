# Add project specific ProGuard rules here.
# By default, the flags are merged with the return of the SDK.
# See http://developer.android.com/tools/help/proguard.html for more details.

-dontwarn com.google.protobuf.**
-dontwarn okio.**
-dontwarn javax.inject.**

# Keep all members of data classes used by Room
-keepclassmembers class * {
    @androidx.room.PrimaryKey <fields>;
}

# Keep all members of the Hilt-generated classes
-keep class * extends androidx.lifecycle.ViewModel {
    <init>(...);
}
-keep class * extends androidx.work.ListenableWorker {
    <init>(...);
}
-keep class * extends dagger.hilt.android.HiltAndroidApp {
    <init>(...);
}
-keep class * extends dagger.hilt.android.HiltAndroidApp {
    <init>(...);
}
-keep class * extends dagger.hilt.android.components.ServiceComponent {
    <init>(...);
}
-keep class * extends dagger.hilt.android.components.ActivityComponent {
    <init>(...);
}
-keep class * extends dagger.hilt.android.components.FragmentComponent {
    <init>(...);
}
-keep class * extends dagger.hilt.android.components.ViewComponent {
    <init>(...);
}
-keep class * extends dagger.hilt.android.components.ViewModelComponent {
    <init>(...);
}
-keep class * extends dagger.hilt.android.components.WorkerComponent {
    <init>(...);
}
