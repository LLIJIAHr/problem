import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.stream.Stream;

public class RandomSolutionGenerator
{
    public Solution generate(int videoCount, int[] videos, int cacheCount, int cacheSize) {
        final long seed = System.currentTimeMillis();

        System.out.println("Seed = " + seed);
        final Random random = new Random(seed);


        Set<Integer>[] solution = new Set[cacheCount];
        for (int i = 0; i < cacheCount; i++)
        {
            int size = 0;
            final Set<Integer> videoCache = new HashSet<>();

            while (size < cacheSize) {
                final int nextVideo = random.nextInt(videoCount);
                if (size + videos[nextVideo] < cacheSize)
                {
                    if (videoCache.add(nextVideo))
                    {
                        size += videos[nextVideo];
                    }
                }
                else
                {
                    break;
                }
            }

            solution[i] = videoCache;
        }
        return new Solution(videoCount, solution);
    }
}
