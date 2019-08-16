FROM open-liberty
COPY liberty/server.xml /config
ADD http://repo2.maven.org/maven2/com/h2database/h2/1.4.199/h2-1.4.199.jar /liberty/lib
ADD target/mp-health-hacks.war /liberty/usr/servers/defaultServer/dropins/
#FROM jboss/wildfly:15.0.1.Final
#ADD target/mp-health-hacks.war /opt/jboss/wildfly/standalone/deployments/
#RUN /opt/jboss/wildfly/bin/add-user.sh admin Admin#70365 --silent
#CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0"]