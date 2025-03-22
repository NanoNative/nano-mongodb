# Nano MongoDB Service

MongoDB Service for [Nano Native](https://github.com/NanoNative/nano)

### Requirements

* `Graalvm` >= 21.0.0 in `$JAVA_HOME` environment variable

### TODO
- [ ] MongoDbService is created
- [ ] MongoDbClient is initialised at `start()`
- [ ] MongoDbClient is closed on `stop()`
- [ ] MongoDbClient is can be configured
- [ ] MongoDBService reacts on mongoDB events `onEvent()`
  - [ ] MongoDBService reacts on mongoDB events can be `JSON String`, `TypeMap` or `MongoDB Query` Objects
  - [ ] MongoDBService returns TypeMap or specific Mongo Objects? TBD
- [ ] Tests are executed without mocks - real nano - real embedded mongodb (e.g. flapdoodle)
- [ ] Add human-readable java docs
- [ ] Check if mongodb lib works in a native executable

### TODO (Yuna)
- [ ] CI/CD Pipeline automatically updates project
- [ ] CI/CD Pipeline automatically keeps the version with nano in sync: rc-xx versions until nano releases its next version.
- [ ] Release to maven central
