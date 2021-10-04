package wirebarley.jeong.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@Controller
@RequestMapping("/rate")
@RequiredArgsConstructor
public class ExchangeRateController {

    private final ExchangeRateRepository exchangeRateRepository;

    @PostMapping("/exchangeRate_save")
    public String addExchangeRate(@RequestParam HashMap<String, String> rateData, Model model) {
        rateData.forEach((country, rate) -> exchangeRateRepository.saveCountry(country, rate));
        ExchangeRateDto exchange = exchangeRateRepository.findCountry("USDKRW");
        model.addAttribute("exchange", exchange);
        return "rate/exchangeRate";
    }

    @PostMapping("/exchangeRate_change")
    public String changeExchangeRate(@RequestParam String country, Model model) {
        ExchangeRateDto exchange = exchangeRateRepository.findCountry(country);
        model.addAttribute("exchange", exchange);
        return "rate/exchangeRate";
    }

    @PostMapping("/exchangeRate_submit")
    public String submitExchangeRate(@RequestParam String amount, @RequestParam String country, Model model) {
        exchangeRateRepository.saveExchangePrice(country, amount);
        ExchangeRateDto exchange = exchangeRateRepository.findCountry(country);
        model.addAttribute("exchange", exchange);
        return "rate/exchangeRate";
    }

    @GetMapping("/exchangeRate")
    public String exchangeRateHtml(Model model) {
        String country = "USDKRW";
        String amount = "0";
        exchangeRateRepository.saveCountry(country, amount);
        ExchangeRateDto exchange = exchangeRateRepository.findCountry(country);
        model.addAttribute("exchange", exchange);
        return "rate/exchangeRate";
    }
}
