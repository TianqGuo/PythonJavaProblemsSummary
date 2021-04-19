/**
 * This is a bad example, need to modified and improved.
 * 
 *Answer Author: Tianquan Guo
 *Date: 4/19/2021
 */

package parkingLot;


import parkingLot.ParkingLot.*;
import java.util.*;


public class Main {
	public static void main(String[] args) {
		  System.out.println("ParkingLot tests starts");
	      ParkingLot lots = new ParkingLot();
	      parkingLot lot = lots.new parkingLot(4, 10);
	      List<vehicle> list = new ArrayList<vehicle>();
	      for (int i = 0; i < 50; i++) {
	    	  vehicle cur = lots.new truck();
	    	  if (i % 3 == 0) {
	    		  cur = lots.new motor();
	    	  } else if (i % 3 == 1) {
	    		  cur = lots.new sedan();
	    	  } 
	    	  
	    	  
	    	  list.add(cur);
	    	  lot.park(cur);
	    	  System.out.println("Parking a new car, size is" + cur.sizeGetter().sizeGetter());
	    	  System.out.println("Testing if still has spot results is :" + lot.hasSpot(cur));
	    	  System.out.println("Current left parking lots are :" + lots.spotsLeft);
	      }
	      
	      for (vehicle cur : list) {
	    	  lot.leave(cur);
	    	  System.out.println("Leaving a new car, size is" + cur.sizeGetter().sizeGetter());
	    	  System.out.println("Testing if still has spot results is :" + lot.hasSpot(cur));
	    	  System.out.println("Current left parking lots are :" + lots.spotsLeft);
	      }
	      
	      
	}
	
	
}
