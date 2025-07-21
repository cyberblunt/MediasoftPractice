package com.mediasoft.restaurant.service;

import com.mediasoft.restaurant.dto.VisitorRequestDto;
import com.mediasoft.restaurant.dto.VisitorResponseDto;
import com.mediasoft.restaurant.entity.Visitor;
import com.mediasoft.restaurant.repository.VisitorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class VisitorService {
    private final VisitorRepository visitorRepository;
    private final AtomicLong idCounter = new AtomicLong(1);

    @Autowired
    public VisitorService(VisitorRepository visitorRepository) {
        this.visitorRepository = visitorRepository;
    }

    public VisitorResponseDto save(VisitorRequestDto requestDto) {
        Visitor visitor = new Visitor();
        visitor.setId(idCounter.getAndIncrement());
        visitor.setName(requestDto.getName());
        visitor.setAge(requestDto.getAge());
        visitor.setGender(requestDto.getGender());
        
        Visitor savedVisitor = visitorRepository.save(visitor);
        return convertToResponseDto(savedVisitor);
    }

    public Optional<VisitorResponseDto> update(Long id, VisitorRequestDto requestDto) {
        Optional<Visitor> existingVisitor = visitorRepository.findById(id);
        if (existingVisitor.isPresent()) {
            Visitor visitor = existingVisitor.get();
            visitor.setName(requestDto.getName());
            visitor.setAge(requestDto.getAge());
            visitor.setGender(requestDto.getGender());
            
            Visitor updatedVisitor = visitorRepository.save(visitor);
            return Optional.of(convertToResponseDto(updatedVisitor));
        }
        return Optional.empty();
    }

    public boolean delete(Long id) {
        Optional<Visitor> visitor = visitorRepository.findById(id);
        if (visitor.isPresent()) {
            visitorRepository.remove(id);
            return true;
        }
        return false;
    }

    public List<VisitorResponseDto> findAll() {
        return visitorRepository.findAll().stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    public Optional<VisitorResponseDto> findById(Long id) {
        return visitorRepository.findById(id)
                .map(this::convertToResponseDto);
    }

    private VisitorResponseDto convertToResponseDto(Visitor visitor) {
        return new VisitorResponseDto(
                visitor.getId(),
                visitor.getName(),
                visitor.getAge(),
                visitor.getGender()
        );
    }
} 