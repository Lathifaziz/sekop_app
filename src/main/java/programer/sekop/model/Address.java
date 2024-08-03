package programer.sekop.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "addresses")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Address {
    @Id
    private String id;

    private String Street;

    private String city;

    private String Country;
    private String Province;
    @Column(name = "postal_code")
    private String postalCode;

    @ManyToOne
    @JoinColumn( name = "contact_id", referencedColumnName = "id")
    private Contact contact;

}
