package wirebarley.jeong.web;

import org.springframework.stereotype.Repository;

import java.text.DecimalFormat;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class ExchangeRateRepository {

    private static final Map<String, ExchangeRateDto> saveRate = new ConcurrentHashMap<>();

    public void saveCountry(String countryName, String countryRate) {
        ExchangeRateDto exchangeRateDto = new ExchangeRateDto();
        exchangeRateDto.setCountryName(countryName.substring(3));
        exchangeRateDto.setCountryRate(countryRate);
        saveRate.put(countryName, exchangeRateDto);
    }

    public void saveExchangePrice(String countryName, String price) {
        ExchangeRateDto findDto = findCountry(countryName);
        String rate = findDto.getCountryRate();
        String replaceRate = rate.replaceAll(",","");
        Float multiRate = Float.parseFloat(replaceRate) * Float.parseFloat(price);

        DecimalFormat digitFormat = new DecimalFormat("###,###.00");
        String formatPrice = digitFormat.format(multiRate);
        findDto.setExchangePrice(formatPrice);
        saveRate.put(countryName, findDto);
    }

    public ExchangeRateDto findCountry(String countryName) {
        return saveRate.get(countryName);
    }

    public void clearSaveRate() {
        saveRate.clear();
    }
}
