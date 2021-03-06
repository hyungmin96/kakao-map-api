package com.example.kakaomap.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "tbl_writer_client_join")
public class WriterClientJoinEntity {

    @Id @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "writer_id")
    private Long writerId;

    @Column(name = "client_id")
    private Long clientId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "writer_exchange_id")
    private WriterExchangeEntity writerExchangeEntity;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "client_exchange_id")
    private ClientExchangeEntity clientExchangeEntity;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private status status;

    public enum status{
        wait, process, complete
    }

    public void updateStatus(status status){
        this.status = status;
    }

}
