package wenjing.xdtic.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Date;

/**
 *
 * @author Michael Chow <mizhoux@gmail.com>
 */
@JsonIgnoreProperties({"id", "userId", "skill", "experience"})
public class SignInfo {

    private Integer id;

    private Integer userId;
    private Integer proId;

    private String apply;
    private String skill;
    private String experience;

    @JsonFormat(pattern = "yyyy.MM.dd HH:mm")
    private Date signTime;

    private String username;

    // 兼容前端
    private Integer sid; // id
    private Integer uid; // userId
    private String profile; // skill
    private String pexperice; // experience
    private String date; // signTime yyyy.MM.dd
    private String time; // signTime HH:mm

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public Date getSignTime() {
        return signTime;
    }

    public void setSignTime(Date signTime) {
        this.signTime = signTime;
    }

    public Integer getSid() {
        return sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getProId() {
        return proId;
    }

    public void setProId(Integer proId) {
        this.proId = proId;
    }

    public String getApply() {
        return apply;
    }

    public void setApply(String apply) {
        this.apply = apply;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getPexperice() {
        return pexperice;
    }

    public void setPexperice(String pexperice) {
        this.pexperice = pexperice;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
