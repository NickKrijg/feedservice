package com.kwetter.feed.controllers;

import com.kwetter.feed.models.Post;
import com.kwetter.feed.service.FeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/feed")
@CrossOrigin(origins = {"http://localhost:8080/", "*"})
public class FeedController {

    @Autowired
    private FeedService feedService;

    @GetMapping("/feed")
    public Iterable<Post> getFeed() throws Exception {
        try{
            return feedService.getFeed();
        } catch (Exception ex) {
            throw new Exception(ex);
        }
    }

    @GetMapping("/setfeed")
    public boolean setFeed() {
        return feedService.setFeed();
    }

    @GetMapping("/test")
    public String test() {
        return "Hello world";
    }
}
