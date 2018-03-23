package hello;

import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;

public class SoftMachine {
  // @Value("${url.path:localhost:3000/requestRestock}")
 //  private String transactionUrl;
    private Map<Double, Integer> coinInventory = new HashMap<>();
    private String companyName;
    private String machineId;
    private String errorMsg = null;
    private int num_nickle;
    private int num_dime;
    private int num_quarter;

    public SoftMachine() {
        coinInventory.put(Coin.getNICKLE(), 10);
        coinInventory.put(Coin.getDIME(), 10);
        coinInventory.put(Coin.getQUARTER(), 10);
        reset();
    }

    public SoftMachine(String machineId) {
        coinInventory.put(Coin.getNICKLE(), 10);
        coinInventory.put(Coin.getDIME(), 10);
        coinInventory.put(Coin.getQUARTER(), 10);
        setMachineId(machineId);
        reset();
    }

    //DEALING WITH MACHINE MONEY
    public String returnMachineCoins(double difference) {
        String change = String.format("Change %.2f is returned: ", difference);
        int coinArray[] = convertCashToCoins(difference);
        int num_quarter = coinArray[0];
        int num_nickel = coinArray[2];
        int num_dime = coinArray[1];
        coinInventory.put(Coin.getQUARTER(), coinInventory.get(Coin.getQUARTER()) - num_quarter);
        coinInventory.put(Coin.getDIME(), coinInventory.get(Coin.getDIME()) - num_dime);
        coinInventory.put(Coin.getNICKLE(), coinInventory.get(Coin.getNICKLE()) - num_nickel);

        change += "Quarter(" + num_quarter + ") Dime(" + num_dime + ") Nickel(" + num_nickel + ")";

        return change;
    }

    public int[] convertCashToCoins(double money) {
        int quarters = (int) Math.floor(money / Coin.getQUARTER());
        money = money - quarters * Coin.getQUARTER();
        int dimes = (int) Math.floor(money / Coin.getDIME());
        money = money - dimes * Coin.getDIME();
        int nickels = Math.round((float)(money / Coin.getNICKLE()));
        int[] coinArray = {quarters, dimes, nickels};
        return coinArray;
    }

    public double currentMoney(){
        return(Coin.getNICKLE()* coinInventory.get(Coin.getNICKLE()))
                + (Coin.getDIME()*coinInventory.get(Coin.getDIME()))
                + (Coin.getQUARTER()*coinInventory.get(Coin.getQUARTER()));
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
        result = Math.round((num_dime * Coin.getDIME()
                + num_nickle * Coin.getNICKLE()
                + num_quarter * Coin.getQUARTER()) * 100.00)/100.00;
        return result;
    }

    public String getErrorMsg() {
        return errorMsg;
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

    public void requestRestock() {
        try {
            String response = makeCall();
            System.out.println(response);
        } catch (Exception e) {
            System.out.println("Request for restock failed.");
            errorMsg = e.toString();
            System.out.println(errorMsg);
        }
    }

    private String makeCall() throws ResourceAccessException {
        String transactionUrl = "http://192.168.88.83:3000/requestRestock?id=" + getMachineId();
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(transactionUrl);
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(builder.toUriString(), String.class);
        return response;
    }

    //GETTER & SETTER FOR MACHINE ID
    public String getMachineId() {
        return machineId;
    }

    public void setMachineId(String machineId) {
        this.machineId = machineId;
    }

    //GETTER & SETTER FOR MACHINE'S CO NAME
    public String getCompanyName() { return this.companyName; }

    public void setCompanyName(BadCompany company) { this.companyName = company.getCompanyName(); }

    // CUSTOMER COIN AMT GETTERS & SETTERS
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