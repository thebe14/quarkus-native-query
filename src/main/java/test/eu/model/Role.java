package test.eu.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import test.eu.entity.RoleEntity;


/***
 * The roles that will govern access to the features in section Service Level Management
 */
public class Role extends VersionInfo {

    @Schema(enumeration={ "Role" })
    public String kind = "Role";

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public Long id;

    public String role; // One of the constants from above

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public String name; // Human-readable version of the role field


    /***
     * Constructor
     */
    public Role() {}

    /***
     * Copy constructor
     * @param role The entity to copy
     */
    public Role(RoleEntity role) {

        this.id = role.id;
        this.role = role.role;
        this.name = role.name;

        this.version = role.version;
        this.changedOn = role.changedOn;
        this.changeDescription = role.changeDescription;
        if(null != role.changeBy)
            this.changeBy = new User(role.changeBy);
    }
}
