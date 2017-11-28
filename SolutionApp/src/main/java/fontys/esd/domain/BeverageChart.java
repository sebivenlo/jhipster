package fontys.esd.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A BeverageChart.
 */
@Entity
@Table(name = "beverage_chart")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class BeverageChart implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "intro", nullable = false)
    private String intro;

    @ManyToOne
    private User user;

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public BeverageChart name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntro() {
        return intro;
    }

    public BeverageChart intro(String intro) {
        this.intro = intro;
        return this;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public User getUser() {
        return user;
    }

    public BeverageChart user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }
    // jhipster-needle-entity-add-getters-setters - Jhipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BeverageChart beverageChart = (BeverageChart) o;
        if (beverageChart.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), beverageChart.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BeverageChart{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", intro='" + getIntro() + "'" +
            "}";
    }
}
