package shop.mtcoding.final5th.domain.joined_chat;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Table(name = "joined_chat")
@Entity
public class JoinedChat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long joinedChatRoomId;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long chatRoomId2;

    @Column(nullable = false)
    private Timestamp joinedChatCreatedAt;
}
