FROM open-liberty
COPY liberty/server.xml /config
ADD --chown=default:root http://repo2.maven.org/maven2/com/h2database/h2/1.4.199/h2-1.4.199.jar /liberty/lib
ADD target/${artifactId}.war /liberty/usr/servers/defaultServer/dropins/