// Copyright 2023 RoughlyUnderscore
// com.roughlyundescore.truapi.MainClassUtils
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.roughlyundescore.truapi

import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin
import java.nio.file.Paths
import kotlin.io.path.createDirectory
import kotlin.io.path.isDirectory
import kotlin.io.path.notExists
import java.lang.reflect.Constructor

/**
 * Utility class for the main class.
 */
class MainClassUtils {
  companion object {
    /**
     * Makes sure that the directories exist, and if they don't, creates them.
     * @param plugin The plugin to create the directories for.
     * @param paths The paths to create.
     * @since 1.0
     */
    fun requireDirectories(plugin: JavaPlugin, vararg paths: String) {
      for (path in paths) {
        val directory = Paths.get(plugin.dataFolder.absolutePath, path)
        if (!directory.isDirectory() || directory.notExists()) directory.createDirectory()
      }
    }

    /**
     * Makes sure that the files exist, and if they don't, creates them.
     * @param plugin The plugin to create the files for.
     * @param filePaths The files to create in the format of a pair of a directory & a file name. The directory can be null,
     * in which case the file will be created in the plugin's data folder.
     * @since 1.0
     */
    fun requireFiles(plugin: JavaPlugin, vararg filePaths: Pair<String?, String>) {
      for (file in filePaths) {
        requireDirectories(plugin, file.first ?: continue)
        val filePath = Paths.get(plugin.dataFolder.absolutePath, file.first ?: "", file.second)
        if (!filePath.toFile().exists()) plugin.saveResource("${file.first}/${file.second}", true)
      }
    }

    /**
     * Creates a new instance of each listener class and registers it against the plugin. The listener classes must
     * have their primary constructor take a JavaPlugin and only a JavaPlugin. For those that don't, use [registerListeners].
     * @param plugin The plugin to register the listeners for.
     * @param classes The classes to register.
     * @since 1.0
     * @see registerListeners
     * @throws Exception See the documentation for [Constructor.newInstance].
     */
    fun registerListenerClasses(plugin: JavaPlugin, vararg classes: Class<out Listener>) {
      for (clazz in classes) {
        val instance = clazz.constructors.first().newInstance(plugin) as Listener
        registerListeners(plugin, instance)
      }
    }

    /**
     * Registers the listeners against the plugin.
     * @param plugin The plugin to register the listeners for.
     * @param listeners The listeners to register.
     */
    fun registerListeners(plugin: JavaPlugin, vararg listeners: Listener) {
      for (listener in listeners) plugin.server.pluginManager.registerEvents(listener, plugin)
    }
  }
}

