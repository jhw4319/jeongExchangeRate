package wirebarley.jeong.web;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ExchangeRateRepositoryTest {

    ExchangeRateRepository exchangeRateRepository = new ExchangeRateRepository();

    @AfterEach
    void afterEach() {
        exchangeRateRepository.clearSaveRate();
    }

    @Test
    void saveCountry() {
        String kr = "USDKRW";
        String krRate = "1182.61";
        String jp = "USDJPY";
        String jpRate = "111.03";
        String php = "USDPHP";
        String phpRate = "50.72";

        exchangeRateRepository.saveCountry(kr, krRate);
        exchangeRateRepository.saveCountry(jp, jpRate);
        exchangeRateRepository.saveCountry(php, phpRate);

        ExchangeRateDto krExchange = exchangeRateRepository.findCountry(kr);
        ExchangeRateDto jpExchange = exchangeRateRepository.findCountry(jp);
        ExchangeRateDto phpExchange = exchangeRateRepository.findCountry(php);
        assertThat(krExchange.getCountryName()).isEqualTo("KRW");
        assertThat(krExchange.getCountryRate()).isEqualTo(krRate);
        assertThat(jpExchange.getCountryName()).isEqualTo("JPY");
        assertThat(jpExchange.getCountryRate()).isEqualTo(jpRate);
        assertThat(phpExchange.getCountryName()).isEqualTo("PHP");
        assertThat(phpExchange.getCountryRate()).isEqualTo(phpRate);
    }

    @Test
    void saveExchangePrice() {
        String jp = "USDJPY";
        String jpRate = "111.08";
        String amount = "100";
        exchangeRateRepository.saveCountry(jp, jpRate);

        exchangeRateRepository.saveExchangePrice(jp, amount);
        ExchangeRateDto jpExchange = exchangeRateRepository.findCountry(jp);

        assertThat(jpExchange.getExchangePrice()).isEqualTo("11,108.00");
    }
}