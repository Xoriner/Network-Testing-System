package rodak.michal.networktesting.target;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rodak.michal.networktesting.common.error.ConflictException;
import rodak.michal.networktesting.common.error.NotFoundException;
import rodak.michal.networktesting.target.dto.TargetRequest;
import rodak.michal.networktesting.target.dto.TargetResponse;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TargetService {

    private final TargetRepository targetRepository;

    @Transactional
    public TargetResponse create(TargetRequest request) {
        if (targetRepository.existsByAddressAndPort(request.address(), request.port())) {
            throw new ConflictException(
                    "Target " + request.address() + ":" + request.port() + " already exists");
        }
        Target saved = targetRepository.save(
                new Target(request.name(), request.address(), request.port()));
        return toResponse(saved);
    }

    @Transactional(readOnly = true)
    public List<TargetResponse> findAll() {
        return targetRepository.findAll().stream().map(TargetService::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public TargetResponse getById(Long id) {
        return toResponse(getEntity(id));
    }

    @Transactional
    public void delete(Long id) {
        targetRepository.delete(getEntity(id));
    }

    //TODO consider if valid for other module (JobService),
    // that needs the entity
    @Transactional(readOnly = true)
    public Target getEntity(Long id) {
        return targetRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Target " + id + " not found"));
    }

    //TODO split into seperate mapper
    private static TargetResponse toResponse(Target target) {
        return new TargetResponse(
                target.getId(),
                target.getName(),
                target.getAddress(),
                target.getPort()
        );
    }
}
