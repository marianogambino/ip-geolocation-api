#!/bin/sh

if ! [ -x "$(command -v jq)" ]; then
	 echo 'Error: jq is not installed. Please install jq -> sudo apt install jq' >&2
     exit 1
fi


response=$(curl --location --request GET 'http://localhost:8080/ip-geolocation-api/api/nearest-distance')

message=$(echo "$response" | jq -r '.message')

if [ -z "$message" ] || [ "$message" = null ] ; then
    echo ''
else
    echo ''
    echo "$message"
    echo '' && exit 1;
fi

echo ''
echo '::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::'
echo ''

country=$(echo "$response" | jq -r '.country')
echo 'Pais:' "$country"

nearest_distance=$(echo "$response" | jq -r '.nearest_distance')
echo 'Distancia mas cercana:' "$nearest_distance"

echo ''
echo '::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::'
echo ''