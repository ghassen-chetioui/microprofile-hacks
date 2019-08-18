docker build -t prometheus -f Dockerfile-prometheus .
docker run --rm --name prometheus --network=host prometheus