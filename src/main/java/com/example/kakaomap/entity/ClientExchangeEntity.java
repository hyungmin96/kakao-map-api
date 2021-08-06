package com.example.kakaomap.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;
import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Table(name = "tbl_client_exchange")
public class ClientExchangeEntity extends BaseTimeEntity {

    @Column(name = "client_id")
    @Id @GeneratedValue
    private Long id;

    @Column(name = "client_user_id")
    private Long userId;

    @Column(name = "client_cotent")
    private String content;

    @Column(name = "client_price")
    private String price;

    @Column(name = "client_request")
    private String request;

    @ManyToOne(fetch = LAZY) // 교환을 요청한 게시글의 info entity
    @JoinColumn(name = "writer_id")
    private WriterExchangeEntity writerExchangeEntity;

    @OneToMany(mappedBy = "client", fetch = LAZY, cascade = CascadeType.ALL)
    private List<ExchangeFileEntity> files;

}
