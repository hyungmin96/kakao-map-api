package com.example.kakaomap.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "tbl_group_board")
public class GroupBoardEntity extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "board_id")
    private Long boardId;

    @Column(name = "group_id")
    private Long groupId;

    @Lob
    @Column(name = "board_content")
    private String content;

    @Column(name = "board_like")
    private int boardLike;

    @Column(name = "board_type")
    @Enumerated(EnumType.STRING)
    private BoardType type;

    @Column(name = "board_category")
    @Enumerated(EnumType.STRING)
    private BoardCategory BoardCategory;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "exchange_id")
    private WriterExchangeEntity writerExchangeEntity;

    @OneToMany(mappedBy = "groupBoard", fetch = LAZY, cascade = CascadeType.ALL)
    private List<GroupBoardFileEntity> files;

    public enum BoardType{
        general, notice, fix
    }

    public enum BoardCategory{
        article, exchange, complete
    }

}
