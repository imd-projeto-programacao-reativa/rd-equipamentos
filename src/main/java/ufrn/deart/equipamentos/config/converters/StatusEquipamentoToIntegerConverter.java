package ufrn.deart.equipamentos.config.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;
import ufrn.deart.equipamentos.model.StatusEquipamento;

@WritingConverter
public class StatusEquipamentoToIntegerConverter implements Converter<StatusEquipamento, Integer> {

    @Override
    public Integer convert(StatusEquipamento source) {
        if (source == null) {
            return null;
        }
        return source.getCodigo();
    }
}