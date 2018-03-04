package com.example.faiq.kuapp;

import java.util.List;

/**
 * Created by faiq on 05/03/2018.
 */

public class PostsModel {
    private String post;
    private List<String> tags;

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}
