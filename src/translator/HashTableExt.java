package translator;

import java.util.Hashtable;
import java.util.Map;

/**
 * @author Greshnikk
 * @version 0.01
 * @since 2014-05-28
 *
 * @param <K>
 * @param <V>
 */
public class HashTableExt<K,V> extends Hashtable<K,V> {

	private static final long serialVersionUID = 3452365286927370069L;

	/**
	 * @param value
	 * @return
	 */
	public K searchKey (V value) {
		for (Map.Entry<K, V> entry : this.entrySet()) {
			if (entry.getValue().equals(value)) {
				return entry.getKey();
			}
		}
		return null;
	}
	
	public V searchValue (K key) {
		for (Map.Entry<K, V> entry : this.entrySet()) {
			if (entry.getKey() == key) {
				return entry.getValue();
			}
		}
		return null;
	}
}
