package me.santipingui58.bungee.utils;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import net.md_5.bungee.BungeeCord;



public class Utils {
	
	private static Utils manager;	
	 public static Utils getUtils() {
	        if (manager == null)
	        	manager = new Utils();
	        return manager;
	    }
	 

	 
		public <T> Set<T> newShuffledSet(Collection<T> collection) {
		    List<T> shuffleMe = new ArrayList<T>(collection);
		    Collections.shuffle(shuffleMe);
		    return new LinkedHashSet<T>(shuffleMe);
		}
		
		
	 
	 //Check if a set has duplicate values on it
	 public <T> boolean hasDuplicate(Iterable<T> all) {
		    Set<T> set = new HashSet<T>();
		    // Set#add returns false if the set does not change, which
		    // indicates that a duplicate element has been added.
		    for (T each: all) if (!set.add(each)) return true;
		    return false;
		}
	 


	
	public String getNamesFromList(List<String> list) {
		String p = "";
		for (String s : list) {
		if (p.equalsIgnoreCase("")) {
			p = s;
		} else {
			p = p+", " + s;
		}
		}
		return p;
	}
	 
	  public <T> LinkedHashMap<T, Integer> sortByValue(Map<T,Integer> hm) { 
	        // Create a list from elements of HashMap 
	        List<Map.Entry<T, Integer> > list = 
	               new LinkedList<Map.Entry<T, Integer> >(hm.entrySet()); 
	  
	        // Sort the list 
	        Collections.sort(list, new Comparator<Map.Entry<T, Integer> >() { 
	            public int compare(Map.Entry<T, Integer> o2,  
	                               Map.Entry<T, Integer> o1) 
	            { 
	                return (o1.getValue()).compareTo(o2.getValue()); 
	            } 
	        }); 
	
	        LinkedHashMap<T, Integer> temp = new LinkedHashMap<T, Integer>(); 
	        for (Map.Entry<T, Integer> aa : list) { 
	            temp.put(aa.getKey(), aa.getValue()); 
	        } 
	        return temp; 
}

	
	  
	  //Return the difference between 2 dates, in miliseconds
	  public long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
		    long diffInMillies = date2.getTime() - date1.getTime();
		    return timeUnit.convert(diffInMillies,TimeUnit.MILLISECONDS);
		}
	  
	  //Check if a list contains the String b, with ignore case
	  public boolean containsIgnoreCase(List<String> list, String b) {
		  
		  for (String o : list) {
			  if (containsIgnoreCase(o,b)) {
				  return true;
			  }
		  }
		return false;
		  
	  }
	  
	  //Check if the String fullStr has inside of it the String serachStr, with ignore case
	  public boolean containsIgnoreCase(String fullStr, String searchStr)   {
		    if(fullStr == null || searchStr == null) return false;

		    final int length = searchStr.length();
		    if (length == 0)
		        return true;

		    for (int i = fullStr.length() - length; i >= 0; i--) {
		        if (fullStr.regionMatches(true, i, searchStr, 0, length))
		            return true;
		    }
		    return false;
		}
	  
		
		
		//Convert seconds to mm:ss
		public String time(int s) {
			
			int minutes = s / 60;
			int seconds = s % 60;

			return String.format("%02d:%02d",  minutes, seconds);
		  }
		



		
		//Converts seconds to days,hours,minutes and seconds. Used in the /stats command
		 public String secondsToDate(int i) {	 
			 int days = (i % 604800) / 86400;
			 int hours = ((i % 604800) % 86400) / 3600;
			 int minutes = (((i % 604800) % 86400) % 3600) / 60;
			 int seconds = i % 60;
			if (days > 0) {
				return String.format("%01d %01dh %01dm %01ds", days, hours, minutes, seconds);
			} else if (hours > 0) {
				return String.format("%01dh %01dm %01ds", hours, minutes, seconds);
			} else if (minutes > 0) {
				return String.format("%01dm %01ds", minutes, seconds);
			} else {
				return String.format("%01ds", seconds);
			}
		 }
		 
		 
		//Converts seconds to years,months,weeks days, and hours. Used in the Online time Ranking
		public String minutesToDate(int i) {
			
			i = i/60;
			return i+" hours";
			
			/*int years =  i / 525600;
			int months = (i % 525600) / 43800;
			int weeks = ((i % 525600) % 43800) / 10080;
			int days = (((i % 525600) % 43800) % 10080) / 1140;
			int hours = ((((i % 525600) % 43800) % 10080) % 1140) / 60;
			 if (years > 0) {
				 return String.format("%01dyears %01dmonths %01dweeks %01ddays %01dhours", years, months, weeks, days, hours);
			 } else if (months > 0) {
				 return String.format("%01dmonths %01dweeks %01ddays %01dhours", months, weeks, days,hours);
			 } else if (weeks > 0) {
				 return String.format("%01dweeks %01ddays %01dhours",weeks, days,hours);
			 } else if (days > 0) {
				 return String.format("%01ddays %01dhours",days,hours);
			 } else if (hours > 0) {
				 return String.format("%01dhours",hours);
			 } else {
				 return "Less than an hour.";
			 }
			
		*/		  
		 }
		

		public String getStringMoney(int money) {
			
			if (money>=1000000) {
				double i = (double) money/ (double) 1000000;
				String d =  new DecimalFormat("#.##").format(i);
				return d+"M";
			} else if (money >=1000){
				double i = (double)money/(double) 1000;
				String d =  new DecimalFormat("#.##").format(i);
				return d+"K";
			}else  {
				return String.valueOf(money);
			}
		}



		public String getPlayerNamesFromList(List<UUID> list) {		 
		 String p = "";
		 BungeeCord bungee =  BungeeCord.getInstance();
		 for (UUID sp : list) {
			if(p.equalsIgnoreCase("")) {	
			p =bungee.getPlayer(sp).getName();	
			}  else {
				p = p+", " + bungee.getPlayer(sp).getName();
			}
		 }
		 
		return p;
	}
		
}


