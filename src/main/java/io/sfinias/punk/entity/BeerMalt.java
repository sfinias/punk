package io.sfinias.punk.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "beer_malt")
public class BeerMalt {

    @EmbeddedId
    private BeerMaltId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("beerId")
    private Beer beer;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("maltId")
    private Malt malt;

    @Column(name = "amount_value")
    private BigDecimal amountValue;

    @Column(name = "amount_unit")
    private String amountUnit;

    public BeerMalt() {

    }

    public BeerMalt(Beer beer, Malt malt) {

        this.beer = beer;
        this.malt = malt;
        this.id = new BeerMaltId(beer.getId(), malt.getId());
    }

    public BeerMaltId getId() {

        return id;
    }

    public void setId(BeerMaltId id) {

        this.id = id;
    }

    public Beer getBeer() {

        return beer;
    }

    public void setBeer(Beer beer) {

        this.beer = beer;
    }

    public Malt getMalt() {

        return malt;
    }

    public void setMalt(Malt malt) {

        this.malt = malt;
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

    @Embeddable
    public static class BeerMaltId implements Serializable {

        @Column(name = "beer_id")
        private Long beerId;

        @Column(name = "malt_id")
        private Long maltId;

        public BeerMaltId() {

        }

        public BeerMaltId(Long beerId, Long maltId) {

            this.beerId = beerId;
            this.maltId = maltId;
        }

        public Long getBeerId() {

            return beerId;
        }

        public void setBeerId(Long beerId) {

            this.beerId = beerId;
        }

        public Long getMaltId() {

            return maltId;
        }

        public void setMaltId(Long maltId) {

            this.maltId = maltId;
        }

        @Override
        public boolean equals(Object o) {

            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            BeerMaltId that = (BeerMaltId) o;
            return Objects.equals(beerId, that.beerId) && Objects.equals(maltId, that.maltId);
        }

        @Override
        public int hashCode() {

            return Objects.hash(beerId, maltId);
        }
    }
}
