public class RNG {
    private double x = 9397;
    private final double a = RandomOddNumber();

    RNG() {
    }

    private double RandomOddNumber() {
        int randomNumber = (int) (Math.random() * 1000000);

        if (randomNumber % 2 == 0) {
            return randomNumber + 1;
        } else {
            return randomNumber;
        }
    }

    public double Generate() {
        double c = 67;
        long m = 4294967296L;
        x = ((a * x + c) % m);
        return x / m;
    }
}
