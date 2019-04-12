package com.guitar.db;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.guitar.db.model.Location;
import com.guitar.db.repository.LocationJpaRepository;

@ContextConfiguration(locations={"classpath:com/guitar/db/applicationTests-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class LocationPersistenceTests {

	@Autowired
	private LocationJpaRepository locationJpaRepository;
	
	@PersistenceContext
	private EntityManager entityManager;

	@Test
	public void testJpaFind(){
		List<Location> locations = locationJpaRepository.findAll();
		assertNotNull(locations);
	}
	
	@Test
	@Transactional
	public void testSaveAndGetAndDelete() throws Exception {
		Location location = new Location();
		location.setCountry("Canada");
		location.setState("British Columbia");
		location = locationJpaRepository.saveAndFlush(location);
		
		// clear the persistence context so we don't return the previously cached location object
		// this is a test only thing and normally doesn't need to be done in prod code
		entityManager.clear();

		Location otherLocation = locationJpaRepository.findOne(location.getId());
		assertEquals("Canada", otherLocation.getCountry());
		assertEquals("British Columbia", otherLocation.getState());
		
		//delete BC location now
		locationJpaRepository.delete(otherLocation);
	}

	@Test
	public void TestfindByStateIgnoreCaseLike() throws Exception {
		List<Location> locs = locationJpaRepository.findByStateIgnoreCaseLike("new%");
		assertEquals(4, locs.size());
	}
	
	@Test
	public void testFindByStateContaining() throws Exception {
		List<Location> locs = locationJpaRepository.findByStateContaining("New");
		assertEquals(4, locs.size());
	}
	
	@Test
	public void testFindDistinctCountryByStateContaining() throws Exception {
		List<Location> locs = locationJpaRepository.findDistinctCountryByStateContaining("New");
		for (Location loc : locs) {
			System.out.println(loc.getCountry());
		}
	}
	
	/**
	 * @throws Exception
	 */
	@Test
	public void testFindByStateIgnoreCaseStartingWithOrderByIdDesc() throws Exception {
		List<Location> locs = locationJpaRepository.findByStateIgnoreCaseStartingWithOrderByIdDesc("new");
		
		for (Location location : locs) {
			System.out.println(location.getId() + "  " + location.getState());
		}
		
		assertEquals(4, locs.size());
	}
	
	
	@Test
	public void testFindByStateLikeOrId() throws Exception {
		List<Location> locs = locationJpaRepository.findByStateLikeOrId("New%", 25L);
		assertEquals(5, locs.size());
	}
	
	@Test
	public void testFindByStateNotAndCountryEquals() throws Exception {
		List<Location> locs = locationJpaRepository.findByStateNotAndCountryEquals("Utah", "United States");
		System.out.println("size : " + locs.size());
		assertEquals(49, locs.size());
	}	

	@Test
	@Transactional  //note this is needed because we will get a lazy load exception unless we are in a tx
	public void testFindWithChildren() throws Exception {
		Location arizona = locationJpaRepository.findOne(3L);
		assertEquals("United States", arizona.getCountry());
		assertEquals("Arizona", arizona.getState());
		
		assertEquals(1, arizona.getManufacturers().size());
		
		assertEquals("Fender Musical Instruments Corporation", arizona.getManufacturers().get(0).getName());
	}
}
