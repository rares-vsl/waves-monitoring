plugins {
    id("org.danilopianini.gradle-pre-commit-git-hooks") version "2.1.4"
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

gitHooks {
    commitMsg { conventionalCommits() }
    createHooks(true)
}

rootProject.name = "kotlin"
