package spring.cloud.config.property;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class ProxyModuleProperty {

    @Value("${api.path.additional}")
    private String additional;
    @Value("${api.path.clients}")
    private String clientsPath;
    @Value("${api.path.client-by-id}")
    private String clientByIdPath;

}
