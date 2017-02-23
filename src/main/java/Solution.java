import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Random;
import java.util.Set;

public class Solution {
    private int videoCount;
    private BitSet[] videos;

    private Solution() {
    }

    public Solution(int videoCount, int[][] caches) {
        this.videoCount = videoCount;

        videos = new BitSet[caches.length];
        for (int i = 0; i < caches.length; i++)
        {
            videos[i] = new BitSet(videoCount);
            for (int video : caches[i])
            {
                videos[i].set(video);
            }
        }
    }

    public Solution(int videoCount, Set<Integer>[] caches)
    {
        this.videoCount = videoCount;

        videos = new BitSet[caches.length];
        for (int i = 0; i < caches.length; i++)
        {
            videos[i] = new BitSet(videoCount);
            for (int video : caches[i])
            {
                videos[i].set(video);
            }
        }
    }

    public long score(Endpoint[] endpoints, Request[] requests)
    {
        long score = 0;
        for (Request request : requests)
        {
            final Endpoint endpoint = endpoints[request.endpointId];

            score += request.n * (videoScore(endpoint, request.videoId) );
        }

        return score;
    }

    private long videoScore(Endpoint endpoint, int videoId)
    {
        for (int i = 0; i < endpoint.caches.length; i++)
        {
            if (videos[endpoint.caches[i]].get(videoId))
                return endpoint.scores[i];
        }

        return 0;
    }

    public void print(Writer writer)
    {
        try
        {
            writer.write(String.valueOf(videos.length) + "\n");
            for (int i = 0; i < videos.length; i++)
            {
                writer.write(String.valueOf(i) + " ");

                for (int j = 0; j < videos[i].size(); j++)
                {
                    if (videos[i].get(j))
                    {
                        writer.write(String.valueOf(j) + " ");
                    }
                }
                writer.write("\n");
            }
            writer.flush();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected Solution clone() {
        Solution solution = new Solution();
        solution.videoCount = this.videoCount;
        solution.videos = new BitSet[this.videos.length];
        for (int i = 0; i < videos.length; i++) {
            solution.videos[i] = (BitSet) videos[i].clone();
        }
        return solution;
    }

    public Solution mutate(int[] videoSizes, int x, int factor) {
        Solution newSolution = this.clone();
        Random random = new Random();
        int[] capacity = new int[videos.length];
        Arrays.fill(capacity, x);
        for (int i = 0; i < videos.length; i++) {
            BitSet videoSet = videos[i];
            for (int j = 0; j < videoSet.length(); j++) {
                capacity[i] -= videoSet.get(j) ? videoSizes[j] : 0;
            }
        }
        for (int i = 0; i < videos.length; i++) {
            BitSet bitSet = videos[i];
            for (int j = 0; j < factor; j++) {
                int index = bitSet.nextSetBit(random.nextInt(bitSet.length()));
                if (index != -1) {
                    bitSet.set(index, false);
                    capacity[i] += videoSizes[index];
                }
            }

            for (int j = 0; j < factor * 2; j++) {
                int index = bitSet.nextClearBit(random.nextInt(bitSet.length()));
                if (index != -1 && (capacity[i] - videoSizes[index]) >= 0) {
                    bitSet.set(index, true);
                    capacity[i] -= videoSizes[index];
                }
            }
        }
        return newSolution;
    }
}
