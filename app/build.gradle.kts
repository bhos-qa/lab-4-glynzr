plugins {
    application
    jacoco // Add the Jacoco plugin for code coverage
    id("org.sonarqube") version "4.0.0.2929" // Use a valid version of the SonarQube plugin
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.7.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.7.0")
    implementation("com.google.guava:guava:31.0.1-jre")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

application {
    mainClass.set("org.example.App")
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        events("passed", "failed", "skipped")
    }
    finalizedBy(tasks.jacocoTestReport) // Ensures the test report is generated after tests run
}

// Configure the Jacoco report
tasks.jacocoTestReport {
    dependsOn(tasks.test) // Ensure tests are run before generating the report

    reports {
        xml.required.set(true)  // Required for SonarCloud
        html.required.set(true) // Optional, for local viewing
    }
}


// Optional: You can customize Jacoco's coverage rules here if needed
jacoco {
    toolVersion = "0.8.11" // Adjust to your preferred version
}

// Configure SonarQube properties

sonarqube {
    properties {

        property("sonar.projectName", "lab-4-glynzr")
        property("sonar.host.url", "https://sonarcloud.io")
        property("sonar.coverage.jacoco.xmlReportPaths","${project.buildDir}/reports/jacoco/test/jacocoTestReport.xml")

    }
}