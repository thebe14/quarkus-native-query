package test.eu.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import test.eu.entity.UserEntity;

/**
 * Details of some user
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public Long id;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public String fullName;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public String email;


    /***
     * Constructor
     */
    public User() {}

    /***
     * Copy constructor
     */
    public User(UserEntity u) {
        super();

        if(null != u) {
            this.id = u.id;
            this.fullName = u.fullName;
            this.email = u.email;
        }
    }
}
