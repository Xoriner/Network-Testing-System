package rodak.michal.networktesting.target;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;

@Getter
@Entity
@Table(
        name = "target",
        uniqueConstraints = @UniqueConstraint(columnNames = {"address", "port"})
)
public class Target {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Setter
    private String name;

    @Column(nullable = false)
    @Setter
    private String address;

    @Column(nullable = false)
    @Setter
    private int port;

    protected Target() {}

    public Target(String name, String address, int port) {
        this.name = name;
        this.address = address;
        this.port = port;
    }
}