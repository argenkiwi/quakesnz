object Versions {
    const val kotlin = "1.3.72"
    const val dagger = "2.26"
    const val rxjava2 = "2.2.17"
    const val store = "4.0.0"
    const val coroutines = "1.3.9"
}

object Dependencies {
    const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
    const val kotlinStdlibJdk7 = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"
    const val dagger = "com.google.dagger:dagger:${Versions.dagger}"
    const val daggerCompiler = "com.google.dagger:dagger-compiler:${Versions.dagger}"
    const val rxjava = "io.reactivex.rxjava2:rxjava:${Versions.rxjava2}"
    const val store = "com.dropbox.mobile.store:store4:${Versions.store}"
    const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
}
