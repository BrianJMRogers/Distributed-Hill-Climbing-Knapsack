import java.util.Collections;
import java.util.*;

public class Item implements Comparator<Item> {
	
	// Variable Dictionary
	String name;
	double cost;
	double benefit;
	String desc;
	Double ratio;
	boolean freebie;
	
	/******************************************************************************************
	* Constructors: Provides several constructors for an Item, each with different parameters.
	******************************************************************************************/
	// Constructor
	public Item() {
		name = null;
		cost = -1;
		benefit = -1;
		desc = null;
		ratio = -1.0;
		freebie = false;
	} // Item ()
	
	// Constructor
	public Item(Item i) {
		name = i.name;
		cost = i.cost;
		benefit = i.benefit;
		desc = i.desc;
		ratio = i.ratio;
		freebie = (cost == 0 ? true : false);
	}
	
	// Constructor 
	public Item(String name) {
		this.name = name;
		cost = -1;
		benefit = -1;
		desc = null;
		ratio = -1.0;
		freebie = false;
	} // Item (N)
	
	// Constructor
	public Item(String name, String desc) {
		this.name = name;
		cost = -1;
		benefit = -1;
		this.desc = desc;
		ratio = -1.0;
		freebie = false;
	} // Item (N, D)

	// Constructor	
	public Item(String name, double cost, double benefit) {
		this.name = name;
		this.cost = cost;
		this.benefit = benefit;
		desc = null;
		ratio = calculateRatio();
		freebie = (cost == 0 ? true : false);
	} // Item (N, C, B)

	// Constructor	
	public Item(String name, double cost, double benefit, String desc) {
		this.name = name;
		this.cost = cost;
		this.benefit = benefit;
		this.desc = desc;
		ratio = calculateRatio();
		freebie = (cost == 0 ? true : false);
	} // Item (N, C, B, D)
	
	/*******************************************************
	* Accessor Methods for the data in an Item.
	*******************************************************/
	// getName()
	public String getName() {
		return name;
	} // getName()
	
	// getCost()
	public double getCost() {
		return cost;
	} // getCost()
	
	// getBenefit()
	public double getBenefit() {
		return benefit;
	} // getBenefit()
	
	// getDescription()
	public String getDescription() {
		return desc;
	} // getDescription()
	
	// getRatio()
	public Double getRatio() {
		return ratio;
	} // getRatio()
	
	/********************************************************
	* Mutator Methods for the data in an Item.
	********************************************************/
	// setName(String n)
	public void setName(String n) {
		name = n;
	} // setName()
	
	// setCost(double c)
	public void setCost(double c) {
		cost = c;
		ratio = calculateRatio();
	} // setCost()
	
	// setBenefit(double b)
	public void setBenefit(double b) {
		benefit = b;
		ratio = calculateRatio();
	} // setBenefit()
	
	// setDescription(String d)
	public void setDescription(String d) {
		desc = d;
	} // setDescription()
	
	/**********************************************************
	* Methods of an Item.
	**********************************************************/
	// calculateRatio(): Calculates the cost/benefit ratio
	public Double calculateRatio() {
		if (benefit == 0) {
			return 0.000000000000000001;
		} else if (cost == 0) {
			return 0.0;
		} else if (cost == -1 || benefit == -1) {
			return 0.000000000000000001;
		} return cost/benefit;
	} // calculateRatio()
	
	/**
	 * @Override
	 * compareTo: 
	 * Overrides the compareTo method
	 *
	 * @author: Jake Ballinger
	 */
	public int compareTo(Item i) {
		return (this.ratio).compareTo(i.ratio);
	}
	
	@Override
	public int compare(Item i, Item i1) {
		return (int) (double) (i.ratio - i1.ratio);
	}
	
	// toString(): Prints a String representation of an Item
	public String toString() {
		return "Name: " + name + ", Cost: " + cost + ", Benefit: " + benefit + ", Description: " + desc + ", Cost-Benefit Ratio: " + ratio; 
	} // toString()
	
	// wipe(): Resets all of the elements in the Item to default or null values.
	public void wipe() {
		name = null;
		cost = 0;
		benefit = 0;
		desc = null;
		ratio = -1.0;
	} // wipe()
} // class Item
