# Calculator service

A small Spring Boot service that adds numbers.

```
GET /calc/1+2+3   ->  {"expression":"1+2+3","result":6}
```

## Run

```bash
mvn spring-boot:run
curl localhost:8080/calc/1+2+3
```

## Test

```bash
mvn test
```
