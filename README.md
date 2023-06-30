# TrUAPI
True Underscore API

TrUAPI is a Kotlin library for Spigot plugins that provides a variety of useful utilities and extensions. The library is designed to be used for projects by RoughlyUnderscore, but is open source and free to use for anyone. Support is not guaranteed if you use this library for yourself. However, if you have a feature suggestion, please feel free to open an issue on the GitHub repository.

### Usage example
```xml
<!-- Maven Central -->
Here be dragons + badge
```
```kotlin
import com.roughlyunderscore.truapi.MainClassUtils
import org.bukkit.plugin.java.JavaPlugin
class MyPlugin : JavaPlugin() {
  override fun onEnable() {
    MainClassUtils.requireDirectories(this, "playerdata")
    MainClassUtils.requireFiles(this, "messages" to "en_US.json")
  }
}
```
