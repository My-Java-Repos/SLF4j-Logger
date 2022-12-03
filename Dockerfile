FROM docker.com/demo/base-java11-image:latest-master

# Various Centos packages in the original image, including the JDK, have vulnerabilities.
# Running 'yum update' gets us the latest security patches for everything.
# USER root
# RUN yum update -y && yum clean all
USER jboss

ENV JAVA_APP_DIR=/deployments
EXPOSE 8080 8778 9779
COPY target/*.jar /deployments/

CMD ["/deployments/run-java.sh"]
