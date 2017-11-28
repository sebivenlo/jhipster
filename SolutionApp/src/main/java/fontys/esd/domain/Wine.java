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
 * A Wine.
 */
@Entity
@Table(name = "wine")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Wine implements Serializable {

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
    @JoinTable(name = "wine_property",
               joinColumns = @JoinColumn(name="wines_id", referencedColumnName="id"),
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

    public Wine name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getPrice() {
        return price;
    }

    public Wine price(Float price) {
        this.price = price;
        return this;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Float getQuantity() {
        return quantity;
    }

    public Wine quantity(Float quantity) {
        this.quantity = quantity;
        return this;
    }

    public void setQuantity(Float quantity) {
        this.quantity = quantity;
    }

    public BeverageChart getBeverageChart() {
        return beverageChart;
    }

    public Wine beverageChart(BeverageChart beverageChart) {
        this.beverageChart = beverageChart;
        return this;
    }

    public void setBeverageChart(BeverageChart beverageChart) {
        this.beverageChart = beverageChart;
    }

    public Set<Property> getProperties() {
        return properties;
    }

    public Wine properties(Set<Property> properties) {
        this.properties = properties;
        return this;
    }

    public Wine addProperty(Property property) {
        this.properties.add(property);
        property.getWines().add(this);
        return this;
    }

    public Wine removeProperty(Property property) {
        this.properties.remove(property);
        property.getWines().remove(this);
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
        Wine wine = (Wine) o;
        if (wine.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), wine.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Wine{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", price='" + getPrice() + "'" +
            ", quantity='" + getQuantity() + "'" +
            "}";
    }
}
