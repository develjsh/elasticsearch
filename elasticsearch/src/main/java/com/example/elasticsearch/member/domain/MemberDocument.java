package com.example.elasticsearch.member.domain;

import java.time.LocalDateTime;

import org.elasticsearch.action.support.replication.ReplicationTask.Status;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Mapping;
import org.springframework.data.elasticsearch.annotations.Setting;

import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Document(indexName = "member")
@Mapping(mappingPath = "elastic/member-mapping.json")
@Setting(settingPath = "elastic/member-setting.json")
public class MemberDocument {

    @Id
    private Long id;

    private String name;

    private String nickname;

    private int age;

    private Status status;

    private Zone zone;

    private String description;

    @Field(type = FieldType.Date, format = {DateFormat.date_hour_minute_second_millis, DateFormat.epoch_millis})
    private LocalDateTime createdAt;

    public static MemberDocument from(Member member){
        return MemberDocument.builder()
                .id(member.getId())
                .name(member.getName())
                .nickname(member.getNickname())
                .age(member.getAge())
                .status(member.getStatus())
                .zone(member.getZone())
                .description(member.getDescription())
                .createdAt(member.getCreatedAt())
                .build();
    }

}