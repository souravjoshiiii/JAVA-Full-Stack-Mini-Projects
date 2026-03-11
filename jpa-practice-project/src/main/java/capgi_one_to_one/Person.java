package capgi_one_to_one;

import jakarta.persistence.*;

@Entity(name = "PanPerson")
@Table(name = "person")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToOne
    @JoinColumn(name = "pan_id", unique = true)
    private Pan pan;

    public Person() {}

    public Person(String name) {
        this.name = name;
    }

    public void setPan(Pan pan) {
        this.pan = pan;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public Pan getPan() { return pan; }
}
