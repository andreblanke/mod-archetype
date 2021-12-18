//file:noinspection GrPackage
import java.nio.file.FileVisitResult
import java.nio.file.Files
import java.nio.file.attribute.BasicFileAttributes
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.SimpleFileVisitor

import javax.lang.model.SourceVersion

def projectPath = Paths.get(request.outputDirectory, request.artifactId)

// Delete dummy pom.xml.
Files.delete(projectPath.resolve("pom.xml"))

def language = request.getProperties()["language"]
println("Using language '${language}'. Removing source files belonging to other languages:")

def removeLanguage(Path projectPath, String language, List<String> excludes) {
    for (String module : ["common", "fabric", "forge"])
        removeModuleLanguage(projectPath.resolve(module), language, excludes)
}

def removeModuleLanguage(Path modulePath, String language, List<String> excludes) {
    Files.walkFileTree(modulePath.resolve("src/main/${language}"), new SimpleFileVisitor<Path>() {
        FileVisitResult visitFile(Path file, BasicFileAttributes attributes) {
            if (!excludes.contains(file.getFileName().toString())) {
                Files.delete(file)
                println("Deleted '${file}'")
            }
            return FileVisitResult.CONTINUE
        }

        FileVisitResult postVisitDirectory(Path directory, IOException exception) throws IOException {
            if (exception != null)
                throw exception

            if (!Files.list(directory).findFirst().isPresent()) {
                Files.delete(directory)
                println("Deleted '${directory}'")
            }
            return FileVisitResult.CONTINUE
        }
    })
}

// Keep the .gitkeep file in the mixin package, as mixins must be written in Java.
def excludes = [".gitkeep"]
if (!language.equalsIgnoreCase("java")) {
    removeLanguage(projectPath, "java", excludes)
} else if (!language.equalsIgnoreCase("kotlin")) {
    removeLanguage(projectPath, "kotlin", excludes)
}

println("Removing Velocity file extensions:")

def removeVelocityFileExtensions(Path projectPath) {
    def velocityFileExtension = ".vsl"

    Files.walkFileTree(projectPath, new SimpleFileVisitor<Path>() {
        FileVisitResult visitFile(Path file, BasicFileAttributes attributes) {
            def fileName = file.getFileName().toString()
            if (file.getFileName().toString().endsWith(velocityFileExtension)) {
                def actualFileName = fileName.substring(0, fileName.length() - velocityFileExtension.length())

                Files.move(file, file.resolveSibling(actualFileName))
                println("Renamed '${file}' to '${actualFileName}'.")
            }
            return FileVisitResult.CONTINUE
        }

        FileVisitResult postVisitDirectory(Path directory, IOException exception) throws IOException {
            if (exception != null)
                throw exception
            return FileVisitResult.CONTINUE
        }
    })
}
removeVelocityFileExtensions(projectPath)

def modName = request.getProperties()["modName"]

boolean fixNeeded = false
String fixedModName = modName
if (SourceVersion.isKeyword(modName)) {
    println("Detected that the chosen modName '${modName}' is a Java keyword. Renaming to '${modName}_'.")

    fixNeeded = true
    fixedModName = "${modName}_"

    if (!language.equalsIgnoreCase("java"))
        println("Please note that no checks are made for keywords of languages other than Java at the moment.")
} else if (!SourceVersion.isIdentifier()) {

}
