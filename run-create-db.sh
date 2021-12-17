#!/bin/sh

export PGPASSWORD=sa
psql -h localhost -U postgres -f create-db.sql

# to connect via command line
# psql -h localhost -U postgres

# other helpful psql commands:
#  \l  -list databases
# \c   -switch db. eg \c myDbName
# \dt  -show tables after switched to a db
