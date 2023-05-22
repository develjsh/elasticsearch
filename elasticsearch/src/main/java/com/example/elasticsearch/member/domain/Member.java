package com.example.elasticsearch.member.domain;

import org.elasticsearch.tasks.Task.Status;

import com.example.elasticsearch.BaseEntity;
import com.example.elasticsearch.zone.Zone;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String nickname;
    private int age;
    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    @JoinColumn(name = "zone_id")
    private Zone zone;

    private String description;

    public static Member from (MemberSaveRequest memberSaveRequest){
        return Member.builder()
                .name(memberSaveRequest.getName())
                .nickname(memberSaveRequest.getNickname())
                .age(memberSaveRequest.getAge())
                .status()
                .zone(Zone.builder().id(memberSaveRequest.getZoneId()).build())
                .description(memberSaveRequest.getDescription())
                .build();
    }
}