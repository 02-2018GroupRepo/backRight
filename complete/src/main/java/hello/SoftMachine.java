package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriComponentsBuilder;
import java.util.HashMap;
import java.util.Map;

public class SoftMachine {
//    @Value("${url.path:localhost:3000/requestRestock}")
//    private String transactionUrl;

    private Map<Double, Integer> coinInventory;
    private String companyName;
    private String machineId = "001";
    private int num_nickle;
    private int num_dime;
    private int num_quarter;

    public SoftMachine() {
        coinInventory = new HashMap<>();
        coinInventory.put(Coin.getNICKLE(), 10);
        coinInventory.put(Coin.getDIME(), 10);
        coinInventory.put(Coin.getQUARTER(), 10);
        reset();
    }

    //DEALING WITH MACHINE MONEY
    public void returnMachineCoins(double difference) {
        do {
            if (difference % Coin.getQUARTER() == 0) {
                coinInventory.put(Coin.getQUARTER(), coinInventory.get(Coin.getQUARTER()) - 1);
                difference -= Coin.getQUARTER();
            }
            else if (difference % Coin.getDIME() == 0) {
                coinInventory.put(Coin.getDIME(), coinInventory.get(Coin.getDIME()) - 1);
                difference -= Coin.getDIME();
            }
            else if (difference % Coin.getNICKLE() == 0) {
                coinInventory.put(Coin.getNICKLE(), coinInventory.get(Coin.getNICKLE()) - 1);
                difference -= Coin.getNICKLE();
            }
        } while (difference > 0);
    }


    //DEALING WITH CUSTOMER COINS
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


    //GETTER & SETTER FOR MACHINE'S CO NAME
    public String getCompanyName() { return this.companyName; }

    public void setCompanyName(BadCompany company) { this.companyName = company.getCompanyName(); }



    // CUSTOMER COIN AMT GETTERS & SETTERS
    public void requestRestock() {
        try {
            String response = makeCall();
            System.out.println(response);
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("Request for restock failed.");
        }
    }

    private String makeCall() throws ResourceAccessException {
        String transactionUrl = "http://192.168.88.83:3000/requestRestock?id=001";
        System.out.println(transactionUrl);
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(transactionUrl)
                .queryParam("id", machineId);
        RestTemplate restTemplate = new RestTemplate();
        System.out.println(builder.toString());
        String response = restTemplate.getForObject(builder.toUriString(), String.class);

        System.out.println(response);
        return response;
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