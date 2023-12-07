package com.example.continuesaggregatesample.dummy;

import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;

@Entity
@Table(name = "related_dummy")
@Data
//@IdClass(GeneralId.class)
public class RelatedDummy {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

//    @Id
    private Instant time;

    @ManyToOne
    @JoinColumn(name = "dummy_id", referencedColumnName = "id")
    private Dummy dummy;

    private Integer othertestone;

    private Integer othertesttwo;

}