plugins {
    `java-library`
    `maven-publish`
    id ("info.solidsoft.pitest") version "1.7.4"
    id("org.openjfx.javafxplugin") version "0.0.13" // Add JavaFX plugin
}

repositories {
    mavenLocal()
    maven {
        url = uri("https://repo.maven.apache.org/maven2/")
    }
}

javafx {
    version = "17.0.2"
    modules = listOf("javafx.controls", "javafx.fxml")
}

dependencies {
    implementation("org.openjfx:javafx-controls:17.0.2")
    implementation("org.openjfx:javafx-fxml:17.0.2")
    implementation("org.openjfx:javafx-graphics:17.0.2")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.8.1")
    testImplementation("org.mockito:mockito-core:4.3.1")
    testImplementation("org.mockito:mockito-junit-jupiter:4.3.1")

}

group = "com.example"
version = "1.0-SNAPSHOT"
description = "Notely"
java.sourceCompatibility = JavaVersion.VERSION_17
java.targetCompatibility = JavaVersion.VERSION_17


publishing {
    publications.create<MavenPublication>("maven") {
        from(components["java"])
    }
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

tasks.withType<Javadoc> {
    options.encoding = "UTF-8"
}

pitest {
    junit5PluginVersion.set("1.2.2")
    pitestVersion.set("1.7.4")
    targetClasses.set(listOf("notely.app.CreateController"))
    targetTests.set(listOf("com.example.test"))

}

tasks.test {
    useJUnitPlatform() // Ensure JUnit 5 is used
    testLogging {
        events("passed", "skipped", "failed")
        showStandardStreams = true
    }
}

