package com.abelium.inatrace.components.currencies;

import com.abelium.inatrace.components.codebook.currencies.CurrencyTypeService;
import com.abelium.inatrace.components.common.BaseService;
import com.abelium.inatrace.components.currencies.api.ApiCurrencyRatesResponse;
import com.abelium.inatrace.components.currencies.api.ApiCurrencySymbolsResponse;
import com.abelium.inatrace.db.entities.codebook.CurrencyType;
import com.abelium.inatrace.db.entities.currencies.CurrencyPair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CurrencyService extends BaseService {

    private static final String CURRENCY = "currency";

    @Autowired
    private CurrencyTypeService currencyTypeService;

    @Value("${INAtrace.exchangerate.apiKey}")
    private String apiKey;

    public BigDecimal convertFromEur(String to, BigDecimal value) {
        return value.multiply(em.createNamedQuery("CurrencyPair.latestRate", BigDecimal.class).setParameter(CURRENCY, to).getResultList().get(0));
    }

    public BigDecimal convertToEur(String from, BigDecimal value) {
        return value.divide(em.createNamedQuery("CurrencyPair.latestRate", BigDecimal.class).setParameter(CURRENCY, from).getResultList().get(0), 6, RoundingMode.HALF_UP);
    }

    @Transactional
    public BigDecimal convertFromEurAtDate(String to, BigDecimal value, Date date) {
        List<BigDecimal> rates = rateAtDateQuery(to, date).getResultList();
        BigDecimal rate;
        if (rates.isEmpty()) {
            fetchRates(date);
            rate = rateAtDateQuery(to, date).getSingleResult();
        } else {
            rate = rates.get(0);
        }
        return value.multiply(rate);
    }

    @Transactional
    public BigDecimal convertToEurAtDate(String from, BigDecimal value, Date date) {
        List<BigDecimal> rates = rateAtDateQuery(from, date).getResultList();
        BigDecimal rate;
        if (rates.isEmpty()) {
            fetchRates(date);
            rate = rateAtDateQuery(from, date).getSingleResult();
        } else {
            rate = rates.get(0);
        }
        return value.divide(rate, 6, RoundingMode.HALF_UP);
    }

    public BigDecimal convert(String from, String to, BigDecimal value) {
        if (from.equals(to)) {
            return value;
        } else if ("EUR".equals(from)) {
            return this.convertFromEur(to, value);
        } else if ("EUR".equals(to)) {
            return this.convertToEur(from, value);
        } else {
            return this.convertFromEur(to, this.convertToEur(from, value));
        }
    }

    @Transactional
    public BigDecimal convertAtDate(String from, String to, BigDecimal value, Date date) {
        if (from.equals(to)) {
            return value;
        } else if ("EUR".equals(from)) {
            return this.convertFromEurAtDate(to, value, date);
        } else if ("EUR".equals(to)) {
            return this.convertToEurAtDate(from, value, date);
        } else {
            return this.convertFromEurAtDate(to, this.convertToEurAtDate(from, value, date), date);
        }
    }

    @Transactional
    @Scheduled(cron = "0 1 0 * * *")
    @EventListener(ApplicationReadyEvent.class)
    public void updateCurrencies() {
        WebClient webClientSymbols = WebClient.create("http://api.exchangeratesapi.io/v1/symbols?access_key=" + apiKey);
        ApiCurrencySymbolsResponse apiCurrencySymbolsResponse = webClientSymbols
                .get()
                .accept(MediaType.APPLICATION_JSON)
                .retrieve().bodyToMono(ApiCurrencySymbolsResponse.class)
                .doOnError(Throwable::printStackTrace)
                .onErrorReturn(new ApiCurrencySymbolsResponse())
                .block();
        if (apiCurrencySymbolsResponse != null && apiCurrencySymbolsResponse.isSuccess()) {
            Map<String, String> symbols = apiCurrencySymbolsResponse.getSymbols();
            for (Map.Entry<String, String> entry : symbols.entrySet()) {
                if (em.createNamedQuery("CurrencyType.getCurrencyTypeByCode").setParameter("code", entry.getKey()).getResultList().isEmpty()) {
                    CurrencyType currencyType = new CurrencyType();
                    currencyType.setCode(entry.getKey());
                    currencyType.setLabel(entry.getValue());
                    currencyType.setEnabled(Boolean.FALSE);
                    em.persist(currencyType);
                }
            }
        }

        WebClient webClientRates = WebClient.create("http://api.exchangeratesapi.io/v1/latest?access_key=" + apiKey + "&base=EUR");
        ApiCurrencyRatesResponse apiCurrencyResponse = webClientRates
                .get()
                .accept(MediaType.APPLICATION_JSON)
                .retrieve().bodyToMono(ApiCurrencyRatesResponse.class)
                .doOnError(Throwable::printStackTrace)
                .onErrorReturn(new ApiCurrencyRatesResponse())
                .block();
        if (apiCurrencyResponse != null && apiCurrencyResponse.isSuccess()) {
            Map<String, BigDecimal> rates = apiCurrencyResponse.getRates();
            Date current = apiCurrencyResponse.getDate();

            List<String> enabled = getEnabledCurrencyCodes();

            for (Map.Entry<String, BigDecimal> entry : rates.entrySet()) {
                if (enabled.contains(entry.getKey()) && em.createNamedQuery("CurrencyPair.rateAtDate").setParameter(CURRENCY, entry.getKey()).setParameter("date", current).getResultList().isEmpty()) {
                    CurrencyPair currencyPair = new CurrencyPair();
                    CurrencyType from = currencyTypeService.getCurrencyTypeByCode("EUR");
                    CurrencyType to = currencyTypeService.getCurrencyTypeByCode(entry.getKey());
                    currencyPair.setFrom(from);
                    currencyPair.setTo(to);
                    currencyPair.setDate(current);
                    currencyPair.setValue(entry.getValue());
                    em.persist(currencyPair);
                }
            }
        }
    }

    public void fetchRates(Date date) {
        String isoDate = DateTimeFormatter.ISO_LOCAL_DATE.format(date.toInstant().atZone(ZoneId.of("GMT")));
        WebClient webClient = WebClient.create("http://api.exchangeratesapi.io/v1/" + isoDate + "?access_key=" + apiKey + "&base=EUR");
        ApiCurrencyRatesResponse apiCurrencyRatesResponse = webClient
                .get()
                .accept(MediaType.APPLICATION_JSON)
                .retrieve().bodyToMono(ApiCurrencyRatesResponse.class)
                .doOnError(Throwable::printStackTrace)
                .onErrorReturn(new ApiCurrencyRatesResponse())
                .block();
        if (apiCurrencyRatesResponse != null && apiCurrencyRatesResponse.isSuccess()) {
            Map<String, BigDecimal> rates = apiCurrencyRatesResponse.getRates();
            Date current = apiCurrencyRatesResponse.getDate();

            List<String> enabled = getEnabledCurrencyCodes();

            for (Map.Entry<String, BigDecimal> entry : rates.entrySet()) {
                if (enabled.contains(entry.getKey()) && rateAtDateQuery(entry.getKey(), current).getResultList().isEmpty()) {
                    CurrencyPair currencyPair = new CurrencyPair();
                    CurrencyType from = currencyTypeService.getCurrencyTypeByCode("EUR");
                    CurrencyType to = currencyTypeService.getCurrencyTypeByCode(entry.getKey());
                    currencyPair.setFrom(from);
                    currencyPair.setTo(to);
                    currencyPair.setDate(current);
                    currencyPair.setValue(entry.getValue());
                    em.persist(currencyPair);
                }
            }
        }
    }

    private List<String> getEnabledCurrencyCodes() {
        return em.createNamedQuery("CurrencyType.getEnabledCurrencyTypes", CurrencyType.class).getResultList().stream().map(CurrencyType::getCode).collect(Collectors.toList());
    }

    private TypedQuery<BigDecimal> rateAtDateQuery(String currency, Date date) {
        return em.createNamedQuery("CurrencyPair.rateAtDate", BigDecimal.class).setParameter(CURRENCY, currency).setParameter("date", date);
    }
}
