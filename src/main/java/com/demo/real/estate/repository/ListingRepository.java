package com.demo.real.estate.repository;

import com.demo.real.estate.model.Listing;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ListingRepository extends CrudRepository<Listing, Long> {

}
