package test.eu.entity;

import org.hibernate.annotations.UpdateTimestamp;
import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import io.smallrye.common.constraint.NotNull;
import io.smallrye.mutiny.Uni;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.*;

import test.eu.model.Role;
import org.hibernate.reactive.mutiny.Mutiny;


/**
 * Details of a role
 */
@Entity
@Table(name = "roles")
public class RoleEntity extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(length = 50)
    @NotNull
    public String role;

    @Column(length = 50)
    @NotNull
    public String name; // Human-readable version of the role field

    // Change tracking
    public int version = 1;

    @UpdateTimestamp
    public LocalDateTime changedOn;

    @Column(length = 2048)
    public String changeDescription;

    @ManyToOne(fetch = FetchType.EAGER,
            cascade = { CascadeType.PERSIST })
    @JoinTable(name = "role_editor_map",
            joinColumns = { @JoinColumn(name = "role_id") },
            inverseJoinColumns = { @JoinColumn(name = "user_id") })
    public UserEntity changeBy = null;


    /***
     * Constructor
     */
    public RoleEntity() {
        super();
    }

    /***
     * Copy constructor
     * @param role The new version (from the frontend)
     */
    public RoleEntity(Role role) {
        super();

        // Copy simple fields
        this.changeDescription = role.changeDescription;
        this.version = role.version + 1;

        this.role = role.role;
        this.name = role.name;
    }

    /***
     * Get all versions of all roles
     * @return All role entities, sorted in reverse chronological order (head of the list is the latest).
     */
    public static Uni<List<RoleEntity>> getAllRoles(Mutiny.Session session) {
        // Get only the last versions (without history)
        final String sql = """
            SELECT * from test.roles\s
                JOIN (\s
                    SELECT DISTINCT ON (role)\s
                        last_value(id) OVER wnd as last_id\s
                    FROM test.roles WINDOW wnd AS (\s
                        PARTITION BY role ORDER BY version ROWS BETWEEN UNBOUNDED PRECEDING AND UNBOUNDED FOLLOWING\s
                    )\s
                ) as last\s
            ON id = last_id
            """;

        var query = session.createNativeQuery(sql, RoleEntity.class);
        return query.getResultList();
    }

    /***
     * Get the last version of a role
     * @return Role entity
     */
    public static Uni<RoleEntity> getRoleLastVersion(String role) {
        return find("role = ?1 ORDER BY version DESC", role).firstResult();
    }

    /***
     * Get all versions of a role
     * @return Role entities
     */
    public static Uni<List<RoleEntity>> getRoleAllVersions(String role) {
        return list("role = ?1 ORDER BY version DESC", role);
    }
}
