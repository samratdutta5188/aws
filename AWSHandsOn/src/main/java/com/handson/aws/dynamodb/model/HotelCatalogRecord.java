package com.handson.aws.dynamodb.model;

import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Value;

@Builder
@Value
public class HotelCatalogRecord {

    public static final String ATTR_HOTEL_NAME = "HotelName";

    public static final String ATTR_CITY = "City";

    public static final String ATTR_RATING = "Rating";

    public static final String ATTR_HOTEL_FEATURE = "HotelFeature";

    private String hotelName;

    private String city;

    private Integer rating;

    private HotelFeature hotelFeature;

}
