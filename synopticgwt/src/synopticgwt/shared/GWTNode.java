package synopticgwt.shared;

import java.io.Serializable;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * A representation of a graph node for GWT. Overall, this is a representation
 * of a partition node which acts as a bridge between Synoptic's server and the
 * front end.
 * 
 * @author a.w.davies.vio
 */
public class GWTNode implements Serializable {

    // TODO: Create a JSNI field that holds a reference
    // to a Dracula Graph node so that updates can be sent
    // to the node via this object rather than in JS
    // file.

    private static final long serialVersionUID = 1L;

    // The event type of the partition.
    private String eType = null;

    // The hashCode of the corresponding pNode.
    private Integer pNodeHash;

//    private JavaScriptObject jsNodeRef;

    public GWTNode() {
        // Default constructor to avoid serialization errors.
    }

    /**
     * Constructs a GWTNode object, which identifies itself via its event type.
     * The event type is the String representation of the event type to which
     * this node must correspond.
     * 
     * @param eType
     *            The String of the eType of the corresponding partition node.
     * @param hashCode
     *            The hashCode of the corresponding partition node.
     */
    public GWTNode(String eType, Integer hashCode) {
        assert eType != null;
        assert hashCode != null;
        this.eType = eType;
        this.pNodeHash = hashCode;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this)
            return true;
        if (!(other instanceof GWTNode))
            return false;
        GWTNode o = (GWTNode) other;
        return o.toString().equals(this.toString())
                && o.pNodeHash == this.pNodeHash;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + eType.hashCode();
        result = 31 * result + pNodeHash;
        return result;
    }

    @Override
    public String toString() {
        assert this.eType != null;
        return eType;
    }

    /**
     * @return The hash code for the Partition Node object that this object
     *         represents.
     */
    public int getPartitionNodeHashCode() {
        return pNodeHash;
    }

    /**
     * Used for adding a JS Node reference from Dracula.
     * 
     * @param node
     *            The JS node.
     */
    public native void addJSNodeRef(JavaScriptObject jsNode) /*-{
		// TODO: Make this add reference
		var node = jsNode;
    }-*/;
}
