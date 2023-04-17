package ar.com.mercadolibre.ipgeolocation.domain.entity;

import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "country_statistics_data")
public class CountryStatisticsDataEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank
    @Column(name = "name")
    private String name;

    @Column(name = "distance")
    private Integer distance;

    @Column(name = "invocations")
    private Long invocations = 1L;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updateAt = LocalDateTime.now();

    public CountryStatisticsDataEntity() {
    }

    public CountryStatisticsDataEntity(String name, Integer distance) {
        this.name = name;
        this.distance = distance;
    }

    public CountryStatisticsDataEntity(Long id, String name, Integer distance, Long invocations, LocalDateTime createdAt, LocalDateTime updateAt) {
        this.id = id;
        this.name = name;
        this.distance = distance;
        this.invocations = invocations;
        this.createdAt = createdAt;
        this.updateAt = updateAt;
    }

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

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    public Long getInvocations() {
        return invocations;
    }

    public void setInvocations(Long invocations) {
        this.invocations = invocations;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(LocalDateTime updateAt) {
        this.updateAt = updateAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CountryStatisticsDataEntity)) return false;
        CountryStatisticsDataEntity that = (CountryStatisticsDataEntity) o;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getName(), that.getName()) && Objects.equals(getDistance(), that.getDistance()) && Objects.equals(getInvocations(), that.getInvocations()) && Objects.equals(getCreatedAt(), that.getCreatedAt()) && Objects.equals(getUpdateAt(), that.getUpdateAt());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getDistance(), getInvocations(), getCreatedAt(), getUpdateAt());
    }
}
