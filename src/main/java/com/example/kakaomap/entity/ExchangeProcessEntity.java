package com.example.kakaomap.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "tbl_group_exchange_process")
public class ExchangeProcessEntity {

    @Column(name = "exchange_id")
    @Id
    @GeneratedValue
    private Long id;

    @OneToOne(mappedBy = "exchangeProcessEntity", fetch = FetchType.LAZY)
    private WriterExchangeEntity writerExchangeEntity;

    @OneToOne(mappedBy = "exchangeProcessEntity", fetch = FetchType.LAZY)
    private ClientExchangeEntity clientExchangeEntity;

    @Column(name = "process_status")
    @Enumerated(EnumType.STRING)
    private Status status;

    public enum Status{
        wait, process, complete
    }

}
