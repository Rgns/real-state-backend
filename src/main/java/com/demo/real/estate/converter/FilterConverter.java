package com.demo.real.estate.converter;

import com.demo.real.estate.model.FilterRange;
import com.demo.real.estate.model.FilterSearchCriteria;
import com.demo.real.estate.model.ListingSearchCriteria;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Objects;

public class FilterConverter {

    public static FilterSearchCriteria parse(ListingSearchCriteria listingSearchCriteria) throws JsonProcessingException {
        FilterSearchCriteria filterSearchCriteria = new FilterSearchCriteria();
        if (Objects.nonNull(listingSearchCriteria.getRentalPrice())) {
            FilterRange rentalPrice = new ObjectMapper().readValue(listingSearchCriteria.getRentalPrice(), FilterRange.class);
            filterSearchCriteria.setRentalPrice(rentalPrice);
        }
        filterSearchCriteria.setRealEstateType(listingSearchCriteria.getRealEstateType());
        if (Objects.nonNull(listingSearchCriteria.getSalesPrice())) {
            FilterRange salesPrice = new ObjectMapper().readValue(listingSearchCriteria.getSalesPrice(), FilterRange.class);
            filterSearchCriteria.setSalesPrice(salesPrice);
        }
        return filterSearchCriteria;
    }
}
