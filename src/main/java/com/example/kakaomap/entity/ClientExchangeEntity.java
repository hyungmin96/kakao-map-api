package com.example.kakaomap.entity;

import lombok.*;
import org.hibernate.annotations.BatchSize;

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

    @Column(name = "board_Id")
    private Long boardId;

    @OneToMany(mappedBy = "client", fetch = LAZY, cascade = CascadeType.ALL)
    private List<ExchangeFileEntity> files;

    @OneToMany(mappedBy = "clientExchangeEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<WriterClientJoinEntity> writerClientJoinEntity;

}
