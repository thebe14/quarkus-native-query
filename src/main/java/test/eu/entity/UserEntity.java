package test.eu.entity;

import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import jakarta.persistence.*;

import test.eu.model.User;


/**
 * Details of some user
 */
@Entity
@Cacheable
@Table(name = "users")
public class UserEntity extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String fullName;

    public String email;


    /***
     * Constructor
     */
    public UserEntity() { super(); }

    /***
     * Copy constructor
     */
    public UserEntity(User user) {
        super();

        this.fullName = user.fullName;
        this.email = user.email;
    }
}
