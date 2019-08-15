echo "Packaging application..."
mvn clean package
echo "Building image..."
docker image build -t ${artifactId} .
echo "Starting container..."
docker container run -d --rm --name ${artifactId} -p 8080:8080 -v /tmp/wad-deployments:/opt/jboss/wildfly/standalone/deployments/ ${artifactId}
echo "Watch and Deploy..."
java -Dmaven.home=/home/cgh/.sdkman/candidates/maven/3.5.4/ -jar ~/.wad/wad.jar /tmp/wad-deployments
echo "Stopping container..."
docker container stop ${artifactId}