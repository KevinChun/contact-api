package homin.chun.contactlist.adapter.out.persistence.jpa.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "contact_info", schema = "public")
@NoArgsConstructor
@SequenceGenerator(name = "contact_info_seq", sequenceName = "contact_info_seq")
public class ContactEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "contact_info_seq")
    public Long id;

    @Column(nullable = false)
    public String name;

    @Column(nullable = false)
    public String email;

    @Column(nullable = false)
    public String phoneNumber;

    @Column(nullable = false)
    public String joinedDate;

    public ContactEntity(String name, String email, String phoneNumber, String joinedDate) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.joinedDate = joinedDate;
    }
}

