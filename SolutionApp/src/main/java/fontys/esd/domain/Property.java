package fontys.esd.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Property.
 */
@Entity
@Table(name = "property")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Property implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @ManyToMany(mappedBy = "properties")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Beer> beers = new HashSet<>();

    @ManyToMany(mappedBy = "properties")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Wine> wines = new HashSet<>();

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

    public Property name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Beer> getBeers() {
        return beers;
    }

    public Property beers(Set<Beer> beers) {
        this.beers = beers;
        return this;
    }

    public Property addBeer(Beer beer) {
        this.beers.add(beer);
        beer.getProperties().add(this);
        return this;
    }

    public Property removeBeer(Beer beer) {
        this.beers.remove(beer);
        beer.getProperties().remove(this);
        return this;
    }

    public void setBeers(Set<Beer> beers) {
        this.beers = beers;
    }

    public Set<Wine> getWines() {
        return wines;
    }

    public Property wines(Set<Wine> wines) {
        this.wines = wines;
        return this;
    }

    public Property addWine(Wine wine) {
        this.wines.add(wine);
        wine.getProperties().add(this);
        return this;
    }

    public Property removeWine(Wine wine) {
        this.wines.remove(wine);
        wine.getProperties().remove(this);
        return this;
    }

    public void setWines(Set<Wine> wines) {
        this.wines = wines;
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
        Property property = (Property) o;
        if (property.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), property.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Property{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
