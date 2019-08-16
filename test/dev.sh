echo "Packaging application..."
mvn clean package
echo "Building image..."
docker image build -t test .
echo "Starting container..."
docker container run -d --rm --name test -p 9080:9080 -v /tmp/wad-deployments:/liberty/usr/servers/defaultServer/dropins/ test
echo "Watch and Deploy..."
java -Dmaven.home=/home/cgh/.sdkman/candidates/maven/3.5.4/ -jar ~/.wad/wad.jar /tmp/wad-deployments
echo "Stopping container..."
docker container stop test