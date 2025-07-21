package com.mediasoft.restaurant.repository;

import com.mediasoft.restaurant.entity.Visitor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class VisitorRepository {
    private final List<Visitor> visitors = new ArrayList<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public Visitor save(Visitor visitor) {
        if (visitor.getId() == null) {
            visitor.setId(idGenerator.getAndIncrement());
        }
        visitors.add(visitor);
        return visitor;
    }

    public void remove(Long id) {
        visitors.removeIf(visitor -> visitor.getId().equals(id));
    }

    public List<Visitor> findAll() {
        return new ArrayList<>(visitors);
    }

    public Optional<Visitor> findById(Long id) {
        return visitors.stream()
                .filter(visitor -> visitor.getId().equals(id))
                .findFirst();
    }
} 