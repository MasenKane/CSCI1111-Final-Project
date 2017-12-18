import java.io.*;
import jm.music.data.*;
import jm.JMC;
import jm.util.*;
import java.util.*;

public class FinalProject implements JMC
{
   public static void main(String[] args)
   {
      Scanner inScan = null;
		String[] year = new String [10];
		int[] mortality= new int[10];
		try 
		{
			BufferedReader b = new BufferedReader(new FileReader("mortality.txt"));
            		inScan = new Scanner(b);
			int yearIndex=0;
	        	while (inScan.hasNextLine()) 
			{
				String line = inScan.nextLine();
				String[] cols = line.split(",");
				year[yearIndex]=cols[0];
				mortality[yearIndex] = Integer.parseInt(cols[1]);
				yearIndex++;	
                        }
			inScan.close();
		}
		catch(FileNotFoundException e)
		{
			System.out.println("There was an error reading the file");
		}

		try
		{
			int sum =0;
			for (int i = 0; i < mortality.length; i++)
			{
				sum += mortality[i];
			}
			int avg = sum / mortality.length;
			String str = "Mortality avg: "+ avg +"\n";
			BufferedWriter writer = new BufferedWriter(new FileWriter("mortalityOut.txt"));
			writer.write(str);
			writer.close();
		}
		catch(IOException i)
		{
			System.out.println("There was an error writing the file");
		}
      
      int sum = 0;
		for (int i = 0; i < mortality.length; i++)
		{
			sum = sum + mortality[i];
		}
		int avg = sum / mortality.length;
      
      scale(mortality);
      Play.midi(makePhrase(mortality, avg));
   }
   
   public static void scale(int a[])
   {
      int sum = 0;
      int min = Integer.MAX_VALUE;
      int max = Integer.MIN_VALUE;
      int myRange = 50;
      int range;
      
      for (int i = 0; i < a.length; i++)
      {
         if (a[i] < min)
            min = a[i];
         if (a[i] > max)
            max = a[i];
      }
      range = max - min;
      
      for (int i = 0; i < a.length; i++)
         a[i] = ((50 + (a[i] - min))*myRange/range) - 128;
   }
   
   public static Phrase makePhrase(int a[], int avg)
   {
      Phrase notes = new Phrase(0.0);
      notes.setInstrument(GOBLIN);
      notes.setTempo(avg);
      
      for (int i = 0; i < a.length; i++)
      {
         notes.addNote(new Note(a[i],HN));
         notes.addNote(new Note(REST,QN));
      }
      
      return notes;
   }
}