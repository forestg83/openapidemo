plugins {
    kotlin("jvm") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25"
    id("org.springframework.boot") version "3.3.4"
    id("io.spring.dependency-management") version "1.1.6"
    id("org.openapi.generator") version "7.8.0"
}

group = "pl.bodus"
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
    implementation("org.springframework.boot:spring-boot-starter-validation")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")


}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.compileKotlin {
    dependsOn(tasks.named("generate-server"))
    dependsOn(tasks.named("generate-client"))
}



tasks.create("generate-server", org.openapitools.generator.gradle.plugin.tasks.GenerateTask::class)
{
    generatorName.set("kotlin-spring")
    remoteInputSpec.set("https://raw.githubusercontent.com/openapitools/openapi-generator/master/modules/openapi-generator/src/test/resources/3_0/petstore.yaml")
//	inputSpec.set("$rootDir/specs/main.spec.yaml")

    outputDir.set("$buildDir/generated/server")
    apiPackage.set("pl.bodus.openapidemo.server.petstore.api")
    modelPackage.set("pl.bodus.openapidemo.server.petstore.api")
    configOptions.putAll(
        mapOf(
            Pair("gradleBuildFile", "false"),
            Pair("useSpringBoot3", "true"),
            Pair("documentationProvider", "none"),
            Pair("exceptionHandler", "false"),
            Pair("serializationLibrary", "jackson")
        )
    )
    generateApiTests.set(false);
}

tasks.create("generate-client", org.openapitools.generator.gradle.plugin.tasks.GenerateTask::class)
{
    generatorName.set("kotlin")
//    inputSpec.set("$rootDir/specs/main.spec.yaml")
    remoteInputSpec.set("https://raw.githubusercontent.com/openapitools/openapi-generator/master/modules/openapi-generator/src/test/resources/3_0/petstore.yaml")
    outputDir.set("$buildDir/generated/client")
    apiPackage.set("pl.bodus.petstore.api")
    packageName.set("pl.bodus.petstore")
    modelPackage.set("pl.bodus.petstore.model")

    configOptions.putAll(
        mapOf(
            Pair("dateLibrary", "java8"),
            Pair("idea", "false"),
            Pair("useSpringBoot3", "true"),
            Pair("serializationLibrary", "jackson"),
        )
    )
    generateApiTests.set(false)
}

sourceSets.getByName("main").kotlin.srcDir("$buildDir/generated")
