package fontys.esd.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Beer.
 */
@Entity
@Table(name = "beer")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Beer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "price", nullable = false)
    private Float price;

    @NotNull
    @Column(name = "quantity", nullable = false)
    private Float quantity;

    @ManyToOne
    private BeverageChart beverageChart;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "beer_property",
               joinColumns = @JoinColumn(name="beers_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="properties_id", referencedColumnName="id"))
    private Set<Property> properties = new HashSet<>();

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

    public Beer name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getPrice() {
        return price;
    }

    public Beer price(Float price) {
        this.price = price;
        return this;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Float getQuantity() {
        return quantity;
    }

    public Beer quantity(Float quantity) {
        this.quantity = quantity;
        return this;
    }

    public void setQuantity(Float quantity) {
        this.quantity = quantity;
    }

    public BeverageChart getBeverageChart() {
        return beverageChart;
    }

    public Beer beverageChart(BeverageChart beverageChart) {
        this.beverageChart = beverageChart;
        return this;
    }

    public void setBeverageChart(BeverageChart beverageChart) {
        this.beverageChart = beverageChart;
    }

    public Set<Property> getProperties() {
        return properties;
    }

    public Beer properties(Set<Property> properties) {
        this.properties = properties;
        return this;
    }

    public Beer addProperty(Property property) {
        this.properties.add(property);
        property.getBeers().add(this);
        return this;
    }

    public Beer removeProperty(Property property) {
        this.properties.remove(property);
        property.getBeers().remove(this);
        return this;
    }

    public void setProperties(Set<Property> properties) {
        this.properties = properties;
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
        Beer beer = (Beer) o;
        if (beer.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), beer.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Beer{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", price='" + getPrice() + "'" +
            ", quantity='" + getQuantity() + "'" +
            "}";
    }
}
