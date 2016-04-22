package com.haskovec.springbootdualemjta.service;

import com.haskovec.springbootdualemjta.domain.primarydb.Example;
import com.haskovec.springbootdualemjta.domain.secondarydb.Secondary;
import org.springframework.stereotype.Service;


/**
 * Created by jhaskovec on 4/13/16.
 */
@Service
public interface ExampleService {

	void testCommit() throws Exception;

	void testRollback();

	void testRollbackSecondary();

	void saveExampleEntity(final Example example);

	void saveSecondaryEntity(final Secondary secondary);
}
