package com.example.kakaomap.entity;

import lombok.*;
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

    @Column(name = "writer_preper_time")
    private String exchangeTime;

    @Column(name = "writer_residence")
    private String residence;

    @OneToOne(mappedBy = "exchange", fetch = FetchType.LAZY)
    private GroupBoardEntity groupBoard;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "process_id")
    private ExchangeProcessEntity exchangeProcessEntity;

    @OneToMany(mappedBy = "writerExchangeEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ClientExchangeEntity> clients;

}
