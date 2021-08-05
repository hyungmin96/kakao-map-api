package com.example.kakaomap.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.List;
import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tbl_client_exchange")
public class ClientExchangeEntity extends BaseTimeEntity {

    @Column(name = "client_id")
    @Id @GeneratedValue
    private Long id;

    @Column(name = "client_location")
    private String location;

    @Column(name = "client_longitude") // 경도
    private String longitude;

    @Column(name = "client_latitude") // 위도
    private String latitude;

    @Column(name = "client_preper_time")
    private String exchangeTime;

    @Column(name = "client_residence")
    private String residence;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "process_id")
    private ExchangeProcessEntity exchangeProcessEntity;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "writer_id")
    private WriterExchangeEntity writerExchangeEntity;

    @OneToMany(mappedBy = "client", fetch = LAZY, cascade = CascadeType.ALL)
    private List<ExchangeFileEntity> files;

}
