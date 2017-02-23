import java.io.IOException;
import java.io.Writer;
import java.util.BitSet;
import java.util.Set;

public class Solution
{
    private int videoCount;
    private BitSet[] videos;

    public Solution(int videoCount, int[][] caches)
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
}
