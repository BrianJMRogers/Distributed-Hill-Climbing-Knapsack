public class History {
	private Item old;
	private Item young; 
	protected String action;
	
	public History(Item old, Item young) {
		this.old = old;
		this.young = young;
		action = old.name + " --> " + young.name;	
	} // History
	
	public String getAction() {
		return action;
	} // getAction
	
	public String toString() {
		return action + "\n";
	} // toString
} // History
