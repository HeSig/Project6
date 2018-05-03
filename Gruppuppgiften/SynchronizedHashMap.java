package Extra;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SynchronizedHashMap<K,V> {
	private HashMap<K,V> hashMap = new HashMap<K,V>();
	
	public synchronized V put(K key, V value) {
		return hashMap.put(key, value);
	}
	
	public synchronized V remove(K key) {
		return hashMap.remove(key);
	}
	
	public synchronized void clear() {
		hashMap.clear();
	}
	
	public synchronized V get(K key) {
		return hashMap.get(key);
	}
	
	public String toString() {
		return hashMap.toString();
	}
	
	public synchronized Set<K> keySet() {
		return hashMap.keySet();
	}
	
	public synchronized Set<Map.Entry<K,V>> entrySet(){
		return hashMap.entrySet();
	}
	
	public synchronized Collection<V> values(){
		return hashMap.values();
	}
	
	public synchronized boolean containsKey(Object key) {
		return hashMap.containsKey(key);
	}
	
	public synchronized boolean containsValue(Object value) {
		return hashMap.containsValue(value);
	}
	
	public synchronized boolean isEmpty() {
		return hashMap.isEmpty();
	}
}
