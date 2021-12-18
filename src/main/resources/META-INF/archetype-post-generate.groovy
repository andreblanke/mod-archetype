import java.nio.file.FileVisitResult
import java.nio.file.Files
import java.nio.file.attribute.BasicFileAttributes
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.SimpleFileVisitor

def projectPath = Paths.get(request.outputDirectory, request.artifactId)

// Delete dummy pom.xml.
Files.delete(projectPath.resolve("pom.xml"))

def language = request.getProperties().get("language")
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
