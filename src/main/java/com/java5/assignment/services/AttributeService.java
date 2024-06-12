package com.java5.assignment.services;

import com.java5.assignment.dto.AttributeDto;
import com.java5.assignment.entities.VersionAttribute;
import com.java5.assignment.jpa.AttributeRepository;
import com.java5.assignment.jpa.VersionAttributeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AttributeService {
    @Autowired
    private AttributeRepository attributeRepository;

    @Autowired
    private VersionAttributeRepository versionAttributeRepository;

}
