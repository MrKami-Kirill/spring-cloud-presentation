package spring.cloud.config.property;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Component
@Getter
@RefreshScope
public class ProxyModuleProperty {

    @Value("${api.path.additional}")
    private String additional;
    @Value("${api.path.clients}")
    private String clientsPath;
    @Value("${api.path.client-by-id}")
    private String clientByIdPath;
    @Value("${api.path.client-contacts}")
    private String clientContactsPath;
    @Value("${api.path.add-client}")
    private String addClientPath;
    @Value("${api.path.change-client}")
    private String changeClientPath;
    @Value("${api.path.add-contact-to-client}")
    private String addContactToClientPath;
    @Value("${api.path.delete-client}")
    private String deleteClientPath;
    @Value("${api.path.delete-client-contact}")
    private String deleteClientContactPath;

}
