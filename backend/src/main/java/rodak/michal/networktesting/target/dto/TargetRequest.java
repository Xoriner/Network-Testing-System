package rodak.michal.networktesting.target.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record TargetRequest(
        @Schema(example = "lab-server")
        @NotBlank @Size(max = 100)
        String name,

        @Schema(example = "192.168.1.10")
        @NotBlank @Size(max = 255)
        @Pattern(regexp = "^[A-Za-z0-9][A-Za-z0-9.:-]*$",
                message = "must be an IP address or a hostname")
        String address,

        @Schema(example = "5201",
                description = "server port, defaults to 5201")
        @Min(1) @Max(65535)
        Integer port
) {
        public TargetRequest {
                if(port == null) port = 5201;
        }
}
