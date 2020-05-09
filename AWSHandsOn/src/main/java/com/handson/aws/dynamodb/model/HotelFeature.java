package com.handson.aws.dynamodb.model;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class HotelFeature {

    public static final String PARKING = "ParkingAvailable";

    public static final String SWIMMING_POOL ="SwimmingPoolAvailable";

    public static final String LIFTS ="Lifts";

    private Boolean parkingAvailable;

    private Boolean swimmingPoolAvailable;

    private Integer lifts;

}
