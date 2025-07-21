package com.mediasoft.restaurant.repository;

import com.mediasoft.restaurant.entity.Rating;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class RatingRepository {
    private final List<Rating> ratings = new ArrayList<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public Rating save(Rating rating) {
        if (rating.getId() == null) {
            rating.setId(idGenerator.getAndIncrement());
        }
        ratings.add(rating);
        return rating;
    }

    public void remove(Long id) {
        ratings.removeIf(rating -> rating.getId().equals(id));
    }

    public List<Rating> findAll() {
        return new ArrayList<>(ratings);
    }

    public Optional<Rating> findById(Long id) {
        return ratings.stream()
                .filter(rating -> rating.getId().equals(id))
                .findFirst();
    }
} 