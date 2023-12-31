plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
//    id("kotlin-android")
    `maven-publish`
}

android {
    namespace = "io.rapidz.library"
    compileSdk = 34

    defaultConfig {
        minSdk = 28

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
//    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.9.10")
//    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
}

/**
 * This is for publishing and build AAR in local
 * Built AAR and commit & push to bitbucket
 * Comment these lines if publishing to Github, as it will return error and unable to release nxt version
 */

artifacts {
    archives(file("$buildDir/outputs/aar/${project.name}-release.aar")) // eg. "$buildDir/outputs/aar/${project.getName()}-release.aar"
}

publishing {
    publications {
        create<MavenPublication>("mavenAar") {
            groupId = "com.rapidz"
            version = "1.0.2"
            artifactId = "android.library"

            artifact(file("$buildDir/outputs/aar/${project.name}-release.aar")) // Replace "aar location" with the actual location of your AAR file

            pom.withXml {
                val dependenciesNode = asNode().appendNode("dependencies")

                configurations["implementation"].allDependencies.forEach { dependency ->
                    val dependencyNode = dependenciesNode.appendNode("dependency")
                    dependencyNode.appendNode("groupId", dependency.group)
                    dependencyNode.appendNode("artifactId", dependency.name)
                    dependencyNode.appendNode("version", dependency.version)
                }
            }
        }

        repositories {
            maven {
                name = "android-rapidz-library"
                setUrl( "/Users/charlesliew/StudioProjects/Rapidz/") // location where build generated
            }
        }
    }
}

/**
 * This is for publish to Github, uncomment if using for publishing to bitbucket
 * Check the versioning of released AAR at jitpack.io
 * Red = failed, green = success
 * Released AAR to jitpack.io might taking time, so the version increment is not instant
 */
//publishing {
//    publications {
//        create<MavenPublication>("ReleaseAar") {
//            groupId = "com.github.Syetchau.Rapidz"
//            artifactId = "RapidzLib"
//            version = "1.0.1"
//            afterEvaluate { artifact(tasks.getByName("bundleReleaseAar")) }
//        }
//    }
//}