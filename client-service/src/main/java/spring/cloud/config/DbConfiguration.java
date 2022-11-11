package spring.cloud.config;

import io.r2dbc.pool.ConnectionPool;
import io.r2dbc.pool.ConnectionPoolConfiguration;
import io.r2dbc.postgresql.PostgresqlConnectionConfiguration;
import io.r2dbc.postgresql.PostgresqlConnectionFactory;
import io.r2dbc.postgresql.codec.EnumCodec;
import io.r2dbc.spi.ConnectionFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import spring.cloud.converter.EnumConverter;
import spring.cloud.model.entity.component.ContactType;

@Configuration
@RequiredArgsConstructor
public class DbConfiguration extends AbstractR2dbcConfiguration {

    @Value("${db.host}")
    private String host;
    @Value("${db.port}")
    private Integer port;
    @Value("${db.username}")
    private String username;
    @Value("${db.password}")
    private String password;
    @Value("${db.schema}")
    private String schema;
    @Value("${db.database}")
    private String database;
    @Value("${db.application-name}")
    private String applicationName;
    @Value("${db.pool.initial-size}")
    private Integer poolInitialSize;
    @Value("${db.pool.max-size}")
    private Integer poolMaxSize;

    @Override
    protected List<Object> getCustomConverters() {
        return List.of(new EnumConverter.ContactTypeEnumConverter());
    }

    @Override
    @Bean
    public ConnectionFactory connectionFactory() {
        var basicConnectionFactory = new PostgresqlConnectionFactory(
                PostgresqlConnectionConfiguration.builder()
                        .host(host)
                        .port(port)
                        .username(username)
                        .password(password)
                        .database(database)
                        .schema(schema)
                        .applicationName(applicationName)
                        .preparedStatementCacheQueries(0)
                        .codecRegistrar(EnumCodec.builder()
                                .withEnum("contact_type", ContactType.class)
                                .build())
                        .build());

        var poolConfiguration = ConnectionPoolConfiguration.builder(basicConnectionFactory)
                .initialSize(poolInitialSize)
                .maxSize(poolMaxSize)
                .build();

        return new ConnectionPool(poolConfiguration);
    }

}
