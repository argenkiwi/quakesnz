object Versions {
    const val kotlin = "1.3.72"
    const val dagger = "2.26"
    const val rxjava2 = "2.2.17"
    const val hilt = "2.33-beta"
}

object Dependencies {
    const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
    const val hiltGradlePlugin = "com.google.dagger:hilt-android-gradle-plugin:${Versions.hilt}"
    const val dagger = "com.google.dagger:dagger:${Versions.dagger}"
    const val daggerCompiler = "com.google.dagger:dagger-compiler:${Versions.dagger}"
    const val rxjava = "io.reactivex.rxjava2:rxjava:${Versions.rxjava2}"
}
