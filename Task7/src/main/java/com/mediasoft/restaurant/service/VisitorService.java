package com.mediasoft.restaurant.service;

import com.mediasoft.restaurant.entity.Visitor;
import com.mediasoft.restaurant.repository.VisitorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VisitorService {
    private final VisitorRepository visitorRepository;

    @Autowired
    public VisitorService(VisitorRepository visitorRepository) {
        this.visitorRepository = visitorRepository;
    }

    public Visitor save(Visitor visitor) {
        return visitorRepository.save(visitor);
    }

    public void remove(Long id) {
        visitorRepository.deleteById(id);
    }

    public List<Visitor> findAll() {
        return visitorRepository.findAll();
    }
    
    public Optional<Visitor> findById(Long id) {
        return visitorRepository.findById(id);
    }
} 