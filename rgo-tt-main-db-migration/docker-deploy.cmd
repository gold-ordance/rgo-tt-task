docker-compose up -d
docker rmi michisig/tt-task-db:%1
docker container commit native-db michisig/tt-task-db:%1
docker image push michisig/tt-task-db:%1
docker-compose stop