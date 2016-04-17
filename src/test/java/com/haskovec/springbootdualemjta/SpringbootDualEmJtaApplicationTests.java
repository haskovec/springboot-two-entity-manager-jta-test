package com.haskovec.springbootdualemjta;

import com.haskovec.springbootdualemjta.domain.primarydb.Example;
import com.haskovec.springbootdualemjta.domain.primarydb.ExampleRepository;
import com.haskovec.springbootdualemjta.domain.secondarydb.Secondary;
import com.haskovec.springbootdualemjta.domain.secondarydb.SecondaryRepository;
import com.haskovec.springbootdualemjta.service.ExampleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SpringbootDualEmJtaApplication.class)
public class SpringbootDualEmJtaApplicationTests {
	@Autowired
	private ExampleService exampleService;

	@Autowired
	private ExampleRepository exampleRepository;

	@Autowired
	private SecondaryRepository secondaryRepository;

	@Test
	public void contextLoads() {
	}

    @Test
    public void testCommit() throws Exception {
        exampleService.testCommit();

        final Example example = exampleRepository.findOne(1);
        assertNotNull(example);

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
