package com.abelium.inatrace.components.currencies;

import com.abelium.inatrace.components.codebook.currencies.CurrencyTypeService;
import com.abelium.inatrace.components.common.BaseService;
import com.abelium.inatrace.components.currencies.api.ApiCurrencyResponse;
import com.abelium.inatrace.db.entities.codebook.CurrencyType;
import com.abelium.inatrace.db.entities.currencies.CurrencyPair;
import com.abelium.inatrace.db.enums.CurrencyEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.UnknownHostException;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class CurrencyService extends BaseService {

    @Autowired
    public CurrencyTypeService currencyTypeService;

    public Date latestDate() {
        List<Date> dateList = em.createNamedQuery("CurrencyPair.getLatestDate", Date.class).getResultList();
        if (dateList.isEmpty()) {
            return null;
        }
        return dateList.get(0);
    }

    public BigDecimal convertFromEur(CurrencyEnum to, BigDecimal value) {
        return value.multiply(em.createNamedQuery("CurrencyPair.latestRate", BigDecimal.class).setParameter("currency", to.toString()).getResultList().get(0));
    }

    public BigDecimal convertToEur(CurrencyEnum from, BigDecimal value) {
        return value.divide(em.createNamedQuery("CurrencyPair.latestRate", BigDecimal.class).setParameter("currency", from.toString()).getResultList().get(0), 6, RoundingMode.HALF_UP);
    }

    public BigDecimal convertFromEurAtDate(CurrencyEnum to, BigDecimal value, Date date) {
        return value.multiply(em.createNamedQuery("CurrencyPair.rateAtDate", BigDecimal.class).setParameter("currency", to.toString()).setParameter("date", date).getSingleResult());
    }

    public BigDecimal convertToEurAtDate(CurrencyEnum from, BigDecimal value, Date date) {
        return value.divide(em.createNamedQuery("CurrencyPair.rateAtDate", BigDecimal.class).setParameter("currency", from.toString()).setParameter("date", date).getSingleResult(), 6, RoundingMode.HALF_UP);
    }

    public BigDecimal convert(CurrencyEnum from, CurrencyEnum to, BigDecimal value) {
        if (from == to) {
            return value;
        } else if (from.equals(CurrencyEnum.EUR)) {
            return this.convertFromEur(to, value);
        } else if (to.equals(CurrencyEnum.EUR)) {
            return this.convertToEur(from, value);
        } else {
            return this.convertFromEur(to, this.convertToEur(from, value));
        }
    }

    public BigDecimal convertAtDate(CurrencyEnum from, CurrencyEnum to, BigDecimal value, Date date) {
        if (from == to) {
            return value;
        } else if (from.equals(CurrencyEnum.EUR)) {
            return this.convertFromEurAtDate(to, value, date);
        } else if (to.equals(CurrencyEnum.EUR)) {
            return this.convertToEurAtDate(from, value, date);
        } else {
            return this.convertFromEurAtDate(to, this.convertToEurAtDate(from, value, date), date);
        }
    }

    @Transactional
    @Scheduled(cron = "0 1 0 * * *")
    @EventListener(ApplicationReadyEvent.class)
    public void updateCurrencies() {
        WebClient webClient = WebClient.create("http://api.exchangeratesapi.io/v1/latest?access_key=b4bea045495a28eb7dd88eeee312e981&base=EUR&symbols=USD,GBP,RWF,HNL");
        ApiCurrencyResponse apiCurrencyResponse = webClient
                .get()
                .accept(MediaType.APPLICATION_JSON)
                .retrieve().bodyToMono(ApiCurrencyResponse.class)
                .doOnError(Throwable::printStackTrace)
                .onErrorReturn(new ApiCurrencyResponse())
                .block();
        if (apiCurrencyResponse == null || !apiCurrencyResponse.isSuccess()) {
            return;
        }
        Map<String, BigDecimal> rates = apiCurrencyResponse.getRates();
        Date current = apiCurrencyResponse.getDate();
        Date latest = this.latestDate();
        if (latest == null || current.after(latest)) {
            for (String code : rates.keySet()) {
                CurrencyPair currencyPair = new CurrencyPair();
                CurrencyType from = currencyTypeService.getCurrencyTypeByCode("EUR");
                CurrencyType to = currencyTypeService.getCurrencyTypeByCode(code);
                currencyPair.setFrom(from);
                currencyPair.setTo(to);
                currencyPair.setDate(current);
                currencyPair.setValue(rates.get(code));
                em.persist(currencyPair);
            }
        }
    }
}
