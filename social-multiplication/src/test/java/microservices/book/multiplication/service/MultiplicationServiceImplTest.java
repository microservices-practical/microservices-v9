package microservices.book.multiplication.service;

import microservices.book.multiplication.domain.Multiplication;
import microservices.book.multiplication.domain.MultiplicationResultAttempt;
import microservices.book.multiplication.domain.User;
import microservices.book.multiplication.event.EventDispatcher;
import microservices.book.multiplication.event.MultiplicationSolvedEvent;
import microservices.book.multiplication.repository.MultiplicationResultAttemptRepository;
import microservices.book.multiplication.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.awt.*;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

public class MultiplicationServiceImplTest {

    private MultiplicationServiceImpl multiplicationServiceImpl;

    @Mock
    private RandomGeneratorService randomGeneratorService;

    @Mock
    private MultiplicationResultAttemptRepository attemptRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private EventDispatcher eventDispatcher;

    @Before
    public void setUp() {
        // With this call to initMocks we tell Mockito to process the annotations
        MockitoAnnotations.initMocks(this);
        multiplicationServiceImpl = new MultiplicationServiceImpl(randomGeneratorService, attemptRepository,
                userRepository, eventDispatcher);
    }

    @Test
    public void createRandomMultiplicationTest() {
        // given (our mocked Random Generator service will return first 50, then 30)
        given(randomGeneratorService.generateRandomFactor()).willReturn(50, 30);

        // when
        Multiplication multiplication = multiplicationServiceImpl.createRandomMultiplication();

        // assert
        assertThat(multiplication.getFactorA()).isEqualTo(50);
        assertThat(multiplication.getFactorB()).isEqualTo(30);
    }

    @Test
    public void checkCorrectAttemptTest() {
        // given
        Multiplication multiplication = new Multiplication(50, 60);
        User user = new User("john_doe");
        MultiplicationResultAttempt attempt = new MultiplicationResultAttempt(
                user, multiplication, 3000, false);
        MultiplicationResultAttempt verifiedAttempt = new MultiplicationResultAttempt(
                user, multiplication, 3000, true);
        MultiplicationSolvedEvent event = new MultiplicationSolvedEvent(attempt.getId(),
                attempt.getUser().getId(), true);
        given(userRepository.findByAlias("john_doe")).willReturn(Optional.empty());
        // Note: the service will set correct to true
        given(attemptRepository.save(verifiedAttempt)).willReturn(verifiedAttempt);

        // when
        MultiplicationResultAttempt resultAttempt = multiplicationServiceImpl.checkAttempt(attempt);

        // assert
        assertThat(resultAttempt.isCorrect()).isTrue();
        verify(attemptRepository).save(verifiedAttempt);
        verify(eventDispatcher).send(eq(event));
    }

    @Test
    public void checkWrongAttemptTest() {
        // given
        Multiplication multiplication = new Multiplication(50, 60);
        User user = new User("john_doe");
        MultiplicationResultAttempt attempt = new MultiplicationResultAttempt(
                user, multiplication, 3010, false);
        MultiplicationResultAttempt storedAttempt = new MultiplicationResultAttempt(
                user, multiplication, 3010, false);
        MultiplicationSolvedEvent event = new MultiplicationSolvedEvent(attempt.getId(),
                attempt.getUser().getId(), false);
        given(userRepository.findByAlias("john_doe")).willReturn(Optional.empty());
        given(attemptRepository.save(attempt)).willReturn(storedAttempt);

        // when
        MultiplicationResultAttempt resultAttempt = multiplicationServiceImpl.checkAttempt(attempt);

        // assert
        assertThat(resultAttempt.isCorrect()).isFalse();
        verify(attemptRepository).save(attempt);
        verify(eventDispatcher).send(eq(event));
    }
}