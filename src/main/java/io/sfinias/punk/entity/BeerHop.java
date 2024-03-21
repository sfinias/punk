package io.sfinias.punk.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "beer_hop")
public class BeerHop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "beer_id")
    private Beer beer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hop_id")
    private Hop hop;

    @Column(name = "add")
    private String add;

    @Column(name = "amount_value")
    private BigDecimal amountValue;

    @Column(name = "amount_unit")
    private String amountUnit;

    @Column(name = "attribute")
    private String attribute;

    public BeerHop() {

    }

    public BeerHop(Beer beer, Hop hop) {

        this.beer = beer;
        this.hop = hop;
    }

    public Long getId() {

        return id;
    }

    public void setId(Long id) {

        this.id = id;
    }

    public Beer getBeer() {

        return beer;
    }

    public void setBeer(Beer beer) {

        this.beer = beer;
    }

    public Hop getHop() {

        return hop;
    }

    public void setHop(Hop hop) {

        this.hop = hop;
    }

    public String getAdd() {

        return add;
    }

    public void setAdd(String add) {

        this.add = add;
    }

    public BigDecimal getAmountValue() {

        return amountValue;
    }

    public void setAmountValue(BigDecimal amountValue) {

        this.amountValue = amountValue;
    }

    public String getAmountUnit() {

        return amountUnit;
    }

    public void setAmountUnit(String amountUnit) {

        this.amountUnit = amountUnit;
    }

    public String getAttribute() {

        return attribute;
    }

    public void setAttribute(String attribute) {

        this.attribute = attribute;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BeerHop beerHop = (BeerHop) o;
        return Objects.equals(id, beerHop.id) && Objects.equals(beer, beerHop.beer) && Objects.equals(hop, beerHop.hop) && Objects.equals(amountValue, beerHop.amountValue) && Objects.equals(amountUnit, beerHop.amountUnit) && Objects.equals(attribute, beerHop.attribute);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, beer, hop, amountValue, amountUnit, attribute);
    }
}
