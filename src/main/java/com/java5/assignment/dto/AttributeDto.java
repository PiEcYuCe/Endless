package com.java5.assignment.dto;

import com.java5.assignment.entities.AttributeValue;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
public class AttributeDto implements Serializable {
    Long id;

    @NotNull
    @Size(max = 255)
    String attributeName;

    @Size(max = 255)
    String attributeNote;

    Set<AttributeValue> attributeValues; // Nếu bạn đảm bảo sử dụng lombok và không cần các getter và setter
}