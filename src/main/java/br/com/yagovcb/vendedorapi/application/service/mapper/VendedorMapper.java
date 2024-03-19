package br.com.yagovcb.vendedorapi.application.service.mapper;

import br.com.yagovcb.vendedorapi.application.dto.FilialDTO;
import br.com.yagovcb.vendedorapi.application.dto.VendedorDTO;
import br.com.yagovcb.vendedorapi.domain.model.Filial;
import br.com.yagovcb.vendedorapi.domain.model.Vendedor;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Vendedor} and its DTO {@link VendedorDTO}.
 */
@Mapper(componentModel = "spring")
public interface VendedorMapper extends EntityMapper<VendedorDTO, Vendedor> {
    @Mapping(target = "filial", source = "filial", qualifiedByName = "filialId")
    VendedorDTO toDto(Vendedor s);

    @Named("filialId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    FilialDTO toDtoFilialId(Filial filial);
}
