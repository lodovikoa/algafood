package com.algaworks.algafood.domain.model.service;

import com.algaworks.algafood.api.dto.VendaDiaria;
import com.algaworks.algafood.domain.model.filter.VendaDiariaFilter;

import java.util.List;

public interface VendaQueryService {

    List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filtro);
}
