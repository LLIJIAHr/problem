import java.util.Random;

public class HillClimb
{
    public Solution optimize(Solution solution, int N, int[] videoSizes, int cacheSize, Endpoint[] endpoints, Request[] requests)
    {
        final Random random = new Random();

        long bestScore = solution.score(endpoints, requests);
        Solution bestSolution = solution;
        for (int i = 0; i < N; i++)
        {
            final Solution mutate = solution.mutate(videoSizes, cacheSize, random.nextInt(16));
            final long score = mutate.score(endpoints, requests);

            if (score > bestScore)
            {
                bestScore = score;
                bestSolution = mutate;

                System.out.println("Found better solution. Score = " + score);
            }

        }
        return solution;
    }
}
