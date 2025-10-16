package ufrn.deart.equipamentos.config;

import io.r2dbc.spi.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.convert.R2dbcCustomConversions;
import ufrn.deart.equipamentos.config.converters.IntegerToStatusEquipamentoConverter;
import ufrn.deart.equipamentos.config.converters.StatusEquipamentoToIntegerConverter;

import java.util.ArrayList;
import java.util.List;
// ufrn/deart/equipamentos/config/R2dbcConfig.java

@Configuration
public class R2dbcConfig extends AbstractR2dbcConfiguration {

    private final ConnectionFactory connectionFactory;

    public R2dbcConfig(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Override
    public ConnectionFactory connectionFactory() {
        return this.connectionFactory;
    }

}