package com.demo.real.estate.repository;

import com.demo.real.estate.model.FilterSearchCriteria;
import com.demo.real.estate.model.Listing;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class ListingCriteriaRepository {

    private EntityManager entityManager;
    private CriteriaBuilder criteriaBuilder;

    public ListingCriteriaRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    public List<Listing> getAllWithFilters(FilterSearchCriteria filterSearchCriteria) {

        CriteriaQuery<Listing> criteriaQuery = criteriaBuilder.createQuery(Listing.class);
        Root<Listing> listingRoot = criteriaQuery.from(Listing.class);
        Predicate predicate = getPredicate(filterSearchCriteria, listingRoot);
        criteriaQuery.where(predicate);
        TypedQuery<Listing> typedQuery = entityManager.createQuery(criteriaQuery);
        return typedQuery.getResultList();
    }

    private Predicate getPredicate(FilterSearchCriteria filterSearchCriteria, Root<Listing> listingRoot) {
        List<Predicate> predicates = new ArrayList<>();

        if (Objects.nonNull(filterSearchCriteria.getRealEstateType())) {
            predicates.add(
                    criteriaBuilder.equal(listingRoot.get("realEstateType"), filterSearchCriteria.getRealEstateType())
            );
        }

        if (Objects.nonNull(filterSearchCriteria.getRentalPrice()) && Objects.nonNull(filterSearchCriteria.getRentalPrice().getMin())) {
            predicates.add(
                    criteriaBuilder.greaterThanOrEqualTo(listingRoot.get("rentalPrice"), filterSearchCriteria.getRentalPrice().getMin())
            );
        }

        if (Objects.nonNull(filterSearchCriteria.getRentalPrice()) && Objects.nonNull(filterSearchCriteria.getRentalPrice().getMax())) {
            predicates.add(
                    criteriaBuilder.lessThanOrEqualTo(listingRoot.get("rentalPrice"), filterSearchCriteria.getRentalPrice().getMax())
            );
        }
        if (Objects.nonNull(filterSearchCriteria.getSalesPrice()) && Objects.nonNull(filterSearchCriteria.getSalesPrice().getMin())) {
            predicates.add(
                    criteriaBuilder.greaterThanOrEqualTo(listingRoot.get("salesPrice"), filterSearchCriteria.getSalesPrice().getMin())
            );
        }
        if (Objects.nonNull(filterSearchCriteria.getSalesPrice()) && Objects.nonNull(filterSearchCriteria.getSalesPrice().getMax())) {
            predicates.add(
                    criteriaBuilder.lessThanOrEqualTo(listingRoot.get("salesPrice"), filterSearchCriteria.getSalesPrice().getMax())
            );
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));

    }

}
