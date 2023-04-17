# Mercado Libre - Geolocalización de IPs - Challenge


## Solución
#### Se desarrollo un microservicio en Java 17, con SprinBoot, Postgresql como Base de datos y Redis para la utilización de cache en memoria.
#### Ademas el servicio consume las siguientes api's para obtener la infomación solicitada en el challenge:
        
    IP GEOLOCATION API
    https://app.ipgeolocation.io/

    API LAYER
    https://api.apilayer.com

    IP API
    http://api.ipapi.com


## Antes de iniciar/levantar el microservicio desde docker debemos ejecutar:
#### Desde el root path del proyecto ejecutar el siguiente comando:
> mvn clean install

## Iniciar el microservicio en DOCKER
### Generar imagen e instalar en docker
#### Primero debemos ejecutar el siguiente comando desde root path del proyecto
> docker build --tag=ip-geolocation-api:latest .

### Iniciar aplicación con docker-compose
#### Desde root path del proyecto ejecutar:
> docker-compose up

### Antes de iniciar la prueba instalar el comando jq

> sudo apt install jq

### Desde la linea de comando, pararse en el root path del proyecto e ir a la carpeta "command"
> cd command

### Otorgar permisos
> chmod 755 traceip.sh

> chmod 755 getNearestDistance.sh

> chmod 755 getFarthestDistance.sh

> chmod 755 getAverageDistance.sh

### Ejecutar el comando traceIp:
> ./traceip.sh 100.42.240.0

### Para probar el resto de los comandos:
> ./getNearestDistance.sh

> ./getFarthestDistance.sh

> ./getAverageDistance.sh


## Para detener el Micoservicio
### Desde root path del proyecto ejecutar:
> docker-compose down


# Información Adicional
## Postman Collection
#### Una vez levantado el microservicio, se puede probar importando el archivo .json (collection) en Postman:
> /postman/collection/IP_GEOLOCATION_API.postman_collection.json

# Documentación del servicio con OpenApi (Swagger)
> http://localhost:8080/ip-geolocation-api/swagger-ui/index.html