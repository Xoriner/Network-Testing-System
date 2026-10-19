package rodak.michal.networktesting.target;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TargetRepository extends JpaRepository<Target, Long> {

    boolean existsByAddressAndPort(String address, int port);
}