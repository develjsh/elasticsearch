package com.example.elasticsearch.zone;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;


@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Zone {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String mainZone;

    private String subZone;

    public static Zone of(String mainZone, String subZone){
        return Zone.builder()
                .mainZone(mainZone)
                .subZone(subZone)
                .build();
    }

}
