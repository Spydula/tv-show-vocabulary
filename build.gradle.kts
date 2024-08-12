plugins {
    kotlin("jvm") version "2.0.0"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("edu.stanford.nlp:stanford-corenlp:4.2.2")
    implementation("edu.stanford.nlp:stanford-corenlp:4.2.2:models")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}