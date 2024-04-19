import org.gradle.api.Project

class AndroidUtils {

    companion object {
        fun getEmulatorId(): String {
            return ProcessBuilder("adb", "devices")
                .start()
                .inputStream
                .bufferedReader()
                .useLines { lines ->
                    lines.filter { it.contains("emulator") }
                        .map { it.substringBefore("\t") }
                        .firstOrNull()
                }
                ?: throw IllegalArgumentException("\u001B[31mNo emulator found running. Please start an emulator and try again.\u001B[0m")
        }

        fun isAppInstalled(emulatorId: String, packageName: String): Boolean {
            return ProcessBuilder("adb", "-s", emulatorId, "shell", "pm", "list", "packages", "-f")
                .start()
                .inputStream
                .bufferedReader()
                .useLines { lines ->
                    lines.any { it.contains(packageName) }
                }
        }

        fun installApp(project: Project, emulatorId: String, apkPath: String) {
            println("App not installed, installing...")
            project.exec {
                commandLine("adb", "-s", emulatorId, "install", apkPath)
                isIgnoreExitValue = true
            }
            println("App installed successfully on emulator: [$emulatorId]")
        }

        fun checkIfMainActivityIsRunning(emulatorId: String, packageName: String): Boolean {
            return ProcessBuilder("adb", "-s", emulatorId, "shell", "dumpsys", "activity", "activities")
                .start()
                .inputStream
                .bufferedReader()
                .useLines { lines ->
                    lines.any { it.contains(packageName) && it.contains("MainActivity") }
                }
        }
    }
}