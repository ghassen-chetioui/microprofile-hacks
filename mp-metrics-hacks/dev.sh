. ../export-env-vars
echo "Packaging application..."
mvn clean package
echo "Building image..."
docker image build -t mp-metrics-hacks .
echo "Starting container..."
docker container run -d --rm --name mp-metrics-hacks -p 9080:9080 -p 9443:9443 -v /tmp/wad-deployments:/liberty/usr/servers/defaultServer/dropins/ mp-metrics-hacks
echo "Watch and Deploy..."
java -Dmaven.home=${M2_HOME} -jar ${WAD_HOME}/wad.jar /tmp/wad-deployments
echo "Stopping container..."
docker container stop mp-metrics-hacks