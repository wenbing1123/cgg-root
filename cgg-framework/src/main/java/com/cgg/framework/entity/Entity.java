package com.cgg.framework.entity;

import com.cgg.framework.converter.Converter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;

import java.io.Serializable;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public abstract class Entity implements Serializable, Converter {

    @Id
    private Long id;

}
