package com.haskovec.springbootdualemjta.domain.primarydb;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by jhaskovec on 4/13/16.
 */
@Repository
public interface ExampleRepository extends JpaRepository<Example, Integer> {

}
