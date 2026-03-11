package capgi_one_to_one;

import jakarta.persistence.*;

@Entity
@Table(name="human")
public class Human{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToOne
    @JoinColumn(name="passport_id", unique=true)
    private Passport passport;
    public Human(){}
    public Human(String name)
    {
        this.name=name;
    }
    public void setPassport(Passport passport)
    {
        this.passport=passport;
        passport.setHuman(this);
    }
    public Long getId()
    {
        return id;
    }
    public String getName(){
        return name;
    }
    public Passport getPassport()
    {
        return passport;
    }
}
