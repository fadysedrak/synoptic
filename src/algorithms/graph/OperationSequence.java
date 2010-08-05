package algorithms.graph;

import java.util.LinkedList;

import model.Partition;
import model.PartitionGraph;
import model.SystemState;
import model.interfaces.IModifiableGraph;

/**
 * Links a couple of operation as a sequence together.
 * @author Sigurd Schneider
 *
 */
public class OperationSequence implements Operation {
	LinkedList<Operation> sequence = new LinkedList<Operation>();

	@Override
	public Operation commit(PartitionGraph g,
			IModifiableGraph<Partition> partitionGraph,
			IModifiableGraph<SystemState<Partition>> stateGraph) {
		OperationSequence rewindOperation = new OperationSequence();
		for (Operation op : sequence)
			rewindOperation.addFirst(g.apply(op));
		return rewindOperation;
	}

	/**
	 * Add an operation before the head of the sequence.
	 * @param operation the operation to add
	 */
	public void addFirst(Operation operation) {
		sequence.addFirst(operation);
	}
}
