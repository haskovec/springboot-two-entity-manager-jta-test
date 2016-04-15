package com.haskovec.springbootdualemjta.domain.secondarydb;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by jhaskovec on 4/15/16.
 */
@Repository
public interface SecondaryRepository extends JpaRepository<Secondary, Integer> {
}
