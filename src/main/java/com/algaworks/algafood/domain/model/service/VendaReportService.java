package com.algaworks.algafood.domain.model.service;

import com.algaworks.algafood.domain.model.filter.VendaDiariaFilter;

public interface VendaReportService {

    byte[] emitirVendasDiarias(VendaDiariaFilter filtro, String timeOffset);
}
