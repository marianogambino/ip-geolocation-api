package ar.com.mercadolibre.ipgeolocation.core.manager;

import ar.com.mercadolibre.ipgeolocation.common.response.AverageDistanceResponse;
import ar.com.mercadolibre.ipgeolocation.common.response.FarthestDistanceResponse;
import ar.com.mercadolibre.ipgeolocation.common.response.IpGeolocationResponse;
import ar.com.mercadolibre.ipgeolocation.common.response.NearestDistanceResponse;

public interface IpGeolocationManager {
    IpGeolocationResponse getGeolocationInfo(String ip);

    NearestDistanceResponse getNearestDistance();

    FarthestDistanceResponse getFarthestDistance();

    AverageDistanceResponse getAverageDistance();
}
