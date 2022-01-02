package com.hamsterbusters.squeaker.follower;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FollowerRepository extends CrudRepository<Follower, FollowerCompositeKey> {
}
