plugins {
    java
    jacoco
    id("org.springframework.boot") version "3.4.4"
    id("io.spring.dependency-management") version "1.1.7"
}


group = "id.ac.ui.cs.advprog"
version = "0.0.1-SNAPSHOT"


java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}


configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}


repositories {
    mavenCentral()
}


val seleniumJavaVersion = "4.14.1"
val seleniumJupiterVersion = "5.0.1"
val webdrivermanagerVersion = "5.6.3"
val junitJupiterVersion = "5.9.1"


dependencies {
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    testImplementation("org.springframework.security:spring-security-test:6.0.2")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    implementation ("org.postgresql:postgresql")

    implementation("io.jsonwebtoken:jjwt-api:0.11.5")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.5")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.5")

    runtimeOnly("org.postgresql:postgresql:42.6.0")
    testImplementation("com.h2database:h2:2.2.220")

    compileOnly("org.projectlombok:lombok")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    implementation("io.jsonwebtoken:jjwt-api:0.11.5")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.5")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.5")

    runtimeOnly("org.postgresql:postgresql:42.6.0")
    runtimeOnly("com.h2database:h2:2.2.220")

    compileOnly("org.projectlombok:lombok")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("io.micrometer:micrometer-registry-prometheus")
    implementation("io.micrometer:micrometer-core")
    runtimeOnly("io.micrometer:micrometer-registry-prometheus")
}


tasks.register<Test>("unitTest") {
    description = "Runs unit tests."
    group = "verification"


    filter {
        excludeTestsMatching("*FunctionalTest")
    }
}


tasks.register<Test>("functionalTest") {
    description = "Runs functional tests."
    group = "verification"


    filter {
        includeTestsMatching("*FunctionalTest")
    }
}


tasks.test {
    filter {
        excludeTestsMatching("*FunctionalTest")
    }
    finalizedBy(tasks.jacocoTestReport)
}


tasks.jacocoTestReport {
    dependsOn(tasks.test)
}


tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}
