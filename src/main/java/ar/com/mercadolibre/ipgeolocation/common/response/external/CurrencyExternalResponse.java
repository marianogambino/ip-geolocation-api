package ar.com.mercadolibre.ipgeolocation.common.response.external;

public class CurrencyExternalResponse {
    private String code;

    public CurrencyExternalResponse(){}

    public CurrencyExternalResponse(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
