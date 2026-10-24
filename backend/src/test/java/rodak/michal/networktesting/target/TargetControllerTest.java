package rodak.michal.networktesting.target;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import rodak.michal.networktesting.target.dto.TargetRequest;
import rodak.michal.networktesting.target.dto.TargetResponse;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@WebMvcTest(TargetController.class)
class TargetControllerTest {

    @Autowired
    MockMvcTester mvc;

    @MockitoBean
    TargetService service;

    @Test
    void createRejectsPortOutOfRange() {
        var result = mvc.post().uri("/api/targets")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                        "name": "lab", 
                        "address": "10.0.0.1",
                        "port": 70000
                        }
                        """);

        assertThat(result).hasStatus(HttpStatus.BAD_REQUEST);
        assertThat(result).bodyJson().extractingPath("$.errors.port").isNotNull();
        verify(service, never()).create(any());
    }

    @Test
    void createUsesDefaultPortWhenMissing() {
        when(service.create(any(TargetRequest.class)))
                .thenAnswer(inv -> {
                    TargetRequest request = inv.getArgument(0);
                    return new TargetResponse(1L, request.name(), request.address(), request.port());
                });

        var result = mvc.post().uri("/api/targets")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {"name": "lab", "address": "10.0.0.1"}
                        """);

        assertThat(result).hasStatus(HttpStatus.CREATED);
        assertThat(result).headers().containsHeader("Location");
        assertThat(result).bodyJson().extractingPath("$.port").isEqualTo(5201);
    }
}
