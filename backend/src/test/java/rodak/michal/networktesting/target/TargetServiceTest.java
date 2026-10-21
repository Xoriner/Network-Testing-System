package rodak.michal.networktesting.target;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import rodak.michal.networktesting.common.error.ConflictException;
import rodak.michal.networktesting.common.error.NotFoundException;
import rodak.michal.networktesting.target.dto.TargetRequest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TargetServiceTest {

    @Mock
    TargetRepository repository;

    @InjectMocks
    TargetService service;

    @Test
    void createRejectsDuplicate() {
        // given
        when(repository.existsByAddressAndPort("10.0.0.1", 5201)).thenReturn(true);
        TargetRequest request = new TargetRequest("lab", "10.0.0.1", 5201);

        // when and then
        assertThatThrownBy(() -> service.create(request))
                .isInstanceOf(ConflictException.class);
        verify(repository, never()).save(any());
    }

    @Test
    void findByIdThrowsWhenMissing() {
        when(repository.findById(42L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.getById(42L))
                .isInstanceOf(NotFoundException.class);
    }
}