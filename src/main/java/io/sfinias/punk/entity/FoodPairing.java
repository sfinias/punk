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

@Entity
@Table(name = "food_pairing")
public class FoodPairing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "beer_id")
    private Beer beer;

    @Column(name = "name")
    private String name;

    public FoodPairing() {

    }

    public FoodPairing(String name) {

        this.name = name;
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

    public String getName() {

        return name;
    }

    public void setName(String food) {

        this.name = food;
    }
}
