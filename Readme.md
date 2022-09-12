# Flyway with test containers
An example of how to use flyway and real postgres instances in JUnit tests.
The benefits of test containers is a quick and reliable way to test with a
real postgres instance.

# Flyway findings
Skipping versions and interleaving them later won't work.
Flyway will complain:
```
FlywayValidateException: Validate failed: Migrations have failed validation
Detected resolved migration not applied to database: 6. To ignore this migration, set -ignoreIgnoredMigrations=true. To allow executing this migration, set -outOfOrder=true.
Need more flexibility with validation rules? Learn more: https://rd.gt/3AbJUZE

	at org.flywaydb.core.Flyway$1.execute(Flyway.java:131)
```
Unless you want to add ignore migrations things won't work right
and adding this has consequences not understood. Strategies are:
* create a stage branch and manually merge all migrations in stage
  before merging to trunk
* make sure you don't deploy off of feature branches and just pull
  from trunk before you merge, bump your unmerged migration file
  versions and re-run tests


## Version files
Version files begin with: 
```
V__
``` 
they are the files
that must be applied in order (in both sequence of files
and time-date added) Otherwise

Flyway will complain:
```
FlywayValidateException: Validate failed: Migrations have failed validation
Detected resolved migration not applied to database: 6. To ignore this migration, set -ignoreIgnoredMigrations=true. To allow executing this migration, set -outOfOrder=true.
Need more flexibility with validation rules? Learn more: https://rd.gt/3AbJUZE

	at org.flywaydb.core.Flyway$1.execute(Flyway.java:131)
```
Unless you want to add ignore migrations things won't work right
and adding this has consequences not understood. Strategies are:
* create a stage branch and manually merge all migrations in stage
before merging to trunk
* make sure you don't deploy off of feature branches and just pull
from trunk before you merge, bump your unmerged migration file 
versions and re-run tests

### Tests
see [external db test](src/test/java/com/skf/flyway/ExternalDbTest.java)

## Repeatable migration files
Repeatable migration files are used for managing database
objects whose definition can be maintained by a single file
and does not need multiple `V__` files. These files are prepended
by:
```
R__
```
for more information see the [flyway docs](https://flywaydb.org/documentation/tutorials/repeatable.html)

# Flyway docs
https://flywaydb.org/documentation/usage/commandline/migrate
