# QuickImmi Model Package

The QuickImmi Model Package provides essential modeling capabilities for the QuickImmi projects.

## Repository

This package is hosted in a private Bytesafe repository to ensure secure and controlled access. For more details about the package and its versions, visit the following Bytesafe link:

[QuickImmi Model Package on Bytesafe](https://quickimmi.bytesafe.dev/workspace/registries/quickimmi-maven/packages/com.quick.immi.ai%3Aquickimmi-model/0.1.0)

## Building the Package

To build the QuickImmi Model Package, make code change and use the following command in the project root directory:

```
./gradlew build --refresh-dependencies
```

## Publishing the Package
If you have made changes to the package and need to publish a new version to Bytesafe, use the following command:
```
./gradlew publish --info
```

## Usage in Service Projects
To include the QuickImmi Model Package in your service projects, add the following dependency to your build.gradle:

```
dependencies {
    implementation 'com.quick.immi.ai:quickimmi-model:0.1.0'
}
```

Ensure your build.gradle also includes the Bytesafe repository in its repositories block:
```
repositories {
    maven {
        url = uri("https://quickimmi.bytesafe.dev/maven/quickimmi-maven/")
        credentials {
            username = System.getenv("BYTESAFE_USERNAME")
            password = System.getenv("BYTESAFE_TOKEN")
        }
    }
}
```