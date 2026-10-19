package rodak.michal.networktesting.target;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rodak.michal.networktesting.target.dto.TargetResponse;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TargetService {

    private final TargetRepository targetRepository;

    @Transactional(readOnly = true)
    public List<TargetResponse> findAll() {
        return targetRepository.findAll().stream().map(TargetService::toResponse).toList();
    }

    private static TargetResponse toResponse(Target target) {
        return new TargetResponse(
                target.getId(),
                target.getName(),
                target.getAddress(),
                target.getPort()
        );
    }
}
