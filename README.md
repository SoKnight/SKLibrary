# SKLibrary
Just usefull library for some my plugins and for you if you need :)

## Installing plugin
You must install `SKLibrary plugin` on your server to use this library in your plugin.
You can download it from the [releases page](https://github.com/SoKnight/SKLibrary/releases) or 
compile it from sources by instruction below.

## Compiling from sources
See instruction for compiling plugin JAR from sources below. Instruction presented for Linux systems, 
but compiling process for Windows is similar.

### Requirements
You must have installed `Maven 3` and `JDK 8` (or above, but it isn't been tested).

### Compiling
1) Download sources using `git clone https://github.com/SoKnight/SKLibrary.git sklib`
2) Go to repository output folder using `cd sklib`
3) Compile JAR from sources using `mvn install`
4) Go to maven output folder using `cd target`
5) See the compiled `sklibrary-1.4.2.jar` :)

## Development
To use my library in your project you need add repository `https://raw.github.com/SoKnight/SKLibrary/tree/mvn-repo/` and the
library as project dependency. See instruction for Maven below. I don't use Gradle so I can't post instructions for it here.

### Maven project
Add Maven repository hosted by Github:
```
    <repositories>
        ...
        <repository>
            <id>SKLibrary-mvn-repo</id>
            <url>https://github.com/SoKnight/SKLibrary/raw/mvn-repo/</url>
            <snapshots>
                <enabled>true</enabled>
                <updatePolicy>always</updatePolicy>
            </snapshots>
        </repository>
    </repositories>
```
Add SKLibrary dependency:
```
    <dependencies>
        <!-- SKLibrary -->
        <dependency>
            <groupId>ru.soknight</groupId>
            <artifactId>sklibrary</artifactId>
            <version>1.4.2</version>
            <scope>provided</scope>
        </dependency>
    </dependencies>
```
