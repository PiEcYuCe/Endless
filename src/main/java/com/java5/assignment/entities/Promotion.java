    package com.java5.assignment.entities;

    import com.fasterxml.jackson.annotation.JsonIdentityInfo;
    import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
    import com.fasterxml.jackson.annotation.ObjectIdGenerators;
    import jakarta.persistence.*;
    import jakarta.validation.constraints.NotNull;
    import jakarta.validation.constraints.Size;
    import lombok.AllArgsConstructor;
    import lombok.Getter;
    import lombok.NoArgsConstructor;
    import lombok.Setter;
    import org.hibernate.annotations.Nationalized;

    import java.time.LocalDate;
    import java.util.LinkedHashSet;
    import java.util.Set;

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    @Entity
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @Table(name = "Promotions")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    public class Promotion {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "PromotionID", nullable = false)
        private Long id;

        @Size(max = 255)
        @NotNull
        @Nationalized
        @Column(name = "Name", nullable = false)
        private String name;

        @Nationalized
        @Lob
        @Column(name = "Description")
        private String description;

        @NotNull
        @Column(name = "StartDate", nullable = false)
        private LocalDate startDate;

        @NotNull
        @Column(name = "EndDate", nullable = false)
        private LocalDate endDate;

        @NotNull
        @Column(name = "Status", nullable = false)
        private Boolean status = false;

        @Size(max = 255)
        @Nationalized
        @Column(name = "Poster")
        private String poster;

        @OneToMany(mappedBy = "promotionID")
        private Set<PromotionDetail> promotionDetails = new LinkedHashSet<>();

    }