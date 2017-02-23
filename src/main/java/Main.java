import java.io.*;
import java.util.Comparator;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Main
{
    public static void main(String[] args) throws IOException
    {
        final Scanner in = new Scanner(new BufferedInputStream(new FileInputStream("videos_worth_spreading.in")));
        int v = in.nextInt();
        int e = in.nextInt();
        int r = in.nextInt();
        int c = in.nextInt();
        int x = in.nextInt();

        int[] videos = new int[v];
        for (int i = 0; i < v; i++) {
            videos[i] = in.nextInt();
        }

        Endpoint[] endpoints = new Endpoint[e];
        for (int i = 0; i < e; i++) {
            Endpoint endpoint = new Endpoint();
            endpoint.dataCenterLatency = in.nextInt();
            int k = in.nextInt();
            endpoint.caches = new int[k];
            endpoint.scores = new int[k];
            TreeMap<Integer, Integer> caches = new TreeMap<>(Comparator.<Integer>reverseOrder());
            for (int j = 0; j < k; j++) {
                int cacheId = in.nextInt();
                int cacheLatency = endpoint.dataCenterLatency - in.nextInt();
                caches.put(cacheLatency, cacheId);
            }
            int j = 0;
            for (Map.Entry<Integer, Integer> entry : caches.entrySet()) {
                endpoint.scores[j] = entry.getKey();
                endpoint.caches[j] = entry.getValue();
                j++;
            }
            endpoints[i] = endpoint;
        }

        Request[] requests = new Request[r];
        for (int i = 0; i < r; i++) {
            Request request = new Request();
            request.videoId = in.nextInt();
            request.endpointId = in.nextInt();
            request.n = in.nextInt();
            requests[i] = request;
        }


        final RandomSolutionGenerator generator = new RandomSolutionGenerator();


        final int requestCount = Stream.of(requests).mapToInt(Request::getN).sum();

        Solution solution = null;


        long maxScore = 0;
        for (int i = 0; i < 10; i++)
        {
            solution = generator.generate(v, videos, c, x);
             long score = solution.score(endpoints, requests);

            final HillClimb hillClimb = new HillClimb();

            solution = hillClimb.optimize(solution, 2000, videos, x, endpoints, requests);
            score = solution.score(endpoints, requests);

            if (score > maxScore)
            {
                System.out.println("Avg Score = " + (score * 1000 / requestCount));

                writeToFile(solution);

                maxScore = score;
            }
        }

        final long score = solution.score(endpoints, requests);
        System.out.println("Score = " + score);

        System.out.println("Avg Score = " + (score * 1000 / requestCount));

    }

    private static void writeToFile(Solution solution) throws IOException
    {
        final FileWriter out = new FileWriter(new File("videos_worth_spreading.out"));
        solution.print(out);
        out.close();
    }
}
