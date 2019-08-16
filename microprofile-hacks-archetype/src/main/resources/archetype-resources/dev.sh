echo "Packaging application..."
mvn clean package
echo "Building image..."
docker image build -t ${artifactId} .
echo "Starting container..."
docker container run -d --rm --name ${artifactId} -p 9080:9080 -v /tmp/wad-deployments:/liberty/usr/servers/defaultServer/dropins/ ${artifactId}
echo "Watch and Deploy..."
java -Dmaven.home=/home/cgh/.sdkman/candidates/maven/3.5.4/ -jar ~/.wad/wad.jar /tmp/wad-deployments
echo "Stopping container..."
docker container stop ${artifactId}