# SKLibrary
Just useful library for my plugins and for you if you need :)

## Installing plugin
You must install `SKLibrary plugin` on your server to use this library in your plugin.<br>
You can download it from the [releases page](https://github.com/SoKnight/SKLibrary/releases) or 
compile it from sources by instruction below.

## Compiling from sources
See instruction for compiling plugin JAR from sources below.<br>
Instruction presented for Linux systems, but compiling process for Windows is similar.

### Requirements
You must have installed `Maven 3` and `JDK 8` (or above, but it isn't been tested).

### Compiling
1) Download sources using `git clone https://github.com/SoKnight/SKLibrary.git sklibrary`
2) Go to repository output folder using `cd sklibrary`
3) Compile JAR from sources using `mvn clean package`
4) Go to maven output folder using `cd target`
5) See the compiled `SKLibrary-X.X.X.jar` :)

## Development
To use my library in your project you need add Jitpack repository and the library as project dependency.<br>
See instruction for Maven below. I don't use Gradle, so I can't post instructions for it here.

### Maven project
Add Maven repository:
```xml
    <repositories>
        <repository>
            <id>jitpack.io</id>
            <url>https://jitpack.io</url>
        </repository>
    </repositories>
```
Add SKLibrary dependency:
```xml
    <dependencies>
        <!-- SKLibrary -->
        <dependency>
            <groupId>com.github.SoKnight</groupId>
            <artifactId>SKLibrary</artifactId>
            <version>1.15.1</version>
            <scope>provided</scope>
        </dependency>
    </dependencies>
```
