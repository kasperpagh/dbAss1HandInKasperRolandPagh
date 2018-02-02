package pagh.cphbusiness;

import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;

import java.io.*;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Database
{

    public void dbWriter(DataModel data)
    {
        File file;
        file = new File("data");
        if (!file.exists())
        {
            try
            {
                file.createNewFile();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        try (FileOutputStream fop = new FileOutputStream(file, true))
        {
            String sData = data.toString();
            byte[] bytes = sData.getBytes("US-ASCII");
            StringBuilder binary = new StringBuilder();
            for (byte b : bytes)
            {
                int val = b;
                for (int i = 0; i < 8; i++)
                {
                    binary.append((val & 128) == 0 ? 0 : 1);
                    val <<= 1;
                }
//                binary.append(' ');
            }
            fop.write(binary.toString().getBytes("US-ASCII"));
//            fop.flush();
//            fop.write(System.lineSeparator().getBytes());
            fop.flush();
            fop.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }


    public String dbReader() throws UnsupportedEncodingException
    {
        File file;
        file = new File("data");

        System.out.println("her er len: " + file.length());
        byte[] result = new byte[(int) file.length()];
        try
        {
            InputStream input = null;
            try
            {
                int totalBytesRead = 0;
                input = new BufferedInputStream(new FileInputStream(file));
                while (totalBytesRead < result.length)
                {
                    int bytesRemaining = result.length - totalBytesRead;
                    int bytesRead = input.read(result, totalBytesRead, bytesRemaining);
                    if (bytesRead > 0)
                    {
                        totalBytesRead = totalBytesRead + bytesRead;
                    }
                }
            }
            finally
            {
                input.close();
            }
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }

        String res = new String(result, "US-ASCII");
        int splitSize = 8;

        if (res.length() % splitSize == 0) //tjekker om antallet af bits, går op med 8 (altså bytes)
        {
            int index = 0;
            int position = 0;

            byte[] resultByteArray = new byte[res.length() / splitSize];
            StringBuilder text = new StringBuilder(res);

            while (index < text.length())
            {
                String binaryStringChunk = text.substring(index, Math.min(index + splitSize, text.length()));
                Integer byteAsInt = Integer.parseInt(binaryStringChunk, 2);
                resultByteArray[position] = byteAsInt.byteValue();
                index += splitSize;
                position++;
            }

            return new String(resultByteArray, "US-ASCII");
        }
        else
        {
            return "not mod 8";
        }

    }
}
