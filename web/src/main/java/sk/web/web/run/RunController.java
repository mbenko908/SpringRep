package sk.web.web.run;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.RestController; // Correct import statement for all annotations

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@RestController
@RequestMapping("/api/runs")
public class RunController {

	private final RunRepository runRepository;

	public RunController(RunRepository runRepository) {
		this.runRepository = runRepository;
	}

	@GetMapping("")
	List<Run> finalAll() {
		return runRepository.findAll();
	}
	
	@GetMapping("/{id}")
	Run findById(@PathVariable Integer id) {
		Optional<Run> run = runRepository.findById(id);
		if (run.isEmpty()) {
			throw new RunNotFoundException();
		}
		return run.get();
	}

	// POST
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("")
	void create(@RequestBody Run run) {
		runRepository.create(run);
	}

	// PUT
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PutMapping("/{id}")
	void update(@RequestBody Run run, @PathVariable Integer id) {
		runRepository.update(run, id);
	}

	// DELETE
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	void delete(@PathVariable Integer id) {
		runRepository.delete(id);
	}
	
}
