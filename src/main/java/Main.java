import java.io.*;
import java.util.Scanner;

public class Main
{
    public static void main(String[] args) throws IOException
    {
        final Scanner in = new Scanner(new BufferedInputStream(new FileInputStream("test.in")));

        final FileWriter out = new FileWriter(new File("result.txt"));

        out.append(in.nextLine());
        out.close();
    }
}
