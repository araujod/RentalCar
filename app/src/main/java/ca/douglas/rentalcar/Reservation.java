package ca.douglas.rentalcar;

import java.io.Serializable;

public class Reservation implements Serializable {

    public String id;
    public String user_ID;
    public String car_ID;
    public String startDate;
    public String endDate;
    public String price;
    public String datePickup;
    public String dateReturn;
    public String finalPrice;
}
