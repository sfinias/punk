package io.sfinias.punk.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "beer")
public class Beer {

    @Id
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "tagline")
    private String tagline;

    @Temporal(TemporalType.DATE)
    @Column(name = "first_brewed")
    private LocalDate firstBrewed;

    @Column(name = "description") // Assuming description can be long
    private String description;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "abv")
    private BigDecimal abv;

    @Column(name = "ibu")
    private BigDecimal ibu;

    @Column(name = "target_fg")
    private BigDecimal targetFg;

    @Column(name = "target_og")
    private BigDecimal targetOg;

    @Column(name = "ebc")
    private BigDecimal ebc;

    @Column(name = "srm")
    private BigDecimal srm;

    @Column(name = "ph")
    private BigDecimal ph;

    @Column(name = "attenuation_level")
    private BigDecimal attenuationLevel;

    @Column(name = "volume_value")
    private BigDecimal volumeValue;

    @Column(name = "volume_unit")
    private String volumeUnit;

    @Column(name = "boil_volume_value")
    private BigDecimal boilVolumeValue;

    @Column(name = "boil_volume_unit")
    private String boilVolumeUnit;

    @Column(name = "mash_temp_value")
    private BigDecimal mashTempValue;

    @Column(name = "mash_temp_unit")
    private String mashTempUnit;

    @Column(name = "mash_temp_duration")
    private Integer mashTempDuration;

    @Column(name = "fermentation_value")
    private BigDecimal fermentationValue;

    @Column(name = "fermentation_unit")
    private String fermentationUnit;

    @Column(name = "twist")
    private String twist;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "yeast_id")
    private Yeast yeast;

    @Column(name = "brewers_tips", length = 1024)
    private String brewersTips;

    @Column(name = "contributed_by")
    private String contributedBy;

    @OneToMany(
            mappedBy = "beer",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<BeerHop> hops = new ArrayList<>();

    @OneToMany(
            mappedBy = "beer",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<BeerMalt> malts = new ArrayList<>();

    @OneToMany(
            mappedBy = "beer",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<FoodPairing> foodPairings = new ArrayList<>();

    public Long getId() {

        return id;
    }

    public void setId(Long id) {

        this.id = id;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public String getTagline() {

        return tagline;
    }

    public void setTagline(String tagline) {

        this.tagline = tagline;
    }

    public LocalDate getFirstBrewed() {

        return firstBrewed;
    }

    public void setFirstBrewed(LocalDate firstBrewed) {

        this.firstBrewed = firstBrewed;
    }

    public String getDescription() {

        return description;
    }

    public void setDescription(String description) {

        this.description = description;
    }

    public String getImageUrl() {

        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {

        this.imageUrl = imageUrl;
    }

    public BigDecimal getAbv() {

        return abv;
    }

    public void setAbv(BigDecimal abv) {

        this.abv = abv;
    }

    public BigDecimal getIbu() {

        return ibu;
    }

    public void setIbu(BigDecimal ibu) {

        this.ibu = ibu;
    }

    public BigDecimal getTargetFg() {

        return targetFg;
    }

    public void setTargetFg(BigDecimal targetFg) {

        this.targetFg = targetFg;
    }

    public BigDecimal getTargetOg() {

        return targetOg;
    }

    public void setTargetOg(BigDecimal targetOg) {

        this.targetOg = targetOg;
    }

    public BigDecimal getEbc() {

        return ebc;
    }

    public void setEbc(BigDecimal ebc) {

        this.ebc = ebc;
    }

    public BigDecimal getSrm() {

        return srm;
    }

    public void setSrm(BigDecimal srm) {

        this.srm = srm;
    }

    public BigDecimal getPh() {

        return ph;
    }

    public void setPh(BigDecimal ph) {

        this.ph = ph;
    }

    public BigDecimal getAttenuationLevel() {

        return attenuationLevel;
    }

    public void setAttenuationLevel(BigDecimal attenuationLevel) {

        this.attenuationLevel = attenuationLevel;
    }

    public BigDecimal getVolumeValue() {

        return volumeValue;
    }

    public void setVolumeValue(BigDecimal volumeValue) {

        this.volumeValue = volumeValue;
    }

    public String getVolumeUnit() {

        return volumeUnit;
    }

    public void setVolumeUnit(String volumeUnit) {

        this.volumeUnit = volumeUnit;
    }

    public BigDecimal getBoilVolumeValue() {

        return boilVolumeValue;
    }

    public void setBoilVolumeValue(BigDecimal boilVolumeValue) {

        this.boilVolumeValue = boilVolumeValue;
    }

    public String getBoilVolumeUnit() {

        return boilVolumeUnit;
    }

    public void setBoilVolumeUnit(String boilVolumeUnit) {

        this.boilVolumeUnit = boilVolumeUnit;
    }

    public BigDecimal getMashTempValue() {

        return mashTempValue;
    }

    public void setMashTempValue(BigDecimal mashTempValue) {

        this.mashTempValue = mashTempValue;
    }

    public String getMashTempUnit() {

        return mashTempUnit;
    }

    public void setMashTempUnit(String mashTempUnit) {

        this.mashTempUnit = mashTempUnit;
    }

    public Integer getMashTempDuration() {

        return mashTempDuration;
    }

    public void setMashTempDuration(Integer mashTempDuration) {

        this.mashTempDuration = mashTempDuration;
    }

    public BigDecimal getFermentationValue() {

        return fermentationValue;
    }

    public void setFermentationValue(BigDecimal fermentationValue) {

        this.fermentationValue = fermentationValue;
    }

    public String getFermentationUnit() {

        return fermentationUnit;
    }

    public void setFermentationUnit(String fermentationUnit) {

        this.fermentationUnit = fermentationUnit;
    }

    public String getTwist() {

        return twist;
    }

    public void setTwist(String twist) {

        this.twist = twist;
    }

    public String getBrewersTips() {

        return brewersTips;
    }

    public void setBrewersTips(String brewersTips) {

        this.brewersTips = brewersTips;
    }

    public String getContributedBy() {

        return contributedBy;
    }

    public void setContributedBy(String contributedBy) {

        this.contributedBy = contributedBy;
    }

    public Yeast getYeast() {

        return yeast;
    }

    public void setYeast(Yeast yeast) {

        this.yeast = yeast;
    }

    public List<BeerHop> getHops() {

        return hops;
    }

    public void setHops(List<BeerHop> hops) {

        this.hops = hops;
    }

    public List<BeerMalt> getMalts() {

        return malts;
    }

    public void setMalts(List<BeerMalt> malts) {

        this.malts = malts;
    }

    public List<FoodPairing> getFoodPairings() {

        return foodPairings;
    }

    public void setFoodPairings(List<FoodPairing> foodPairings) {

        this.foodPairings = foodPairings;
    }

    public void addFoodPairing(FoodPairing foodPairing) {

        this.foodPairings.add(foodPairing);
        foodPairing.setBeer(this);
    }
}
