package com.example.kakaomap.entity;

import lombok.*;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.springframework.context.annotation.Lazy;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "tbl_group_exchange")
public class WriterExchangeEntity extends BaseTimeEntity {

    @Column(name = "writer_id")
    @Id @GeneratedValue
    private Long id;

    @Column(name = "writer_location")
    private String location;

    @Column(name = "writer_longitude") // 경도
    private String longitude;

    @Column(name = "writer_latitude") // 위도
    private String latitude;

    @Column(name = "writer_prefer_time")
    private String exchangeTime;

    @Column(name = "writer_residence")
    private String residence;

    @OneToMany(mappedBy = "writerExchangeEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<WriterClientJoinEntity> writerClientJoinEntity;

}
