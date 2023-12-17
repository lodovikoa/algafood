package com.algaworks.algafood.api.dto.input;

import com.algaworks.algafood.core.validation.Groups;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EstadoInputIdDTO {

    @NotNull
    private long id;
}
