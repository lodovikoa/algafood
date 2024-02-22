package com.algaworks.algafood.core.modelmapper;

import com.algaworks.algafood.api.v1.dto.input.ItemPedidoInputDTO;
import com.algaworks.algafood.api.v1.dto.model.EnderecoModelDTO;
import com.algaworks.algafood.domain.model.Endereco;
import com.algaworks.algafood.domain.model.ItemPedido;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        var modelMapper = new ModelMapper();

        var enderecoToEnderecoModelTypeMap = modelMapper.createTypeMap(Endereco.class, EnderecoModelDTO.class);
        enderecoToEnderecoModelTypeMap.<String>addMapping(
                 enderecoSrc -> enderecoSrc.getCidade().getEstado().getNome(),
                (enderecoModelDest, value) -> enderecoModelDest.getCidade().setEstado(value)
        );

        modelMapper.createTypeMap(ItemPedidoInputDTO.class, ItemPedido.class).addMappings(mapper -> mapper.skip(ItemPedido::setId));

        return modelMapper;
    }
}
