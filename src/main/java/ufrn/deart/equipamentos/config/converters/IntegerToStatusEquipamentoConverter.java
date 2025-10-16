package ufrn.deart.equipamentos.config.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import ufrn.deart.equipamentos.model.StatusEquipamento;

@ReadingConverter
public class IntegerToStatusEquipamentoConverter implements Converter<Integer, StatusEquipamento> {

    @Override
    public StatusEquipamento convert(Integer source) {
        if (source == null) {
            return null;
        }
        return StatusEquipamento.of(source);
    }
}