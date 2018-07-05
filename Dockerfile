FROM tomcat:8.5

COPY webapp/target/*.war /usr/local/tomcat/webapps/pizzeria.war

EXPOSE 8080:8080