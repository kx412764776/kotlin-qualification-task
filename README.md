# kotlin-qualification-task

Qualification task for "Support Android target in kotlinx-benchmark" project

## Setup

1. Clone the repository
2. Required SDKs and tools:
    - Android Studio
    - JDK 17
    - Android SDK & Emulator
3. Configure Environment Variables:
    - `JAVA_HOME` to JDK 17, verify by running `java --version`
    - `adb` in SYSTEM PATH, verify by running `adb --version`
    - `gradle` in SYSTEM PATH, verify by running `gradle --version`

## Running Gradle Tasks

1. Start the emulator
2. Open a terminal or command prompt
3. navigate to the project's root directory
4. Build the project
    ```shell
    ./gradlew build
    ```
5. Task: Print Hello World
    - Jvm:
        ```shell
        ./gradlew jvmPrintHelloWorld
        ```
    - Android:
        ```shell
        ./gradlew androidPrintHelloWorld
        ```
6. Task: Print Fibonacci Sequence (--N as argument for length of sequence)
    - Jvm:
        ```shell
        ./gradlew jvmPrintFibonacciSequence -PN=7
        ```
    - Android: Before running the task, make sure running the app or start the MainActivity class
        ```shell
        ./gradlew androidPrintFibonacciSequence --N=7
        ```