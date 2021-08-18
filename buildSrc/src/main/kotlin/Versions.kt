import java.text.SimpleDateFormat

object Versions {
    const val applicationId = "com.android.mvvm"

    const val compileSdkVersion = 29
    const val buildToolsVersion = "30.0.2"

    const val minAndroidSdk = 21
    const val targetSdk = 29
    const val verCode = 100     // XYZ
    const val verName = "1.0.0" // X.Y.Z; X = Major, Y = minor, Z = Patch level

    val dateFormat: String = SimpleDateFormat("HHmm").format(System.currentTimeMillis())
}
