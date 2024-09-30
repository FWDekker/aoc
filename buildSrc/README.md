# AoC: buildSrc
Common build logic for all other subprojects.

Exposes a [convention plugin](https://docs.gradle.org/current/userguide/sharing_build_logic_between_subprojects.html) that can be imported in the other subprojects by writing
```kotlin
plugins { id("challenges.common") }
```
in the `build.gradle.kts`.
Structure taken [from this example project](https://docs.gradle.org/current/samples/sample_sharing_convention_plugins_with_build_logic.html).
