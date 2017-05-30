#!/bin/sh

# create database
echo "Create database..."

psql --username "$POSTGRES_USER" <<EOSQL
CREATE DATABASE testbed;
EOSQL

psql --username "$POSTGRES_USER" testbed <<EOSQL
CREATE EXTENSION postgis;
CREATE EXTENSION postgis_topology;
EOSQL

# Run other SQL scripts
echo "Run SQL scripts preparing database..."
for file in /opt/sql/*.sql
do
  if [ -e "$file" ];
  then
    psql --username "$POSTGRES_USER" testbed < "$file"
  fi
done
for file in /opt/sql/*.sql.gz
do
  if [ -e "$file" ];
  then
    gunzip -c "$file" | psql --username "$POSTGRES_USER" testbed
  fi
done

# restore text dump
echo "Restore any text based data dumps..."
for file in /opt/sql/*.dump
do
  if [ -e "$file" ];
  then
    psql --username "$POSTGRES_USER" testbed < "$file"
  fi
done
for file in /opt/sql/*.dump.gz
do
  if [ -e "$file" ];
  then
    gunzip -c "$file" | psql --username "$POSTGRES_USER" testbed
  fi
done

# restore binary dump schema
echo "Restore any binary schema dumps..."
for file in /opt/pg_dump/*.schema
do
  if [ -e "$file" ];
  then
    gosu postgres pg_restore --schema-only -d testbed -O -x "$file"
  fi
done

# restore binary dump data & schema
echo "Restore any binary data dumps..."
for file in /opt/pg_dump/*.dump
do
  if [ -e "$file" ];
  then
    gosu postgres pg_restore -d testbed -O -x "$file"
  fi
done

