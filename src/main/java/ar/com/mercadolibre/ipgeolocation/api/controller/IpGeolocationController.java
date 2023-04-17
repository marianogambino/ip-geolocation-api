package ar.com.mercadolibre.ipgeolocation.api.controller;


import ar.com.mercadolibre.ipgeolocation.common.constants.AttributesValidation;
import ar.com.mercadolibre.ipgeolocation.common.constants.HttpConstant;
import ar.com.mercadolibre.ipgeolocation.common.exception.ErrorCode;
import ar.com.mercadolibre.ipgeolocation.common.response.AverageDistanceResponse;
import ar.com.mercadolibre.ipgeolocation.common.response.FarthestDistanceResponse;
import ar.com.mercadolibre.ipgeolocation.common.response.IpGeolocationResponse;
import ar.com.mercadolibre.ipgeolocation.common.response.NearestDistanceResponse;
import ar.com.mercadolibre.ipgeolocation.common.route.Route;
import ar.com.mercadolibre.ipgeolocation.core.manager.IpGeolocationManager;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@Tag(name = "IP GEOLOCATION API")
@RestController
@RequestMapping(Route.IP_GEOLOCATION_BASE_PATH)
@Validated
public class IpGeolocationController {

    private IpGeolocationManager ipGeolocationManager;

    public IpGeolocationController(IpGeolocationManager ipGeolocationManager) {
        this.ipGeolocationManager = ipGeolocationManager;
    }

    @Operation(description = "Get IP Geolocation Information")
    @ApiResponse(
            responseCode = "200",
            description = "according to your ip returns the information of a country",
            content = {
                    @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = IpGeolocationResponse.class)
                    )
                }
            )
    @GetMapping(Route.IP_GEOLOCATION_GET_INFO)
    public IpGeolocationResponse getIpGeolocationInfo(
            @Valid
            @RequestParam(HttpConstant.PARAM_IP)
            @NotNull
            @Pattern(
                regexp = AttributesValidation.IP,
                message = ErrorCode.INVALID_IP_FORMAT)
                    String ip){

        return ipGeolocationManager.getGeolocationInfo(ip);
    }

    @Operation(description = "Get nearest distance")
    @ApiResponse(
            responseCode = "200",
            description = "Returns the nearest distance",
            content = {
                    @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = IpGeolocationResponse.class)
                    )
            }
    )
    @GetMapping(Route.IP_GEOLOCATION_GET_NEAREST_DISTANCE)
    public NearestDistanceResponse getNearestDistance(){
        return ipGeolocationManager.getNearestDistance();
    }

    @Operation(description = "Get farthest distance")
    @ApiResponse(
            responseCode = "200",
            description = "Returns the farthest distance",
            content = {
                    @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = IpGeolocationResponse.class)
                    )
            }
    )
    @GetMapping(Route.IP_GEOLOCATION_GET_FARTHEST_DISTANCE)
    public FarthestDistanceResponse getFarthestDistance(){
        return ipGeolocationManager.getFarthestDistance();
    }

    @Operation(description = "Get average distance")
    @ApiResponse(
            responseCode = "200",
            description = "Returns the farthest distance",
            content = {
                    @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = IpGeolocationResponse.class)
                    )
            }
    )
    @GetMapping(Route.IP_GEOLOCATION_GET_AVERAGE_DISTANCE)
    public AverageDistanceResponse getAverageDistance(){
        return ipGeolocationManager.getAverageDistance();
    }
}
