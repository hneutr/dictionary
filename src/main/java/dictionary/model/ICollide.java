package dictionary.model;

/**
 * @author alexkillian
 *
 * Interface that says which classes can detect collisions.
 */
public interface ICollide<T> {

	/**
	 * Tests if the given object collides with this object.
	 * Return true if so, false otherwise.
	 * 
	 * @param o The object to test against.
	 * @return
	 */
	public boolean collides(T o);
}
