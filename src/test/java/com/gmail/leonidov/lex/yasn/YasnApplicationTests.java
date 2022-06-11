package com.gmail.leonidov.lex.yasn;

import com.gmail.leonidov.lex.yasn.beans.YTopHitsSpotifyBean;
import com.gmail.leonidov.lex.yasn.repositories.YTopHitsSpotifyRepository;
import com.gmail.leonidov.lex.yasn.services.YSpotifyStatisticService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = {YasnApplicationTests.Initializer.class})
@Testcontainers
class YasnApplicationTests {

	@Autowired
	private YSpotifyStatisticService service;

	@Autowired
	private YTopHitsSpotifyRepository repository;

	private static YTopHitsSpotifyBean testBean;

	@Container
	private static final PostgreSQLContainer<?> container =
			new PostgreSQLContainer<>("postgres:14")
					.withInitScript("script.sql")
					.withDatabaseName("yasn_test_db")
					.withUsername("postgres")
					.withPassword("postgres");

	static class Initializer
			implements ApplicationContextInitializer<ConfigurableApplicationContext> {
		public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
			TestPropertyValues.of(
					"spring.datasource.url=" + container.getJdbcUrl(),
					"spring.datasource.username=" + container.getUsername(),
					"spring.datasource.password=" + container.getPassword()
			).applyTo(configurableApplicationContext.getEnvironment());
		}
	}

	@BeforeAll
	static void createTestedYTopHitsSpotifyBean() {
		testBean = new YTopHitsSpotifyBean(1L, 1L, 2000, (short) 1, 1F, 1F, (short) 1);
	}

	@Test
	void testCreateBean() {
		YTopHitsSpotifyBean bean = new YTopHitsSpotifyBean(
				2L, 1L, 2000, (short) 1, 1F, 1F, (short) 1
		);
		assertEquals(bean, testBean);
	}

	@Test
	void testBeanGetDuration(){
		assertEquals(1L, testBean.getDuration());
	}

	@Test
	void testBeanGetYear(){
		assertEquals(2000, testBean.getYear());
	}

	@Test
	void testBeanGetPopularity(){
		assertEquals((short) 1, testBean.getPopularity());
	}

	@Test
	void testBeanGetDanceability(){
		assertEquals(1F, testBean.getDanceability());
	}

	@Test
	void testBeanGetEnergy(){
		assertEquals(1F, testBean.getEnergy());
	}

	@Test
	void testBeanGetKey(){
		assertEquals((short) 1, testBean.getKey());
	}

	@Test
	void testFillTestDB() {
		assertEquals(10, fillTestDB().size());
	}

	@Test
	void testRepositoryGetAllDuration() {
		fillTestDB();
		String columnName = "duration_ms";
		List<Long> result = List.of(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L);
		assertEquals(result, repository.getAllDuration(columnName));
	}

	@Test
	void testRepositoryGetAllDurationFromYear() {
		fillTestDB();
		String columnName = "duration_ms";
		List<Long> result = List.of(5L);
		assertEquals(result, repository.getAllDurationFromYear(columnName, 5));
	}

	@Test
	void testRepositoryGetAllYear() {
		fillTestDB();
		String columnName = "year";
		List<Integer> result = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
		assertEquals(result, repository.getAllYear(columnName));
	}

	@Test
	void testRepositoryGetAllYearFromYear() {
		fillTestDB();
		String columnName = "year";
		List<Integer> result = List.of(9);
		assertNotEquals(result, repository.getAllYearFromYear(columnName, 8));
	}

	@Test
	void testRepositoryGetAllPopularity() {
		fillTestDB();
		String columnName = "popularity";
		List<Short> result = List.of(
				(short) 1, (short) 2, (short) 3, (short) 4, (short) 5,
				(short) 6, (short) 7, (short) 8, (short) 9, (short) 10
		);
		assertEquals(result, repository.getAllPopularity(columnName));
	}

	@Test
	void testRepositoryGetAllPopularityFromYear() {
		fillTestDB();
		String columnName = "popularity";
		List<Short> result = List.of((short) 3);
		assertNotEquals(result, repository.getAllPopularityFromYear(columnName, 4));
	}

	@Test
	void testRepositoryGetAllDanceability() {
		fillTestDB();
		String columnName = "danceability";
		List<Float> result = List.of(1F, 2F, 3F, 4F, 5F, 6F, 7F, 8F, 9F, 10F);
		assertEquals(result, repository.getAllDanceability(columnName));
	}

	@Test
	void testRepositoryGetAllDanceabilityFromYear() {
		fillTestDB();
		String columnName = "danceability";
		List<Float> result = List.of(9F);
		assertNotEquals(result, repository.getAllDanceabilityFromYear(columnName, 8));
	}

	@Test
	void testRepositoryGetAllEnergy() {
		fillTestDB();
		String columnName = "energy";
		List<Float> result = List.of(1F, 2F, 3F, 4F, 5F, 6F, 7F, 8F, 9F, 10F);
		assertEquals(result, repository.getAllDanceability(columnName));
	}

	@Test
	void testRepositoryGetAllEnergyFromYear() {
		fillTestDB();
		String columnName = "energy";
		List<Float> result = List.of(9F);
		assertEquals(result, repository.getAllDanceabilityFromYear(columnName, 9));
	}

	@Test
	void testRepositoryGetAllKey() {
		fillTestDB();
		String columnName = "key";
		List<Short> result = List.of(
				(short) 1, (short) 2, (short) 3, (short) 4, (short) 5,
				(short) 6, (short) 7, (short) 8, (short) 9, (short) 10
		);
		assertEquals(result, repository.getAllKey(columnName));
	}

	@Test
	void testRepositoryGetAllKeyFromYear() {
		fillTestDB();
		String columnName = "key";
		List<Short> result = List.of((short) 7);
		assertEquals(result, repository.getAllKeyFromYear(columnName, 7));
	}

	private List<YTopHitsSpotifyBean> fillTestDB() {
		List<YTopHitsSpotifyBean> testData = new ArrayList<>();
		for (int i = 1; i < 11; i++) {
			YTopHitsSpotifyBean bean =
					new YTopHitsSpotifyBean((long) i, (long)i, i, (short) i, (float) i, (float) i, (short) i);
			testData.add(bean);
		}
		return repository.saveAll(testData);
	}

}
