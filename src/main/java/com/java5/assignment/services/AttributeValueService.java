package com.java5.assignment.services;

import com.java5.assignment.dto.AttributeValueDto;
import com.java5.assignment.entities.AttributeValue;
import com.java5.assignment.jpa.AttributeValueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AttributeValueService {
    @Autowired
    private AttributeValueRepository attributeValueRepository;

    public List<AttributeValueDto> getAttributeValueByAttributeID(long id) {
        List<AttributeValue> list = attributeValueRepository.getAttributeValueByAttributeID(id);
        List<AttributeValueDto> dtos = new ArrayList<>();
        for (AttributeValue attributeValue : list) {
            dtos.add(new AttributeValueDto(attributeValue.getId(), attributeValue.getValue()));
        }
        return dtos;
    }
}
