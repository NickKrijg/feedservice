package com.kwetter.feed.models;

import java.io.Serializable;
import java.util.Date;

public class Post implements Serializable {

    private Long id;
    private String username;
    private String content;
    private Date createdAt;
    private Boolean isChildFriendly;

    public Post(String username, String content) {
        this.username = username;
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Boolean getChildFriendly() {
        return isChildFriendly;
    }

    public void setChildFriendly(Boolean childFriendly) {
        isChildFriendly = childFriendly;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", content='" + content + '\'' +
                ", createdAt=" + createdAt +
                ", isChildFriendly=" + isChildFriendly +
                '}';
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
