package com.cgg.framework.dto.request;

import com.cgg.framework.dto.Dto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public abstract class Command extends Dto {
}
