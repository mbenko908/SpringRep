package sk.web.web.run;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;

@Repository
public class RunRepository {

	private List<Run> runs = new ArrayList<>();

	List<Run> findAll() {
		return runs;
	}

	Optional<Run> findById(Integer id) {
		return runs.stream().filter(run -> run.id() == id).findFirst();
	}

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("")
	void create(@Valid @RequestBody Run run) {
		runs.add(run);
	}

	void update(Run run, Integer id) {
		Optional<Run> existingRun = findById(id);
		if (existingRun.isPresent()) {
			runs.set(runs.indexOf(existingRun.get()), run);
		}
	}

	void delete(Integer id) {
		runs.removeIf(run -> run.id().equals(id));
	}

	@PostConstruct
	private void init() {
		runs.add(new Run(1, "Monday Morning run", LocalDateTime.now(), LocalDateTime.now().plus(30, ChronoUnit.MINUTES),
				3, Location.INDOOR));
		runs.add(new Run(2, "Wednesday Evening Run", LocalDateTime.now(),
				LocalDateTime.now().plus(60, ChronoUnit.MINUTES), 6, Location.OUTDOOR));
	}

}
