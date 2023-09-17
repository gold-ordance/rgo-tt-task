docker-compose up -d
docker rmi michisig/tt-task-db:%1
docker container commit task-db michisig/tt-task-db:%1
docker image push michisig/tt-task-db:%1
docker-compose stop
docker rm task-db
docker rmi task-db:latest