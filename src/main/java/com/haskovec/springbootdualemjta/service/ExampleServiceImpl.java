package com.haskovec.springbootdualemjta.service;

import com.haskovec.springbootdualemjta.domain.primarydb.Example;
import com.haskovec.springbootdualemjta.domain.primarydb.ExampleRepository;
import com.haskovec.springbootdualemjta.domain.secondarydb.Secondary;
import com.haskovec.springbootdualemjta.domain.secondarydb.SecondaryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by jhaskovec on 4/21/16.
 */
@Service
@Transactional(transactionManager = "transactionManager")
public class ExampleServiceImpl implements ExampleService {
	private static final Logger log = LoggerFactory.getLogger(ExampleService.class);

	private final ExampleRepository exampleRepository;

	private final SecondaryRepository secondaryRepository;

	@Autowired
	public ExampleServiceImpl(final ExampleRepository exampleRepository, final SecondaryRepository secondaryRepository) {
		this.exampleRepository = exampleRepository;
		this.secondaryRepository = secondaryRepository;
	}

	public void testCommit() throws Exception {
		try {
			log.info("testCommit");

			final Example example = new Example();
			example.setId(1);
			example.setName("Test 1");
			exampleRepository.save(example);

			log.info("testCommit OK");
		} catch (Exception e) {
			log.error("testCommit FAIL with " + e);
			throw e;
		}

		try {
			log.info("testSecondaryCommit");

			final Secondary secondary = new Secondary();
			secondary.setId(1);
			secondary.setName("secondary 1");
			secondaryRepository.save(secondary);

			log.info("testSecondaryCommit OK");
		} catch (Exception e) {
			log.error("testSecondaryCommit FAIL with " + e);
			throw e;
		}
	}


	public void testRollback() {
		log.info("test rollback");

		final Example example2 = new Example();
		example2.setId(2);
		example2.setName("Test 2");
		exampleRepository.save(example2);

		throw new RuntimeException("Throw exception in method to see if we rolled back the insert");
	}


	public void testRollbackSecondary() {
		log.info("test rollback secondary");

		final Secondary secondary = new Secondary();
		secondary.setId(2);
		secondary.setName("Secondary 2");
		secondaryRepository.save(secondary);

		throw new RuntimeException("Throw Exception in method to see if we rolled back the insert");
	}


	public void saveExampleEntity(final Example example) {
		exampleRepository.saveAndFlush(example);
	}

	public void saveSecondaryEntity(final Secondary secondary) {
		secondaryRepository.save(secondary);
	}
}
