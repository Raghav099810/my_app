plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
}
dependencies {
    implementation(kotlin("script-runtime"))
}

fun implementation(kotlin: Any) {

}
