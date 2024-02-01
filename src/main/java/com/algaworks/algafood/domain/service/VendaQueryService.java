package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.api.dto.VendaDiaria;
import com.algaworks.algafood.domain.filter.VendaDiariaFilter;

import java.util.List;

public interface VendaQueryService {

    List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filtro, String timeOffset);
}
