import java.nio.file.Files
import java.nio.file.Paths

def projectPath = Paths.get(request.outputDirectory, request.artifactId)

// Delete dummy pom.xml.
Files.delete(projectPath.resolve("pom.xml"))
