package rodak.michal.networktesting.target.dto;

public record TargetResponse(
        Long id,
        String name,
        String address,
        int port
) {}
