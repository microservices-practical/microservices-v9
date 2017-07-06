package microservices.book.gamification.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * @author moises.macero
 */
@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public final class LeaderBoardRow {

    private final Long userId;
    private final Long totalScore;

    // Empty constructor for JSON (de)serialization
    public LeaderBoardRow() {
        this(0L, 0L);
    }
}
