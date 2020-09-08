
FROM payara/server-full:5.182
# This image will thus require the following environment variables viz
# ENV ADMIN_PASSWORD and ENV PAYARA_DOMAIN
# These are inherited from the base image and customed ones thus:

MAINTAINER Ayeni Femi

# The name given to the database of the application as created in the database container
ENV DB_NAME testdb

# The name given to the mysql image container as used in docker compose for linking
ENV DB_SERVER_NAME database-test

# The name to given to the created datasource as in the application
ENV DS_NAME test_datasource

COPY   ["bin/connectorJ.jar", "/opt/payara5/glassfish/domains/domain1/lib/connectorJ.jar"]

RUN echo 'create-jdbc-connection-pool --datasourceclassname com.mysql.jdbc.jdbc2.optional.MysqlDataSource --restype javax.sql.DataSource --property user=root:password=root:DatabaseName=${DB_NAME}:ServerName=${DB_SERVER_NAME}:port=3306:Url=jdbc\:mysql\://${DB_SERVER_NAME}\:3306/${DB_NAME} app-pool'> mycommands.asadmin

RUN echo 'create-jdbc-resource --connectionpoolid app-pool jdbc/${DS_NAME}'>> mycommands.asadmin


ENTRYPOINT ${PAYARA_PATH}/generate_deploy_commands.sh && \
  cat ${DEPLOY_COMMANDS} >> mycommands.asadmin && \
  ${PAYARA_PATH}/bin/asadmin start-domain -v --postbootcommandfile mycommands.asadmin ${PAYARA_DOMAIN}



COPY target/happiness-0.0.1-SNAPSHOT.war ${AUTODEPLOY_DIR}
