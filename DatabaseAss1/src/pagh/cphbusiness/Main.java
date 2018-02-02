package pagh.cphbusiness;

import java.io.*;

public class Main
{

    public static void main(String[] args)
    {
        Database db = new Database();

        try
        {
            db.dbWriter(new DataModel("1", "jeg hedder Kasper1"));
            db.dbWriter(new DataModel("2", "jeg hedder Kasper2"));
            db.dbWriter(new DataModel("3", "jeg hedder Kasper3"));
            db.dbWriter(new DataModel("4", "jeg hedder Kasper4"));
            db.dbWriter(new DataModel("5", "jeg hedder Kasper5"));
        }
        finally
        {
            try
            {
                System.out.println(db.dbReader());
            }
            catch (UnsupportedEncodingException e)
            {
                e.printStackTrace();
            }
        }
    }
}
