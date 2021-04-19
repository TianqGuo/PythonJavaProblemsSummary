/**
 * For ParkingLot class it includes the following main class:
 * <p>
 * 1) parkingSpot: this is base class which is used to check if vehicle is fit for the spot, and show/change spot status (parked/empty)
 * this class will be used in level class.
 * 2) level: this class supports list of all the parkingSpots for different size cars in one level and related park/leave method. 
 * 3) parkingLot: this class supports list of each level in one parkingLot and related park/leave method.
 * 
 * Also this ParkingLots class supports one more type of vehicle: Motor, and it supports checking left spots. 
 * 
 * <p>
 * 
 *Answer Author: Tianquan Guo
 *Date: 4/19/2021
 */


package parkingLot;

import java.util.*;

/**
 * For ParkingLot class it includes the following main class:
 * <p>
 * 1) parkingSpot: this is base class which is used to check if vehicle is fit for the spot, and show/change spot status (parked/empty)
 * this class will be used in level class.
 * 2) level: this class supports list of all the parkingSpots for different size cars in one level and related park/leave method. 
 * 3) parkingLot: this class supports list of each level in one parkingLot and related park/leave method.
 * 
 * Also this ParkingLots class supports one more type of vehicle: Motor, and it supports checking left spots. 
 * 
 * <p>
 * 
 */

public class ParkingLot {
	
	public int spotsLeft;

//	define base vehicle size along with getter method and constructor	
	public enum vehicleSize{
		Motor(0),
		Sedan(1),
		Truck(2);
		
		private int size;
		
		vehicleSize(int size) {
			this.size = size;
		}
		
		public int sizeGetter() {
			return size;
		}
	}

//	define abstract class vehicel
	public abstract class vehicle {
		public abstract vehicleSize sizeGetter();
	}

//	define three vehicle types
	public class motor extends vehicle {
		@Override
		public vehicleSize sizeGetter() {
			return vehicleSize.Motor;
		}
	}
	
	public class sedan extends vehicle {
		@Override
		public vehicleSize sizeGetter() {
			return vehicleSize.Sedan;
		}
	}
	
	public class truck extends vehicle {
		@Override
		public vehicleSize sizeGetter() {
			return vehicleSize.Truck;
		}
	}

//	define basic parking spot, which can check if vehicle fit this spot and perform park and leave action	
	public class parkingSpot {
		private vehicleSize size;
		private vehicle current;
		
		parkingSpot(vehicleSize size) {
			this.size = size;
		}
		
		public boolean isFit (vehicle cur) {
			return current == null && (cur.sizeGetter().sizeGetter() <= this.size.sizeGetter());
		}
		
		private void park(vehicle cur) {
			this.current = cur;
			spotsLeft--;
		}
		
		private void leave() {
			this.current = null;
			spotsLeft++;
		}
		
		private vehicle getCur() {
			return current;
		}
	}

//	define level class which can check each spots in this level has available slots, and perform park and leave action 
//	through parkingSpot class
	public class level {
		private List<parkingSpot> spots;		
		
		level(int AllSpots) {
			List<parkingSpot> list = new ArrayList<>();
			
			int i = 0;
			for (; i < AllSpots * 2 / 4 ; i++) {
				list.add(new parkingSpot(vehicleSize.Sedan));
			}
			
			while ( i < AllSpots * 3 / 4) {
				list.add(new parkingSpot(vehicleSize.Motor));
				i++;
			}
			
			while (i < AllSpots) {
				list.add(new parkingSpot(vehicleSize.Truck));
				i++;
			}
			
			spots = Collections.unmodifiableList(list);
		}
		
		boolean hasSpot(vehicle cur) {
			for (parkingSpot s : spots) {
				if (s.isFit(cur)) {
					return true;
				}
			}
			return false;
		}
		
		boolean park (vehicle cur) {
			for (parkingSpot s : spots) {
				if (s.isFit(cur)) {
					s.park(cur);
					return true;
				}
			}
			return false;
		}
		
		boolean leave(vehicle cur) {
			for (parkingSpot s: spots) {
				if (s.getCur() == cur ) {
					s.leave();
					return true;
				}
			}
			return false;
		}
	}	

//	This method initialize parking lot levels, spots and spotsLeft.And perform park and leave action 
//	through parkingSpot and level class
	public class parkingLot {
		private level[] levels;
		
		public parkingLot (int numLevels, int numSpotsOneLevel) {
			spotsLeft = numLevels * numSpotsOneLevel;
			levels = new level[numLevels];
			for (int i = 0; i < numLevels; i++) {
				levels[i] = new level(numSpotsOneLevel);
			}
		}
		
		public boolean hasSpot(vehicle cur) {
			for (level l : levels) {
				if (l.hasSpot(cur)) {
					return true;
				}
			}
			return false;
		}
		
		public boolean park(vehicle cur) {
			for (level l : levels) {
				if (l.park(cur)) {
					return true;
				}
			}
			return false;
		}
		
		public boolean leave (vehicle cur) {
			for (level l: levels) {
				if (l.leave(cur)) {
					return true;
				}
			}
			return false;
		}
	}
	
	
	
	
}
