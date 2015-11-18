apply plugin: 'java'
//apply plugin: 'application'
apply plugin: 'groovy'

def projectMainClass='de.othsoft.??'
def projectTitle='This is a sample title - change'

sourceCompatibility = '1.7'
[compileJava, compileTestJava]*.options*.encoding = 'UTF-8'

project.group = 'de.othsoft.??'
project.version = '0.1-SNAPSHOT'

if (!hasProperty('mainClass')) {
    ext.mainClass = projectMainClass
}


uploadArchives {
    repositories {
        flatDir {
                dirs "${System.getenv('HOME')}/myGradleRepos/${project.group}/${project.name}"
        }
    }
}

repositories {
    ivy {
        url "file://${System.getenv('HOME')}/myGradleRepos"
        layout "pattern", {
            artifact "[organisation]/[artifact]/[artifact]-[revision].[ext]"
        }
    }
    mavenCentral()
}

dependencies {
    testCompile group: 'junit', name: 'junit', version: '4.10'
    compile 'org.slf4j:slf4j-api:1.7.5'
    compile 'ch.qos.logback:logback-classic:1.0.9'
    testCompile 'org.codehaus.groovy:groovy-all:2.4.3'
}

tasks.withType(Test) {
    reports.html.destination = file("${reporting.baseDir}/${name}")
}


