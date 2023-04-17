package ar.com.mercadolibre.ipgeolocation.common.response.external.apiLayer;

import java.math.BigDecimal;
import java.util.Map;

public class CurrencyExchangeRateExternalResponse {
    private Map<String, BigDecimal> rates;

    public CurrencyExchangeRateExternalResponse() {
    }

    public CurrencyExchangeRateExternalResponse(Map<String, BigDecimal> rates) {
        this.rates = rates;
    }

    public Map<String, BigDecimal> getRates() {
        return rates;
    }

    public void setRates(Map<String, BigDecimal> rates) {
        this.rates = rates;
    }
}
