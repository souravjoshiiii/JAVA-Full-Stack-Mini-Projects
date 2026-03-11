package capgi_one_to_one;

import jakarta.persistence.*;

@Entity
@Table(name = "pan")
public class Pan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String panNumber;

    public Pan() {}

    public Pan(String panNumber) {
        this.panNumber = panNumber;
    }

    public Long getId() { return id; }
    public String getPanNumber() { return panNumber; }
}
