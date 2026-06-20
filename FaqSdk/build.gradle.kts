plugins {
    id("com.android.library")
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.smcoding.faqsdk"
    compileSdk = 37

    defaultConfig {
        minSdk = 24
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.recyclerview)
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)
    implementation(libs.pdfbox.android)
    implementation("com.microsoft.onnxruntime:onnxruntime-android:1.26.0")

    implementation("androidx.room:room-runtime:2.8.4")
    ksp("androidx.room:room-compiler:2.8.4")
    implementation("ai.djl.huggingface:tokenizers:0.36.0")
    implementation("com.google.code.gson:gson:2.14.0")

    implementation("com.tom-roush:pdfbox-android:2.0.27.0")

    //ONNX runtime extensions
    implementation("com.microsoft.onnxruntime:onnxruntime-android:1.26.0")
    implementation("com.microsoft.onnxruntime:onnxruntime-extensions-android:0.13.0")

}
