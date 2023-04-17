package ar.com.mercadolibre.ipgeolocation.domain.repository;

import ar.com.mercadolibre.ipgeolocation.domain.entity.CountryStatisticsDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CountryStatisticsDataRepository extends JpaRepository<CountryStatisticsDataEntity, Long> {
    Optional<CountryStatisticsDataEntity> findByName(String countryName);
}
