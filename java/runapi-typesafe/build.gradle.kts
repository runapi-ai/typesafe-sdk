plugins {
  `java-library`
  `maven-publish`
}

extra["runapiSlug"] = "typesafe"

description = "RunAPI TypeSafe Java SDK for TypeSafe workflows."

java {
  withSourcesJar()
  withJavadocJar()
}

dependencies {
  api("ai.runapi:runapi-core:0.9.0")

  testImplementation(platform("org.junit:junit-bom:5.10.3"))
  testImplementation("org.junit.jupiter:junit-jupiter")
}

publishing {
  publications {
    create<MavenPublication>("mavenJava") {
      from(components["java"])
      artifactId = "runapi-typesafe"
      pom {
        name = "RunAPI TypeSafe Java SDK"
        description = "RunAPI TypeSafe Java SDK for TypeSafe workflows."
        url = "https://runapi.ai/models/jev"
        licenses {
          license {
            name = "Apache License, Version 2.0"
            url = "https://www.apache.org/licenses/LICENSE-2.0"
          }
        }
        developers {
          developer {
            id = "runapi"
            name = "RunAPI"
            email = "contact@runapi.ai"
          }
        }
        scm {
          url = "https://github.com/runapi-ai/typesafe-sdk"
          connection = "scm:git:https://github.com/runapi-ai/typesafe-sdk.git"
          developerConnection = "scm:git:ssh://git@github.com/runapi-ai/typesafe-sdk.git"
        }
      }
    }
  }
}
