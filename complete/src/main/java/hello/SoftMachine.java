package hello;

import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import java.util.HashMap;
import java.util.Map;

public class SoftMachine {
    private Map<Double, Integer> coinInventory;
    private String companyName;
    private String machineId;
    private int num_nickle;
    private int num_dime;
    private int num_quarter;

    public SoftMachine() {
        coinInventory = new HashMap<>();
        coinInventory.put(Coin.getNICKLE(), 0);
        coinInventory.put(Coin.getDIME(), 0);
        coinInventory.put(Coin.getQUARTER(), 0);
        reset();
    }

    public double insertCoin(double coin) {
        if(isValidCoin(coin)) {
            coinInventory.put(coin, coinInventory.get(coin) + 1);
            if (coin == Coin.getDIME()) num_dime++;
            if (coin == Coin.getQUARTER()) num_quarter++;
            if (coin == Coin.getNICKLE()) num_nickle++;
        }
        return coin;

    }

    public double getTotalAmount() {
        double result = 0.0;
        result = Math.round((num_dime * Coin.getDIME() + num_nickle * Coin.getNICKLE() + num_quarter * Coin.getQUARTER()) * 100.00)/100.00;
        return result;
    }

    public boolean isValidCoin(double coin) {
        boolean isValidCoin = false;
        if(coin == Coin.getNICKLE() || coin == Coin.getDIME() || coin == Coin.getQUARTER()) {
            isValidCoin = true;
        } else {
            System.out.println("[ERROR]: Invalid coin. " + coin + " is returned.");
        }
        return isValidCoin;
    }

    public void reset() {
        setNum_dime(0);
        setNum_nickle(0);
        setNum_quarter(0);
    }
    
    public String getCompanyName() {
        return this.companyName;
    }

    public void setCompanyName(BadCompany company) {
        this.companyName = company.getCompanyName();
    }

    public void requestRestock() {
        try {
            makeCall();
        } catch (Exception e) {
            System.out.println("Request for restock failed.");
        }
    }

    private void makeCall() throws ResourceAccessException {
        String transactionUrl = "http://192.168.88.123:8080/requestRestock";
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromUriString(transactionUrl).queryParam("id", machineId);

        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(builder.toUriString(), String.class);
        System.out.println(response);
    }

    public int getNum_nickle() {
        return num_nickle;
    }

    public void setNum_nickle(int num_nickle) {
        this.num_nickle = num_nickle;
    }

    public int getNum_dime() {
        return num_dime;
    }

    public void setNum_dime(int num_dime) {
        this.num_dime = num_dime;
    }

    public int getNum_quarter() {
        return num_quarter;
    }

    public void setNum_quarter(int num_quarter) {
        this.num_quarter = num_quarter;
    }
}
