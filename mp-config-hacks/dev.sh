echo "Packaging application..."
mvn clean package
echo "Building image..."
docker image build -t mp-config-hacks .
echo "Starting container..."
docker container run -d --rm --name mp-config-hacks -p 8080:8080 -p 9990:9990 -v /tmp/wad-deployments:/opt/jboss/wildfly/standalone/deployments/ mp-config-hacks
echo "Watch and Deploy..."
java -Dmaven.home=/home/cgh/.sdkman/candidates/maven/3.5.4/ -jar ~/.wad/wad.jar /tmp/wad-deployments
echo "Stopping container..."
docker container stop mp-config-hacks