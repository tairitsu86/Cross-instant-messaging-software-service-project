# microservice-instant-message
A project of connect telegram and line!  
See introduction and updata log on https://hackmd.io/sUxf18wWTHCNEO4dYwu4sg  
## Software version
Springframework 3.0.4  
JDK 17  
Docker 20.0.4  
SQL Server 2012
## How to use dockerfile?
Of course you need docker engine.  
1. Use 'mvn clean install' in command line to build your project to a jar file.  
2. Change file name to microservice-instant-message.jar, or edit dockerfile.  
3. Use 'docker image pull openjdk:17' to get the openjdk17 image  
4. Use 'docker build -t microservice-instant-message -f Dockerfile-microservice-instant-message .'  
and you should have a docker image named microservice-instant-message!  
5. Use 'docker run -d -p 8080:8080 -p 1433:1433 --add-host=host.docker.internal:host-gateway --name mim microservice-instant-message '    
than it should working on docker, port 8080 for webhook and api, port 1433 for sql server!
