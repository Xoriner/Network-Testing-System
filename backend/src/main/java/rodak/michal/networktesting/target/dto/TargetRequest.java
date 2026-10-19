package rodak.michal.networktesting.target.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record TargetRequest(
        @NotBlank @Size(max = 100)
        String name,

        @NotBlank @Size(max = 255)
        @Pattern(regexp = "^[A-Za-z0-9][A-Za-z0-9.:-]*$",
                message = "must be an IP address or a hostname")
        String address,

        @Min(1) @Max(65535)
        int port
) {}
