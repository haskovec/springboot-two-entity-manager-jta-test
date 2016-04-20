package com.haskovec.springbootdualemjta;

import com.haskovec.springbootdualemjta.domain.primarydb.Example;
import com.haskovec.springbootdualemjta.domain.primarydb.ExampleRepository;
import com.haskovec.springbootdualemjta.domain.secondarydb.Secondary;
import com.haskovec.springbootdualemjta.domain.secondarydb.SecondaryRepository;
import com.haskovec.springbootdualemjta.service.ExampleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SpringbootDualEmJtaApplication.class)
public class SpringbootDualEmJtaApplicationTests {
    private static final Logger log = LoggerFactory.getLogger(SpringbootDualEmJtaApplicationTests.class);

	@Autowired
	private ExampleService exampleService;

	@Autowired
	private ExampleRepository exampleRepository;

	@Autowired
	private SecondaryRepository secondaryRepository;

    @Autowired
    @Qualifier("primaryJdbcTemplate")
    private JdbcTemplate primaryJdbcTemplate;

    @Autowired
    @Qualifier("secondaryJdbcTemplate")
    private JdbcTemplate secondaryJdbcTemplate;

	@Test
	public void contextLoads() {
	}

    @Test
    public void testCommit() throws Exception {
        exampleService.testCommit();

        final Integer count = primaryJdbcTemplate.queryForObject("select count(*) from example where id = 1", Integer.class);
        log.info("The count on the primary DB for example is: " + count);

        //assertNotNull(count);
        //assertEquals(1, count.intValue());

        final Example example = exampleRepository.findOne(1);
        //assertNotNull(example);


        final Integer countSecondary = secondaryJdbcTemplate.queryForObject("select count(*) from secondary where id = 1", Integer.class);
        log.info("The count on the secondary DB for secondary is: " + countSecondary);

        final Secondary secondary = secondaryRepository.findOne(1);
        assertNotNull(secondary);
    }

    @Test
    public void testRollback() {
        try {
            exampleService.testRollback();
        } catch (RuntimeException e) {
            //This is expected
        }

        final Example example = exampleRepository.findOne(2);
        assertNull(example);
    }


    @Test
    public void testRollbackSecondary() {
        try {
            exampleService.testRollbackSecondary();
        } catch (RuntimeException e) {
            //This is expected
        }

        final Secondary secondary = secondaryRepository.findOne(2);
        assertNull(secondary);
    }

}
