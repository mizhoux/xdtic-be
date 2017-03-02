package wenjing.xdtic.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Date;

/**
 *
 * @author wenjing
 */
@JsonIgnoreProperties({"id", "userId", "content"})
public class Message {

    public static enum Type {
        POST, PASS, JOIN
    }

    private Integer id;

    private Integer proId;
    private Integer userId;
    private String content;
    private String type;
    private boolean read;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date date;

    // 兼容前端
    private Integer mid; // id
    private Integer uid; // userId
    private String massage; // content

    public static void syncDataForBack(Message message) {
        message.setId(message.getMid());
        message.setUserId(message.getUid());
        message.setContent(message.getMassage());
    }

    public static void syncDataForFront(Message message) {
        message.setMid(message.getId());
        message.setUid(message.getUserId());
        message.setMassage(message.getContent());
    }

    public static Message of(Project project, Message.Type type) {
        Message message = new Message();

        message.setUserId(project.getUserId());
        message.setProId(project.getId());
        message.setContent(getMessageContent(project.getName(), type));
        message.setType(type.name().toLowerCase());

        return message;
    }

    private static String getMessageContent(String proName, Message.Type type) {
        String content = "";
        switch (type) {
            case POST:
                content = "棒棒哒~ 成功发布项目【" + proName + "】，请等待审核";
                break;
            case PASS:
                content = "厉害了~ 项目【" + proName + "】通过了审核";
                break;
            case JOIN:
                content = "好开心~ 有用户报名了项目【" + proName + "】";
                break;
        }

        return content;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getMid() {
        return mid;
    }

    public void setMid(Integer mid) {
        this.mid = mid;
    }

    public String getMassage() {
        return massage;
    }

    public void setMassage(String massage) {
        this.massage = massage;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public Integer getProId() {
        return proId;
    }

    public void setProId(Integer proId) {
        this.proId = proId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
