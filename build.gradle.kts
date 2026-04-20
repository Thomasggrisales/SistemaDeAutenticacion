plugins {
    id("java")
    id("org.sonarqube") version "5.0.0.4638"
    id("org.springframework.boot") version "3.2.5"
    id("io.spring.dependency-management") version "1.1.4"
    id("info.solidsoft.pitest") version "1.15.0"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("org.springframework.boot:spring-boot-starter-web")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    pitest("org.pitest:pitest-junit5-plugin:1.2.1")
    testImplementation("io.cucumber:cucumber-java:7.18.0")
    testImplementation("io.cucumber:cucumber-junit-platform-engine:7.18.0")
    testImplementation("org.junit.platform:junit-platform-suite:1.10.2")
    testImplementation("io.cucumber:cucumber-spring:7.14.0")



}

sonar {
    properties {
        property("sonar.projectKey", "SistemaAutenticacion")
        property("sonar.projectName", "Sistemadeautenticacion")
        property("sonar.host.url", "http://localhost:9000")
        property("sonar.token", System.getenv("SONAR_TOKEN"))

        property("sonar.qualitygate.wait", "true")
    }
}

pitest {
    pitestVersion.set("1.15.0")
    junit5PluginVersion.set("1.2.1")
    targetClasses.set(listOf("org.example.*"))
    targetTests.set(listOf("org.example.*"))
    threads.set(4)
    outputFormats.set(listOf("HTML"))
    timestampedReports.set(false)
}

tasks.test {
    useJUnitPlatform()
}

tasks.register<Test>("acceptanceTest") {
    useJUnitPlatform()
    description = "Runs Cucumber acceptance tests."
    group = "verification"

    useJUnitPlatform()

    testClassesDirs = sourceSets["test"].output.classesDirs
    classpath = sourceSets["test"].runtimeClasspath

    systemProperty("cucumber.plugin", "pretty")
}