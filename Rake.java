import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.io.*;
public class Rake {
	ArrayList<String> stoplist = new ArrayList<String>();
	HashMap<String,Float> candidates_score = new HashMap<String,Float>();
	String[] lines;
	String[] keywords;
	public Rake(String[] lines , ArrayList<String> x)
	{
		stoplist.addAll(x);
		this.lines=lines;//sets the text
		this.get_keys(this.lines);//execute the algorithm
	}	
	public int contains_stop_orr_comma(String x)
	{
		for(int i=0;i<x.length();i++)
		{
			if(x.charAt(i)=='.' || x.charAt(i)==',')
			{
				return 1;
			}
		}
		return 0;
	}
	public int inStopList(String x)
	{
		for(String s:this.stoplist)
		{
			if(x.equals(s))
			{
				return 1;
			}
		}
		return 0;
	}
	public void get_keys(String[] lines)
	{
		ArrayList<String> candidates = new ArrayList<String>();
		for(int i=0;i<lines.length;i++)
		{
			String pline="";
			String[] tok = lines[i].split(" ");
			for(int j=0;j<tok.length;j++)
			{
				if(inStopList(tok[j])==1)
				{
					pline += " # ";
				}else if(contains_stop_orr_comma(tok[j])==1)
				{
					String tmp="";
					char[] c = tok[j].toCharArray();
					for(char x:c)
					{
						if(x=='.' || x==',')
						{
							tmp += " # ";
						}else
						{
							tmp+=x;
						}
					}
					pline+=tmp;
				}else
				{
					pline+= tok[j] + " ";
				}
			}
			lines[i] = pline;
		}
		for(String s:lines)
		{
			for(String c:s.split("#"))
			{
				if(c.trim().equals("") == false) {
				candidates.add(c.trim().toLowerCase());
				}
			}
		}//candidates selected!
		setMapScore(candidates);
	}
	private void setMapScore(ArrayList<String> candidates)
	{
		for(String c:candidates)
		{
			//System.out.println(c);
		}
		HashMap<String,Integer> freq = new HashMap<String,Integer>();
		HashMap<String,Integer> deg = new HashMap<String,Integer>();
		for(String c:candidates)
		{
			String[] tok = c.split(" ");
			for(String w:tok)
			{
				if(freq.containsKey(w)==false)
				{
					freq.put(w,0);
					deg.put(w,0);
				}else
				{
					continue;
				}
			}
		}
		for(Map.Entry<String,Integer> e:freq.entrySet())
		{
			int f=e.getValue();
			for(String c:candidates)
			{
				for(String a:c.split(" "))
				{
					//System.out.println(a);
					if(a.equals(e.getKey()))
					{
						f+=1;
					}
				}
			}
			//System.out.println();
			e.setValue(f);
		}
		for(Map.Entry<String,Integer> e:deg.entrySet())
		{
			int degree=e.getValue();
			for(String s:candidates)
			{
				String[] tok=s.split(" ");
				for(String w:tok)
				{
					if(w.equals(e.getKey())==true)
					{
						degree = degree + tok.length;
					}
				}
			}
			e.setValue(degree);
		}
		for(String c:candidates)
		{
			float score=0.0f;
			String[] words = c.split(" ");
			for(String w:words)
			{
				score = score + (float)deg.get(w)/(float)freq.get(w);
			}
			candidates_score.put(c, score);
		}
		ArrayList<candidate> tmp = new ArrayList<candidate>();
		for(Map.Entry<String, Float> e:candidates_score.entrySet())
		{
			//System.out.println(e.getKey() + " >> " + e.getValue());
			tmp.add(new candidate(e.getKey() ,e.getValue()));
		}
		this.keywords = new String[12];
		candidate[] t = tmp.toArray(new candidate[tmp.size()]);
		quickSort(t,0,t.length-1);
		for(int i=0;i<12;i++)
		{
			keywords[i] = t[i].text;
		}
		System.out.println("Keywords are: ");
		for(String s:keywords)
		{
			System.out.println(s);
		}
	}
	private class candidate{
		String text;
		float score;
		public candidate(String x,float f)
		{
			this.text = x;
			this.score=f;
		}
	}
	//end of main functions
	
	
	
	
	
	
	
	
	
	private static int partition(candidate arr[], int left, int right) //change
	{
	      int i = left, j = right;
	      candidate tmp; //change
	      candidate pivot = arr[(left + right) / 2];  //comparator***
	      while (i <= j) {
	            while (arr[i].score > pivot.score) //comparator***
	                  i++;
	            while (arr[j].score < pivot.score)   //comparator***
	                  j--;
	            if (i <= j) {
	                  tmp = arr[i];
	                  arr[i] = arr[j];
	                  arr[j] = tmp;
	                  i++;
	                  j--;
	            }
	      };
	      return i;
	}
	private static void quickSort(candidate arr[], int left, int right) { //change
	      int index = partition(arr, left, right);
	      if (left < index - 1)
	            quickSort(arr, left, index - 1);
	      if (index < right)
	            quickSort(arr, index, right);
	}
}
