package com.java5.assignment.services;

import com.java5.assignment.dto.AttributeDto;
import com.java5.assignment.jpa.VersionAttributeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class VersionAttributeService {
    @Autowired
    private VersionAttributeRepository versionAttributeRepository;

    public List<AttributeDto> getAttributesByProductVersionID(Long productVersionID) {
        List<Object[]> results = versionAttributeRepository.findByProductVersionID(productVersionID);
        Map<String, List<String>> attributeMap = new HashMap<>();

        for (Object[] result : results) {
            String attributeName = (String) result[0];
            String attributeValue = (String) result[1];

            attributeMap
                    .computeIfAbsent(attributeName, k -> new ArrayList<>())
                    .add(attributeValue);
        }

        List<AttributeDto> attributeDtos = new ArrayList<>();
        for (Map.Entry<String, List<String>> entry : attributeMap.entrySet()) {
            attributeDtos.add(new AttributeDto(entry.getKey(), entry.getValue().toArray(new String[0])));
        }

        return attributeDtos;
    }
}
