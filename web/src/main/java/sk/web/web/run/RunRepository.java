package sk.web.web.run;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;

@Repository
public class RunRepository {

	private static final Logger log = LoggerFactory.getLogger(RunRepository.class);
	private final JdbcClient jdbcClient;
	
	public RunRepository(JdbcClient jdbcClient) {
		this.jdbcClient = jdbcClient;
	}
	
	
	//private List<Run> runs = new ArrayList<>();

	public List<Run> findAll() {
		return jdbcClient.sql("select * from run")
				.query(Run.class)
				.list();
	}
	
	Optional<Run> findById(Integer id) {
		return jdbcClient.sql("SELECT id, title, started_on, completed_on, miles, location FROM Run WHERE id = :id")
				.param("id", id)
				.query(Run.class)
				.optional();
	}
	

	public void create(Run run) {
		var updated = jdbcClient.sql("INSERT INTO Run(id, title, started_on, completed_on, miles, location) VALUES (?,?,?,?,?,?)")
				.params(List.of(run.id(),run.title(), run.startedOn(), run.completedOn(), run.miles(), run.location().toString()))
				.update();
		
		Assert.state(updated == 1,  "Failed to create run" + run.title());
	}

	public void update(Run run, Integer id) {
		var updated = jdbcClient.sql("update run set title = ?, started_on = ?, completed_on = ?, miles = ?, location = ? where id = ?")
				.params(List.of(run.title(), run.startedOn(), run.completedOn(), run.miles(), run.location().toString(), id))
				.update();
		
		Assert.state(updated == 1,  "Failed to update run" + run.title());
	}

	public void delete(Integer id) {
	var updated = jdbcClient.sql("delete from run where id = :id")
			.param("id", id)
			.update();

	Assert.state(updated == 1,  "Failed to delete run" + id);
	}

	public int count() {return jdbcClient.sql("select * from run").query().listOfRows().size();}
	
	public void saveAll(List<Run> runs) {runs.stream().forEach(this::create);}

	public List<Run> findByLocation(String location) {
		return jdbcClient.sql("select * from run where location = :location")
				.param("location", location)
				.query(Run.class)
				.list();
	}
	
}
