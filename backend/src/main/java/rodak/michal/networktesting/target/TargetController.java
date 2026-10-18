package rodak.michal.networktesting.target;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/targets")
@Tag(name = "Targets API")
public class TargetController {

    private final TargetRepository targetRepository;

    @GetMapping()
    List<Target> getTargets() {
        return targetRepository.findAll();
    }
}
