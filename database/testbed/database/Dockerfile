FROM mdillon/postgis:9.4

RUN mkdir -p /docker-entrypoint-initdb.d
COPY ./testbed-initdb.sh /docker-entrypoint-initdb.d/testbed-initdb.sh

COPY ./sql /opt/sql
RUN chown -R postgres:postgres /opt/sql

COPY ./pg_dump /opt/pg_dump
RUN chown -R postgres:postgres /opt/pg_dump
