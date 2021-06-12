package com.kwetter.feed.service;

import com.kwetter.feed.models.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@EnableCaching
public class FeedService {

    @Autowired
    RedisTemplate<String, List<Post>> redisTemplate;

    @Cacheable("feedCache")
    public Iterable<Post> getFeed() {
        System.out.println("Not used the cache");
        List<Post> posts = new ArrayList<>();
        posts.add(new Post("user1", "content1"));
        posts.add(new Post("user2", "content2"));
        posts.add(new Post("user3", "content3"));
        posts.add(new Post("user4", "content4"));
        posts.add(new Post("user5", "content5"));
        posts.add(new Post("user6", "content6"));
        return posts;
    }

    public void deleteAllByUsername(String username) {
        ListOperations<String, List<Post>> opsList = redisTemplate.opsForList();

//        var postList = opsList.leftPop("feedCache");

        var postListList = opsList.range("feedCache", 0, -1);
        var postList = postListList.get(0);

        if (postList != null) {
            postList.removeIf(post -> post.getUsername().equals(username));
        }
    }
}
