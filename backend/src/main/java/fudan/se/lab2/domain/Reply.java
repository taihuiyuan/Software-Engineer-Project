package fudan.se.lab2.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "reply")
@JsonIgnoreProperties({ "handler","hibernateLazyInitializer" })
public class Reply implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    /*@ManyToOne(targetEntity = Book.class,fetch= FetchType.LAZY)
    @JoinColumn(name = "cid", nullable = false, referencedColumnName = "id")
    private Comment comment;
    public Comment getComment() {
        return comment;
    }
    public void setComment(Comment comment) {
        this.comment = comment;
    }*/

    private int cid;//回复的评论

    private int rid;//回复的回复

    private String username;
    private String replyUsername;//被回复的人
    private String content;
    private String time;
    private int valid;

    public Reply(){}

    public Reply(String username, String content, int cid, String time, int valid){
        this.cid = cid;
        this.content = content;
        //this.replyUsername = replyUsername;
        this.username = username;
        //this.rid = rid;
        this.time = time;
        this.valid = valid;
    }


    public int getId() {
        return id;
    }

    public void setRid(int rid) {
        this.rid = rid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getReplyUsername() {
        return replyUsername;
    }

    public void setReplyUsername(String replyUsername) {
        this.replyUsername = replyUsername;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getCid() {
        return cid;
    }

    public int getRid() {
        return rid;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public int getValid() {
        return valid;
    }

    public void setValid(int valid) {
        this.valid = valid;
    }
}
