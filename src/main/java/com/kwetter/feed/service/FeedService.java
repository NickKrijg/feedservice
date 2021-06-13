package com.kwetter.feed.service;

import com.google.gson.Gson;
import com.kwetter.feed.models.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Service
@EnableCaching
public class FeedService {

    @Autowired
    RedisTemplate<String, String> redisTemplate;

    @Cacheable("feedCache")
    public Iterable<Post> getFeed() {
        System.out.println("Not used the cache");

        String feedString = redisTemplate.opsForValue().get("feedCache");
        var gson = new Gson();

        return Arrays.asList(gson.fromJson(feedString, Post[].class));
    }

    public boolean setFeed() {
        List<Post> posts = new ArrayList<>();
        posts.add(new Post("user1", "content1"));
        posts.add(new Post("user2", "content2"));
        posts.add(new Post("user3", "content3"));
        posts.add(new Post("user4", "content4"));
        posts.add(new Post("user5", "content5"));
        posts.add(new Post("user6", "content6"));

        var gson = new Gson();
        try{
            redisTemplate.opsForValue().set("feedCache", gson.toJson(posts));
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    @CacheEvict("feedCache")
    public void deleteAllByUsername(String username) {
        String feedString = redisTemplate.opsForValue().get("feedCache");
        var gson = new Gson();

        var postList = new LinkedList<>(Arrays.asList(gson.fromJson(feedString, Post[].class)));

        postList.removeIf(post -> post.getUsername().equals(username));

        redisTemplate.opsForValue().set("feedCache", gson.toJson(postList));
    }
}
