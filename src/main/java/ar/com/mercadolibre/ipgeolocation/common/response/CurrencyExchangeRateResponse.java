package ar.com.mercadolibre.ipgeolocation.common.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;


@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CurrencyExchangeRateResponse {
    private String currencyExchange;
    private String currentDate;
    private CurrencyRates currencyRates;

    public CurrencyExchangeRateResponse(String currencyExchange, String currentDate, CurrencyRates currencyRates) {
        this.currencyExchange = currencyExchange;
        this.currentDate = currentDate;
        this.currencyRates = currencyRates;
    }

    public String getCurrencyExchange() {
        return currencyExchange;
    }

    public void setCurrencyExchange(String currencyExchange) {
        this.currencyExchange = currencyExchange;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    public CurrencyRates getCurrencyRates() {
        return currencyRates;
    }

    public void setCurrencyRates(CurrencyRates currencyRates) {
        this.currencyRates = currencyRates;
    }


}
