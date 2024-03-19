package br.com.yagovcb.vendedorapi.application.service.mapper;

import br.com.yagovcb.vendedorapi.application.dto.FilialDTO;
import br.com.yagovcb.vendedorapi.domain.model.Filial;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Filial} and its DTO {@link FilialDTO}.
 */
@Mapper(componentModel = "spring")
public interface FilialMapper extends EntityMapper<FilialDTO, Filial> {}
