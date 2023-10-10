# Peticiones Curl:

## Recuperar un empleado por su ID
curl -X GET http://localhost:8080/employee/{id} -H 'X-API-KEY: Baeldung'

## Actualizar un empleado por su ID
curl -X PUT http://localhost:8080/employee/{id} -H 'X-API-KEY: Baeldung' -H 'Content-Type: application/json' -d '{
"firstName": "NuevoNombre",
"lastName": "NuevoApellido",
"salary": 60000,
"hireDate": "2022-01-01",
"position": "NuevoPuesto",
"department": "NuevoDepartamento"
}'

## Eliminar un empleado por su ID
curl -X DELETE http://localhost:8080/employee/{id} -H 'X-API-KEY: Baeldung'

## Agregar empleado
curl -X POST http://localhost:8080/employee/add -H 'X-API-KEY: Baeldung' -H 'Content-Type: application/json' -d '{
"firstName": "Fernando",
"lastName": "Gutiérrez",
"salary": 60000,
"hireDate": "2022-01-01",
"position": "Desarrollador",
"department": "TI"
}'