/**
 * Created by Leandro on 14/02/2018.
 */
object Versions {
    const val lifecycle = "1.1.0"
    const val dagger= "2.14.1"
    const val retrofit = "2.3.0"
    const val rxjava = "2.1.9"
}

object Libraries {
    const val dagger = "com.google.dagger:dagger:${Versions.dagger}"
    const val dagger_compiler = "com.google.dagger:dagger-compiler:${Versions.dagger}"
    const val dagger_android = "com.google.dagger:dagger-android:${Versions.dagger}"
    const val dagger_android_support = "com.google.dagger:dagger-android-support:${Versions.dagger}"
    const val dagger_android_processor = "com.google.dagger:dagger-android-processor:${Versions.dagger}"
    const val lifecycle_extensions = "android.arch.lifecycle:extensions:${Versions.lifecycle}"
    const val lifecycle_reactivestreams = "android.arch.lifecycle:reactivestreams:${Versions.lifecycle}"
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val retrofit_converter = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
    const val retrofit_adapter = "com.squareup.retrofit2:adapter-rxjava2:${Versions.retrofit}"
    const val rxjava = "io.reactivex.rxjava2:rxjava:${Versions.rxjava}"
}