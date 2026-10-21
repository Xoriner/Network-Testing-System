package rodak.michal.networktesting.target;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import rodak.michal.networktesting.target.dto.TargetRequest;
import rodak.michal.networktesting.target.dto.TargetResponse;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/targets")
@Tag(name = "Targets API")
public class TargetController {

    private final TargetService service;

    @GetMapping
    public List<TargetResponse> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public TargetResponse getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    @Operation(summary = "Create a new target",
            description = "Adds a new target if the address and port combination does not exist.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Target created successfully"),
            @ApiResponse(responseCode = "400",
                    description = "Invalid input"),
            @ApiResponse(responseCode = "409",
                    description = "Target with this address and port already exists")
    })
    public ResponseEntity<TargetResponse> create(@Valid @RequestBody TargetRequest request) {
        TargetResponse created = service.create(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(created.id()).toUri();
        return ResponseEntity.created(location).body(created);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}