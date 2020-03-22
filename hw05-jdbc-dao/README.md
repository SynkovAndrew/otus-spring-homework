###How to run application

The application uses postgres database. It may be started with docker. 
It's not necessary to start postgres for tests cause they use embedded H2 database. 
```
docker pull postgres
docker run --rm -e POSTGRES_PASSWORD=postgres -d -p 5432:5432 postgres
```
Then build application with maven and run it.
```
mvn clean package
```


