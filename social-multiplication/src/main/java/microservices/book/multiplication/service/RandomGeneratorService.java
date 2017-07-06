package microservices.book.multiplication.service;

public interface RandomGeneratorService {

    /**
     * @return a random number that will be used as a factor
     *         of a mid-complexity operation
     */
    int generateRandomFactor();

}
