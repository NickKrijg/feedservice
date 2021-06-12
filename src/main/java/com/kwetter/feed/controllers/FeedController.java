package com.kwetter.feed.controllers;

import com.kwetter.feed.models.Post;
import com.kwetter.feed.service.FeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/feed")
public class FeedController {

    @Autowired
    private FeedService feedService;

    @GetMapping
    public Iterable<Post> getFeed() {
        return feedService.getFeed();
    }

    @GetMapping("/test")
    public String test() {
        return "Hello world";
    }
}
