package com.algaworks.algafood.api.v1.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@AllArgsConstructor
@Getter
@Setter
public class VendaDiaria {
    private Date data;
    private Long totalVendas;
    private BigDecimal totalFaturado;
}
