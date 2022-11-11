package spring.cloud.handler;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import spring.cloud.config.property.ClientServiceProperty;
import spring.cloud.model.dto.request.AddClient;
import spring.cloud.model.dto.request.AddContact;
import spring.cloud.repository.TClientRepository;
import spring.cloud.repository.TContactRepository;

@SpringBootTest
public class RouterHandlerTest {

    @Autowired
    private ApplicationContext context;
    @Autowired
    private ClientServiceProperty property;
    @Autowired
    private ValidationHandler<AddClient> addClientValidationHandler;
    @Autowired
    private ValidationHandler<AddContact> addContactValidationHandler;
    @MockBean
    private TClientRepository tClientRepository;
    @MockBean
    private TContactRepository tContactRepository;

    private WebTestClient webTestClient;

    @BeforeEach
    public void setUp() {
        webTestClient = WebTestClient.bindToApplicationContext(context)
                .build();
        //when(tClientRepository.findAll()).thenReturn();
        //when(tClientRepository.findById(anyLong())).thenReturn();
        when(tClientRepository.deleteById(anyLong())).thenReturn(Mono.empty());
        //when(tContactRepository.findByClientId(anyLong())).thenReturn();
        when(tContactRepository.deleteAllByClientId(anyLong())).thenReturn(Mono.empty());
        when(tContactRepository.delete(any())).thenReturn(Mono.empty());
    }


}
