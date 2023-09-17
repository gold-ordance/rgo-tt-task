FROM postgres:15

RUN mkdir -p /usr/local/task/pgdata; \
    mkdir -p /usr/local/task/pgindex; \

    chown -R postgres:postgres /usr/local; \
    chmod -R u+rwx /usr/local

ENV POSTGRES_USER=postgres
ENV POSTGRES_PASSWORD=postgres
ENV POSTGRES_DB=task

RUN mkdir -p /var/lib/postgresql-static/data
ENV PGDATA /var/lib/postgresql-static/data