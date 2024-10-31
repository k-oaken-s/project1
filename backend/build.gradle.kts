plugins {
    id("org.jetbrains.kotlin.jvm") version "1.9.25"
    id("org.jetbrains.kotlin.plugin.spring") version "1.9.25"
    id("org.springframework.boot") version "3.3.5"
    id("io.spring.dependency-management") version "1.1.6"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

repositories {
	mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-devtools")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.15.2") // 最新の Jackson モジュール
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.9.10") // 最新の Kotlin バージョン
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.9.10") // 最新の Kotlin stdlib
    implementation("jakarta.validation:jakarta.validation-api:3.0.2") // 最新の Jakarta Validation
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("io.jsonwebtoken:jjwt-api:0.11.5") // 最新の JJWT API
    implementation("mysql:mysql-connector-java:8.0.33") // 最新の MySQL Connector
    runtimeOnly("mysql:mysql-connector-java:8.0.33")
    runtimeOnly("com.h2database:h2:2.2.222") // 最新の H2 Database
    implementation("org.flywaydb:flyway-core:10.10.0") // 最新の Flyway Core
    implementation("org.flywaydb:flyway-mysql:10.10.0") // Flyway MySQL Extension
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.10.0") // 最新の JUnit 5
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.10.0")
}

kotlin {
    jvmToolchain(21)
}

tasks.test {
    useJUnitPlatform()
}
