package microservices.book.multiplication.controller;

import microservices.book.multiplication.domain.MultiplicationResultAttempt;
import microservices.book.multiplication.service.MultiplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * This class provides a REST API to POST the attempts from users.
 */
@RestController
@RequestMapping("/results")
final class MultiplicationResultAttemptController {

    private final MultiplicationService multiplicationService;

    @Autowired
    MultiplicationResultAttemptController(final MultiplicationService multiplicationService) {
        this.multiplicationService = multiplicationService;
    }

    @PostMapping
    ResponseEntity<MultiplicationResultAttempt> postResult(@RequestBody MultiplicationResultAttempt multiplicationResultAttempt) {
        return ResponseEntity.ok(multiplicationService.checkAttempt(multiplicationResultAttempt));
    }

    @GetMapping
    ResponseEntity<List<MultiplicationResultAttempt>> getStatistics(@RequestParam("alias") String alias) {
        return ResponseEntity.ok(
                multiplicationService.getStatsForUser(alias)
        );
    }

}
