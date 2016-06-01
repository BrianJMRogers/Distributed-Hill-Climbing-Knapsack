import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
@SuppressWarnings("unchecked")
public class Node {

	// v: the vector in the state space to which the node corresponds;
	protected Vector<Item> v;

	// parent: the parent node
	private Node parent;

	// history: how we created this node;
	protected History history;

	// favorite: the Item with the lowest ratio
	protected double r;

	/**
	 * Constructs a node with the specified state.
	 */
	public Node(Vector<Item> v) {
		this.v = v;
		r = playFavorites(v);
	} // node

	/**
	 * Constructs a node with the specified state, parent, and step
	 * cost.
	 *
	 * @param state: the state in the state space to which the node corresponds.
	 * @param history: the node swapped to obtain this state
	 */
	public Node(Vector<Item> v, Node parent, Item source, Item swap) {
		this(v);
		this.parent = parent;
		history = new History(source, swap);
		r = playFavorites(v);
	} // Node()

	public Node(Vector v, Node parent) {
		this(v);
		this.parent = parent;
		r = playFavorites(v);
	} // Node()

	public Node(Vector v, Item source, Item swap) {
		this(v);
		parent = null;
		history = new History(source, swap);
		r = playFavorites(v);
	} // Node()

	/**
	 * Returns the state in the state space to which the node corresponds.
	 *
	 * @return the state in the state space to which the node corresponds.
	 */
	public Vector getState() {
		return v;
	} // getState()

	/**
	 * Returns this node's parent node, from which this node was generated.
	 *
	 * @return the node's parenet node, from which this node was generated.
	 */
	public Node getParent() {
		return parent;
	} // getParent()

	/**
	 * Returns the cost of the path from the initial state to this node as
	 * indicated by the parent pointers.
	 *
	 * @return the cost of the path from the initial state to this node as
	 *         indicated by the parent pointers.
	 */
	public History getHistory() {
		return history;
	} // getHistory()

	/**
	 * Returns <code>true</code> if the node has no parent.
	 *
	 * @return <code>true</code> if the node has no parent.
	 */
	public boolean isRootNode() {
		return parent == null;
	} // isRootNode()

	/**
	 * playFavorites
	 * Finds the vector with the lowest ratio
	 *
	 * @author Jake Ballinger
	 */
	public static double playFavorites(Vector<Item> v) {
		return (Knapsack.calculateTotalCost(v) / Knapsack.calculateTotalBenefit(v));
	} // playFavorites()

	@Override
	public String toString() {
		StringBuilder built = new StringBuilder();
		built.append("*** STATE OF THE NODE ***\n\n");
		for (int i = 0; i < v.size(); i++) {
			built.append("\tElement at index " + Integer.toString(i) + ": \n");
			built.append("\t\t" + v.elementAt(i).toString() + "\n");
		} // for

		built.append("\tThe Parent Node: " + parent + "\n");
		built.append("\tThe History: " + history + "\n");

		return built.toString();
	} // toString()
} // Node
