# SKLibrary
Just usefull library for some my plugins and for you if you need :)

## Installing plugin
You must install SKLibrary plugin on your server to use this library in your plugin. Soon I will public plugin jars on
Spigot resources portal, but now you only can compile it from sources. Just follow instruction below. Instruction written for
Linux distribs, but for Windows it will be similar.

1) Download sources: `git clone https://github.com/SoKnight/SKLibrary.git sklibrary`
2) Move into sources directory: `cd sklibrary`
3) Compile sources: `mvn clean install`
4) Open `sklibrary/target` and copy `sklibrary-1.0.8.jar` into server `plugins` folder
5) Restart your server

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
            <url>https://raw.github.com/SoKnight/SKLibrary/tree/mvn-repo/</url>
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
            <version>1.0.8</version>
            <scope>provided</scope>
        </dependency>
    </dependencies>
```
