# Advent of Code
My solutions to the [Advent of Code](https://adventofcode.com/), years 2023 and 2024.
* [The source code is here.](https://github.com/FWDekker/aoc/tree/main/aoc/src/main/kotlin/com/fwdekker/aoc)
* [My puzzle inputs are here.](https://github.com/FWDekker/aoc-inputs) (In a private repo.)

Also includes my solutions for [Project Euler](https://projecteuler.net/).
* [The source code is here.](https://github.com/FWDekker/project-euler) (In a private repo.)

## Git submodules
Project Euler does not allow sharing solutions.
Therefore, the code for Project Euler is [stored in a private repository](https://github.com/FWDekker/project-euler).
This repository is included as a [git submodule](https://git-scm.com/book/en/v2/Git-Tools-Submodules).
In other words, this repository contains a link to a specific commit in that other repository.

Note the following usage instructions.
```shell
# Clone without submodules
git clone git@github.com:FWDekker/aoc.git
# Clone with submodules
git clone git@github.com:FWDekker/aoc.git --recurse-submodules

# Add missing submodules after cloning
git submodule update --init --recursive
# Update submodules to respective HEADs
git submodule update --recursive --remote
```

To commit changes you've made in the `euler/` directory, run
```shell
cd euler; git commit
```
Now, the root repository will be outdated, since the local repository inside `euler/` no longer matches the commit referred to by the root repository.
Therefore, you'll have to commit the changes there as well (but don't fret if you forget).

## Gradle sub-projects
To allow code Advent of Code and Project Euler to be built separately, but still use a common codebase, the repository has been structured using [Gradle subprojects](https://docs.gradle.org/current/userguide/intro_multi_project_builds.html).
This repository has the following subprojects:
* [`buildSrc`](https://github.com/FWDekker/aoc/tree/main/buildSrc): Common build logic for all subprojects.
* [`std`](https://github.com/FWDekker/aoc/tree/main/std): Utility and helper functions for other subprojects.
* [`aoc`](https://github.com/FWDekker/aoc/tree/main/aoc): Advent of Code.
* [`euler`](https://github.com/FWDekker/project-euler): Project Euler.

Check their respective `README.md`s for more information.

You can run tasks as follows:
```shell
# Run all tests in all subprojects
gradlew test
# Run all tests in subproject 'aoc'
gradlew :aoc:test
# Run tests tagged with "Foo" in subproject 'aoc'
gradlew :aoc:test -Pkotest.tags="Foo"
# Run all tests for AoC 2024
gradlew test --tests "com.fwdekker.aoc.y2024.*"
```
