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

# Tests
see [external db test](src/test/java/com/skf/flyway/ExternalDbTest.java)

# Flyway docs
https://flywaydb.org/documentation/usage/commandline/migrate
