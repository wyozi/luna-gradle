luna-gradle adds Gradle support for Luna source sets.

The Luna compiler is included with the dependency and does not need Lua to run (LuaJ is used internally).

## Example gradle project
```
buildscript {
    repositories {
        mavenLocal()
        mavenCentral()
    }
    dependencies {
        classpath group: 'com.github.wyozi.luna',
                name: 'luna-gradle-plugin',
                version: '1.0-SNAPSHOT'
    }
}

apply plugin: 'luna'

compileLuna {
    outputDir = file 'lua/'
}
```

This project definition automatically compiles all .luna files in `src/` and places them to relative location in `lua/`.

Compile Luna to Lua: `gradlew compile`
Compile & watch for changes: `gradlew compile --continuous`