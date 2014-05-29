/**
 * This class extends standard Hashtable class, adding searching methods
 * using key or value as a parameter of search.
 */
package translator;

import java.util.Hashtable;
import java.util.Map;

/**
 * @author Greshnikk
 * @version 0.01
 * @since 2014-05-28
 *
 * @param <K> Key type.
 * @param <V> Value type.
 */
public class HashtableExt<K,V> extends Hashtable<K,V> {

	private static final long serialVersionUID = 3452365286927370069L;

	/**
	 * Searches Hashtable key corresponding to input value. 
	 * 
	 * @param value Value to search for.
	 * @return Corresponding key, or null if value doesn't exist.
	 */
	public K searchKey (V value) {
		for (Map.Entry<K, V> entry : this.entrySet()) {
			if (entry.getValue().equals(value)) {
				return entry.getKey();
			}
		}
		return null;
	}
	
	/**
	 * Searches Hashtable value corresponding to input key.
	 * 
	 * @param key Key to search for.
	 * @return Corresponding value, or null if key doesn't exist.
	 */
	public V searchValue (K key) {
		for (Map.Entry<K, V> entry : this.entrySet()) {
			if (entry.getKey() == key) {
				return entry.getValue();
			}
		}
		return null;
	}
}