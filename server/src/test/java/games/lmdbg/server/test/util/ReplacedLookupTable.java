package games.lmdbg.server.test.util;

import games.lmdbg.rules.model.CardSet;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ReplacedLookupTable<T extends CardSet> implements AutoCloseable {
	Map<Integer, T> lookupTable;
	
	Map<Integer, T> oldValues;
	
	/**
	 * 
	 */
	public ReplacedLookupTable(Map<Integer, T> lookupTable, Collection <T> newValues) {
		this.lookupTable = lookupTable;
		
		this.oldValues = new HashMap<>(lookupTable);
		this.lookupTable.clear();
		for(T value : newValues) {
			lookupTable.put(value.getId(), value);
		}
	}
	
	@Override
	public void close() {
		this.lookupTable.clear();
		this.lookupTable.putAll(this.oldValues);
	}
	
}