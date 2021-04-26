package com.demo.real.estate.controller;

import com.demo.real.estate.exceptions.ListingNotFoundException;
import com.demo.real.estate.model.Listing;
import com.demo.real.estate.model.ListingSearchCriteria;
import com.demo.real.estate.service.ListingService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ListingController {

    @Autowired
    ListingService listingService;

    @RequestMapping(value = "/listings", method = RequestMethod.GET)
    ResponseEntity<List<Listing>> getAllListing(ListingSearchCriteria listingSearchCriteria) {
        try {
            return new ResponseEntity<>(listingService.getAllWithFilters(listingSearchCriteria), HttpStatus.OK);
        } catch (JsonProcessingException e) {
            System.out.println(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/listings", method = RequestMethod.POST)
    ResponseEntity<Listing> newEmployee(@RequestBody Listing listing) {
        return new ResponseEntity<>(listingService.addListing(listing), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/listings/{listingId}", method = RequestMethod.PUT)
    ResponseEntity<Listing> replaceEmployee(@RequestBody Listing listing, @PathVariable Long listingId) {

        Optional<Listing> data = listingService.findListingById(listingId);
        if (data.isPresent()) {
            listing.setListingId(listingId);
            return new ResponseEntity<>(listingService.updateListing(listing), HttpStatus.OK);
        } else {
            throw new ListingNotFoundException("Provided Listing Id is not present for update");
        }
    }

    @RequestMapping(value = "/listings/{listingId}", method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteEmployee(@PathVariable Long listingId) {
        Optional<Listing> data = listingService.findListingById(listingId);
        if (data.isPresent()) {
            listingService.deleteListingById(listingId);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            // throw an exception
            throw new ListingNotFoundException("Provided Listing Id is not present for delete");
        }
    }

}
