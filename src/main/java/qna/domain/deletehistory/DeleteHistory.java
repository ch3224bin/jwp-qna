package qna.domain.deletehistory;

import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import qna.domain.user.User;

@Entity
public class DeleteHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private ContentType contentType;

    @Embedded
    private ContentId contentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "deleted_by_id",
        foreignKey = @ForeignKey(name = "fk_delete_history_to_user"))
    private User deletedBy;

    private LocalDateTime createDate = LocalDateTime.now();

    public static DeleteHistory ofQuestion(Long contentId, User deletedBy, LocalDateTime createDate){
        return new DeleteHistory(ContentType.QUESTION, contentId, deletedBy, createDate);
    }

    public static DeleteHistory ofAnswer(ContentId contentId, User deletedBy, LocalDateTime createDate){
        return new DeleteHistory(ContentType.ANSWER, contentId, deletedBy, createDate);
    }

    public static DeleteHistory ofAnswer(Long contentId, User deletedBy, LocalDateTime createDate){
        return new DeleteHistory(ContentType.ANSWER, contentId, deletedBy, createDate);
    }

    private DeleteHistory(ContentType contentType, ContentId contentId, User deletedBy, LocalDateTime createDate) {
        this.contentType = contentType;
        this.contentId = contentId;
        this.deletedBy = deletedBy;
        this.createDate = createDate;
    }

    private DeleteHistory(ContentType contentType, Long contentId, User deletedBy, LocalDateTime createDate) {
        this(contentType, new ContentId(contentId), deletedBy, createDate);
    }
    // for jpa
    protected DeleteHistory() { }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeleteHistory that = (DeleteHistory) o;
        return Objects.equals(id, that.id) &&
                contentType == that.contentType &&
                Objects.equals(contentId, that.contentId) &&
                Objects.equals(deletedBy, that.deletedBy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, contentType, contentId, deletedBy);
    }

}