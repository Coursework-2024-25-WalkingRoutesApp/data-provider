plugins {
	kotlin("jvm") version "1.9.25"
	kotlin("plugin.spring") version "1.9.25"
	id("org.springframework.boot") version "3.4.0"
	id("io.spring.dependency-management") version "1.1.6"
}

group = "ru.hse.coursework"
version = "1.1.0"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

repositories {
	mavenCentral()
}

val kotlinVersion: String by project
val kotlinTestJunit5Version: String by project
val springBootStarterVersion: String by project
val jacksonModuleKotlinVersion: String by project
val jacksonDatatypeJtsVersion: String by project
val awsSdkVersion: String by project
val apacheClientVersion: String by project
val liquibaseVersion: String by project
val postgisJdbcVersion: String by project
val postgresqlVersion: String by project
val junitPlatformLauncherVersion: String by project
val micrometerJvmExtrasVersion: String by project
val springDocVersion: String by project

dependencies {

	implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:$springDocVersion")

	implementation("org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion")

	implementation("com.fasterxml.jackson.module:jackson-module-kotlin:$jacksonModuleKotlinVersion")
	implementation("com.bedatadriven:jackson-datatype-jts:$jacksonDatatypeJtsVersion")

	implementation("software.amazon.awssdk:aws-sdk-java:$awsSdkVersion")
	implementation("software.amazon.awssdk:apache-client:$apacheClientVersion")

	implementation("org.liquibase:liquibase-core:$liquibaseVersion")

	implementation("net.postgis:postgis-jdbc:$postgisJdbcVersion")
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("io.micrometer:micrometer-core")
	implementation("io.github.mweirauch:micrometer-jvm-extras:$micrometerJvmExtrasVersion")
	runtimeOnly("org.postgresql:postgresql:$postgresqlVersion")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5:$kotlinTestJunit5Version")
	runtimeOnly("io.micrometer:micrometer-registry-prometheus")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher:$junitPlatformLauncherVersion")
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
