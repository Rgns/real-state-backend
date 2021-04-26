package com.demo.real.estate.service;

import com.demo.real.estate.converter.FilterConverter;
import com.demo.real.estate.model.FilterSearchCriteria;
import com.demo.real.estate.model.Listing;
import com.demo.real.estate.model.ListingSearchCriteria;
import com.demo.real.estate.repository.ListingCriteriaRepository;
import com.demo.real.estate.repository.ListingRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.h2.tools.Csv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ListingService {

    ListingRepository listingRepo;
    ListingCriteriaRepository listingCriteriaRepository;
    private ResourceLoader resourceLoader;

    @Autowired
    public ListingService(ListingRepository repo,
                          ListingCriteriaRepository listingCriteriaRepository,
                          ResourceLoader resourceLoader) {
        this.listingRepo = repo;
        this.listingCriteriaRepository = listingCriteriaRepository;
        initData();
    }

    public List<Listing> getAllWithFilters(ListingSearchCriteria listingSearchCriteria) throws JsonProcessingException {
        if (Objects.nonNull(listingSearchCriteria)) {
            FilterSearchCriteria filterSearchCriteria = FilterConverter.parse(listingSearchCriteria);
            return listingCriteriaRepository.getAllWithFilters(filterSearchCriteria);
        }
        return (List<Listing>) listingRepo.findAll();
    }

    public Listing addListing(Listing listing) {

        return this.listingRepo.save(listing);
    }

    private void initData() {
        createListingFromCsvRecord();
    }

    private void createListingFromCsvRecord() {
        try {
            String path = "classpath:static/listings.csv";
            ResultSet rs = new Csv().read(path, null, null);
            List<Listing> list = new ArrayList<>();
            while (rs.next()) {
                Listing listing = new Listing();
                listing.setListingId(Long.parseLong(rs.getString("listingId")));
                listing.setRealEstateType(rs.getString("realEstateType"));
                listing.setStreet(rs.getString("street"));
                listing.setHouseNumber(rs.getString("houseNumber"));
                listing.setPostcode(rs.getString("postcode"));
                listing.setCity(rs.getString("city"));
                listing.setLivingArea(rs.getString("livingArea"));
                listing.setSiteArea(rs.getString("siteArea"));
                String rentalCost = rs.getString("rentalPrice");
                if (rentalCost != null) {
                    listing.setRentalPrice(rentalCost);
                }
                String salesCost = rs.getString("salesPrice");
                if (salesCost != null) {
                    listing.setSalesPrice(salesCost);
                }
                listing.setImageURL(rs.getString("imageURL"));
                list.add(listing);
            }

            listingRepo.saveAll(list);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public Optional<Listing> findListingById(Long listingId) {
        return listingRepo.findById(listingId);
    }

    public Listing updateListing(Listing listing) {
        return listingRepo.save(listing);
    }

    public void deleteListingById(Long listingId) {
        listingRepo.deleteById(listingId);
    }
}
