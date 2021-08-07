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

    @Column(name = "writer_status")
    @Enumerated(EnumType.STRING)
    private status status;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private GroupBoardEntity groupBoard;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "exchange_id")
    private ClientExchangeEntity exchange;

    @OneToMany(mappedBy = "writerExchangeEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @BatchSize(size = 1000)
    private List<ClientExchangeEntity> clients;

    public enum status{
        wait, process, complete
    }

    public void updateStatus(status status){
        this.status = status;
    }

    public void selectExchangeEntity(ClientExchangeEntity exchange){
        this.exchange = exchange;
    }

}
