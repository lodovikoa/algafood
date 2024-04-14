package com.algaworks.algafood.api.v1.dto.input;

import com.algaworks.algafood.core.validation.Groups;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EstadoInputIdDTO {

    @Schema(example = "1")
    @NotNull
    private long id;
}
