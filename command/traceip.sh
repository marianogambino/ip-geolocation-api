#!/bin/sh

if ! [ -x "$(command -v jq)" ]; then
	 echo 'Error: jq is not installed. Please install jq -> sudo apt install jq' >&2
     exit 1
fi

if [ -z "$1" ] ; then
  echo "First parameter needed!" && exit 1;
fi

ip="$1"
response=$(curl --location --request GET 'http://localhost:8080/ip-geolocation-api/api/geolocation?ip='"$ip")

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

echo 'IP: '"$ip"

country_name=$(echo "$response" | jq -r '.country_name')
echo 'Pais:' "$country_name"

country_code=$(echo "$response" | jq -r '.country_code')
echo 'ISO code:' "$country_code"

current_date=$(echo "$response" | jq -r '.current_date')
echo 'Fecha Actual:' "$current_date"


languagesCountry=$(echo "$response" | jq -r '.languages' )


languages=''
for row in $(echo "${languagesCountry}" | jq -r '.[] | @base64'); do
    _jq() {
     	echo ${row} | base64 --decode | jq -r ${1}
	}
   languages="${languages} $(echo $(_jq '.name')) /"
done

echo 'Idiomas:' "$languages"

currency=$(echo "$response" | jq -r '.currency_exchange_rate.currency_rates.currency')
echo 'Moneda:' "$currency"

currency_rates=$(echo "$response" | jq -r '.currency_exchange_rate.currency_rates.price')
echo 'Cotizacion: 1 USD ->' "$currency_rates"' '"$currency"

time=$(echo "$response" | jq -r '.time')
echo 'Hora en' "$country_name"':' "$time"

latitude=$(echo "$response" | jq -r '.latitude')
longitude=$(echo "$response" | jq -r '.longitude')


estimated_distance_kms=$(echo "$response" | jq -r '.estimated_distance_kms')
echo 'Distancia Estimada:' "$estimated_distance_kms" 'kms (-34, -64) a' '('"$latitude"','"$longitude"')'

echo ''
echo '::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::'
echo ''