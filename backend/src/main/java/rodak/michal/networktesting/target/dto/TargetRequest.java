package rodak.michal.networktesting.target.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public record TargetRequest(
        @NotBlank String name,
        @NotBlank String address,
        @Min(1) @Max(65535) int port
) {}
