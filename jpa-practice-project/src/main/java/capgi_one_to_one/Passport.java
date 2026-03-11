package capgi_one_to_one;

import jakarta.persistence.*;

@Entity
public class Passport {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
private Long id;
    private String passportNumber;
    @OneToOne(mappedBy = "passport")
private Human human;
    public Passport(){}
public Passport(String passportNumber)
{
    this.passportNumber=passportNumber;
}
public void setHuman(Human human)
{
    this.human=human;
}
public Long getId(){
        return id;
}
public String getPassportNumber()
{
    return passportNumber;
}
public Human getHuman()
{
    return human;
}
}