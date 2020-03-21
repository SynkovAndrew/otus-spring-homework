###How to run application

```
docker pull postgres
docker run --rm -e POSTGRES_PASSWORD=postgres -d -p 5432:5432 postgres
```