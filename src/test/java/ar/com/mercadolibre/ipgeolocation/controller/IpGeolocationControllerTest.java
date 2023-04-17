package ar.com.mercadolibre.ipgeolocation.controller;

import ar.com.mercadolibre.ipgeolocation.common.constants.HttpConstant;
import ar.com.mercadolibre.ipgeolocation.common.route.Route;
import ar.com.mercadolibre.ipgeolocation.common.util.CacheUtil;
import ar.com.mercadolibre.ipgeolocation.common.util.LocalDateTimeWrapper;
import ar.com.mercadolibre.ipgeolocation.configuration.TestRedisConfiguration;
import ar.com.mercadolibre.ipgeolocation.domain.entity.CountryStatisticsDataEntity;
import ar.com.mercadolibre.ipgeolocation.domain.repository.CountryStatisticsDataRepository;
import ar.com.mercadolibre.ipgeolocation.BaseTest;
import ar.com.mercadolibre.ipgeolocation.util.MockApiLayerClient;
import ar.com.mercadolibre.ipgeolocation.util.MockIpApiClient;
import ar.com.mercadolibre.ipgeolocation.util.MockIpGeolocationClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockserver.client.MockServerClient;
import org.mockserver.junit.jupiter.MockServerSettings;
import org.mockserver.model.HttpStatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.time.LocalDateTime;
import java.util.List;

@MockServerSettings(ports = 1080, perTestSuite = true)
@SpringBootTest(classes = TestRedisConfiguration.class)
public class IpGeolocationControllerTest extends BaseTest {

    @Autowired
    private CountryStatisticsDataRepository repository;

    @MockBean
    LocalDateTimeWrapper localDateTimeWrapper;


    @Test
    public void healthCheckTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/health"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @SqlGroup({
            @Sql(scripts = "/db/queries/delete_country_statistics_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    })
    public void given_ip_shouldReturn_geolocation_info_and_success(MockServerClient mockSever) throws Exception {
        Mockito.when(localDateTimeWrapper.getLocalDateTimeNow()).thenReturn(LocalDateTime.of(2023, 4, 16, 15,39,35));
        Mockito.when(localDateTimeWrapper.getLocalDateTimeNow("America/Halifax")).thenReturn(LocalDateTime.of(2023, 4, 16, 15,39,35));

        String response = fileFromResources("response/ipGeolocation_api_response.json");

        String ipGeolocationClientExternalResponse = fileFromResources("response/external/ipGeolocation_external_response.json");
        MockIpGeolocationClient.getIpGeolocationInfo(HttpStatusCode.OK_200, IP, ipGeolocationClientExternalResponse, mockSever);

        String ipApiClientExternalResponse = fileFromResources("response/external/ipApi_external_response.json");
        MockIpApiClient.getLanguages(HttpStatusCode.OK_200, IP, ipApiClientExternalResponse, mockSever);

        String apiLayerClientExternalResponse = fileFromResources("response/external/apiLayer_external_response.json");
        MockApiLayerClient.getCurrencyExchangeRates(HttpStatusCode.OK_200, "USD", "CAD", apiLayerClientExternalResponse, mockSever);

        mvc.perform(
                    MockMvcRequestBuilders.get(Route.IP_GEOLOCATION_BASE_PATH.concat(Route.IP_GEOLOCATION_GET_INFO))
                        .queryParam(HttpConstant.PARAM_IP, IP)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response))
                .andDo((handler) -> {
                    List<CountryStatisticsDataEntity> entities = repository.findAll();
                    Assertions.assertEquals(1, entities.size());

                    CountryStatisticsDataEntity entity = entities.get(0);
                    Assertions.assertNotNull(entity.getName());
                    Assertions.assertTrue(entity.getName().contains("Canada"));
                    Assertions.assertEquals(1, entity.getInvocations());
                });
    }


    @Test
    @SqlGroup({
            @Sql(scripts = "/db/queries/delete_country_statistics_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(scripts = "/db/queries/insert_country_statistics_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    })
    public void when_ip_and_country_info_exist_in_cache_shouldReturn_geolocation_info_and_success(MockServerClient mockSever) throws Exception {
        Mockito.when(localDateTimeWrapper.getLocalDateTimeNow()).thenReturn(LocalDateTime.of(2023, 4, 16, 15,39,35));
        Mockito.when(localDateTimeWrapper.getLocalDateTimeNow("America/Halifax")).thenReturn(LocalDateTime.of(2023, 4, 16, 15,39,35));

        String response = fileFromResources("response/ipGeolocation_api_response.json");

        String countryCacheResponse = fileFromResources("response/cache/ipGeolocation_api_response.json");
        CacheUtil.getCacheProvider().set(IP, "Canada");
        CacheUtil.getCacheProvider().set("Canada", countryCacheResponse);

        mvc.perform(
                        MockMvcRequestBuilders.get(Route.IP_GEOLOCATION_BASE_PATH.concat(Route.IP_GEOLOCATION_GET_INFO))
                                .queryParam(HttpConstant.PARAM_IP, IP)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response))
                .andDo((handler) -> {
                    List<CountryStatisticsDataEntity> entities = repository.findAll();
                    Assertions.assertEquals(3, entities.size());

                    CountryStatisticsDataEntity entity = entities.get(0);
                    Assertions.assertNotNull(entity.getName());
                    Assertions.assertTrue(entity.getName().contains("Canada"));
                    Assertions.assertEquals(11, entity.getInvocations());
                });
    }

    @Test
    @SqlGroup({
            @Sql(scripts = "/db/queries/delete_country_statistics_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(scripts = "/db/queries/insert_country_statistics_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    })
    public void when_only_country_info_exist_in_cache_shouldReturn_geolocation_info_and_success(MockServerClient mockSever) throws Exception {
        Mockito.when(localDateTimeWrapper.getLocalDateTimeNow()).thenReturn(LocalDateTime.of(2023, 4, 16, 15,39,35));
        Mockito.when(localDateTimeWrapper.getLocalDateTimeNow("America/Halifax")).thenReturn(LocalDateTime.of(2023, 4, 16, 15,39,35));

        String response = fileFromResources("response/ipGeolocation_api_response.json");

        String countryCacheResponse = fileFromResources("response/cache/ipGeolocation_api_response.json");
        CacheUtil.getCacheProvider().set("Canada", countryCacheResponse);

        String ipGeolocationClientExternalResponse = fileFromResources("response/external/ipGeolocation_external_response.json");
        MockIpGeolocationClient.getIpGeolocationInfo(HttpStatusCode.OK_200, IP, ipGeolocationClientExternalResponse, mockSever);


        mvc.perform(
                        MockMvcRequestBuilders.get(Route.IP_GEOLOCATION_BASE_PATH.concat(Route.IP_GEOLOCATION_GET_INFO))
                                .queryParam(HttpConstant.PARAM_IP, IP)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response))
                .andDo((handler) -> {
                    List<CountryStatisticsDataEntity> entities = repository.findAll();
                    Assertions.assertEquals(3, entities.size());

                    CountryStatisticsDataEntity entity = entities.get(0);
                    Assertions.assertNotNull(entity.getName());
                    Assertions.assertTrue(entity.getName().contains("Canada"));
                    Assertions.assertEquals(11, entity.getInvocations());
                });
    }


    @Test
    @SqlGroup({
            @Sql(scripts = "/db/queries/delete_country_statistics_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    })
    public void given_ip_shouldReturn_error_when_ip_geolocation_client_fails(MockServerClient mockSever) throws Exception {
        Mockito.when(localDateTimeWrapper.getLocalDateTimeNow()).thenReturn(LocalDateTime.of(2023, 4, 16, 15,39,35));

        String ipGeolocationClientExternalResponse = fileFromResources("response/external/ipGeolocation_external_response.json");
        MockIpGeolocationClient.getIpGeolocationInfo(HttpStatusCode.BAD_GATEWAY_502, IP, ipGeolocationClientExternalResponse, mockSever);

        mvc.perform(
                        MockMvcRequestBuilders.get(Route.IP_GEOLOCATION_BASE_PATH.concat(Route.IP_GEOLOCATION_GET_INFO))
                                .queryParam(HttpConstant.PARAM_IP, IP)
                )
                .andExpect(MockMvcResultMatchers.status().isFailedDependency())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("ip.geolocation.client.failed_dependency"));
    }


    @Test
    @SqlGroup({
            @Sql(scripts = "/db/queries/delete_country_statistics_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    })
    public void given_ip_shouldReturn_error_when_ip_api_client_fails(MockServerClient mockSever) throws Exception {
        Mockito.when(localDateTimeWrapper.getLocalDateTimeNow()).thenReturn(LocalDateTime.of(2023, 4, 16, 15,39,35));

        String ipGeolocationClientExternalResponse = fileFromResources("response/external/ipGeolocation_external_response.json");
        MockIpGeolocationClient.getIpGeolocationInfo(HttpStatusCode.OK_200, IP, ipGeolocationClientExternalResponse, mockSever);

        String ipApiClientExternalResponse = fileFromResources("response/external/ipApi_external_response.json");
        MockIpApiClient.getLanguages(HttpStatusCode.SERVICE_UNAVAILABLE_503, IP, ipApiClientExternalResponse, mockSever);

        mvc.perform(
                        MockMvcRequestBuilders.get(Route.IP_GEOLOCATION_BASE_PATH.concat(Route.IP_GEOLOCATION_GET_INFO))
                                .queryParam(HttpConstant.PARAM_IP, IP)
                )
                .andExpect(MockMvcResultMatchers.status().isFailedDependency())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("ip.api.client.failed_dependency"));
    }

    @Test
    @SqlGroup({
            @Sql(scripts = "/db/queries/delete_country_statistics_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    })
    public void given_ip_shouldReturn_error_when_api_layer_client_fails(MockServerClient mockSever) throws Exception {
        Mockito.when(localDateTimeWrapper.getLocalDateTimeNow()).thenReturn(LocalDateTime.of(2023, 4, 16, 15,39,35));

        String ipGeolocationClientExternalResponse = fileFromResources("response/external/ipGeolocation_external_response.json");
        MockIpGeolocationClient.getIpGeolocationInfo(HttpStatusCode.OK_200, IP, ipGeolocationClientExternalResponse, mockSever);

        String ipApiClientExternalResponse = fileFromResources("response/external/ipApi_external_response.json");
        MockIpApiClient.getLanguages(HttpStatusCode.OK_200, IP, ipApiClientExternalResponse, mockSever);

        String apiLayerClientExternalResponse = fileFromResources("response/external/apiLayer_external_response.json");
        MockApiLayerClient.getCurrencyExchangeRates(HttpStatusCode.GATEWAY_TIMEOUT_504, "USD", "CAD", apiLayerClientExternalResponse, mockSever);

        mvc.perform(
                        MockMvcRequestBuilders.get(Route.IP_GEOLOCATION_BASE_PATH.concat(Route.IP_GEOLOCATION_GET_INFO))
                                .queryParam(HttpConstant.PARAM_IP, IP)
                )
                .andExpect(MockMvcResultMatchers.status().isFailedDependency())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("api.layer.client.failed_dependency"));
    }

    @Test
    @SqlGroup({
            @Sql(scripts = "/db/queries/delete_country_statistics_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(scripts = "/db/queries/insert_country_statistics_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    })
    public void get_farthest_distance_shouldReturn_success(MockServerClient mockSever) throws Exception {
        String response = fileFromResources("response/farthest_distance_response.json");

        mvc.perform(
                        MockMvcRequestBuilders.get(Route.IP_GEOLOCATION_BASE_PATH.concat(Route.IP_GEOLOCATION_GET_FARTHEST_DISTANCE))
                                .queryParam(HttpConstant.PARAM_IP, IP)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Test
    @SqlGroup({
            @Sql(scripts = "/db/queries/delete_country_statistics_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(scripts = "/db/queries/insert_country_statistics_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    })
    public void get_nearest_distance_shouldReturn_success(MockServerClient mockSever) throws Exception {
        String response = fileFromResources("response/nearest_distance_response.json");

        mvc.perform(
                        MockMvcRequestBuilders.get(Route.IP_GEOLOCATION_BASE_PATH.concat(Route.IP_GEOLOCATION_GET_NEAREST_DISTANCE))
                                .queryParam(HttpConstant.PARAM_IP, IP)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Test
    @SqlGroup({
            @Sql(scripts = "/db/queries/delete_country_statistics_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(scripts = "/db/queries/insert_country_statistics_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    })
    public void get_average_distance_shouldReturn_success(MockServerClient mockSever) throws Exception {
        String response = fileFromResources("response/average_distance_response.json");

        mvc.perform(
                        MockMvcRequestBuilders.get(Route.IP_GEOLOCATION_BASE_PATH.concat(Route.IP_GEOLOCATION_GET_AVERAGE_DISTANCE))
                                .queryParam(HttpConstant.PARAM_IP, IP)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }


    @Override
    public void cleanData() {
        CacheUtil.deleteKey(BaseTest.IP);
        CacheUtil.deleteKey(BaseTest.COUNTRY);
    }

}
