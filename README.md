# Circle of Trust Service
A Vert.x Backend API for the `Circle of Trust` application

Build
------
run `./gradlew build`

Test
----
run `./gradlew test`

Run Service
-----------
run `./gradlew run`. This will start the service on port 8080 in listening mode.
Any changes made would automatically restart the service.

Package ShadowJar
-----------------
run `./gradlew clean build shadowJar` and the jar would appear under `build/libs`
