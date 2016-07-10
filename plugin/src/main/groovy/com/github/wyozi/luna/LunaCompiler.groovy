package com.github.wyozi.luna

import org.gradle.api.file.FileTreeElement
import org.gradle.api.file.RelativePath
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.SourceTask
import org.gradle.api.tasks.TaskAction
import org.luaj.vm2.LuaString
import org.luaj.vm2.LuaValue
import org.luaj.vm2.Varargs
import org.luaj.vm2.lib.ResourceFinder
import org.luaj.vm2.lib.jse.JsePlatform

class LunaCompiler extends SourceTask {
    @OutputDirectory File outputDir;

    @TaskAction
    void compile() {
        logger.info("Initializing Lua env")

        def globals = JsePlatform.standardGlobals()
        globals.finder = new ResourceFinder() {
            @Override
            InputStream findResource(String filename) {
                return LunaCompiler.class.classLoader.getResourceAsStream("luna/bin/" + filename)
            }
        }

        def compiler = globals.load("""return require("compiler")""").call()
        def lunaToLua = compiler.get("lunaToLua")

        source.visit { FileTreeElement f ->
            if (f.isDirectory())
                return

            RelativePath relPath = f.getRelativePath()

            String luna = f.open().text
            String lua = lunaToLua.call(LuaValue.valueOf(luna)).tojstring()

            RelativePath outPath = relPath.replaceLastName(relPath.lastName.replace(".luna", ".lua"))
            File outFile = outPath.getFile(outputDir)

            outFile.parentFile.mkdirs()
            outFile.write(lua)

            /*
            String luna = f.file.text
            String lua = lunaToLua.call(LuaValue.valueOf(luna)).tojstring()
            println(lua)
            */
        }
    }
}
