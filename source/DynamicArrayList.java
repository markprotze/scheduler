package packager;

import java.util.ArrayList;

@SuppressWarnings("serial")
class DynamicArrayList<T> extends ArrayList<ArrayList<T>> {
	public void addToInnerArray(int index, int index2, T element) {
		while (index >= this.size()) {
			this.add(new ArrayList<T>());
        }
		ArrayList<T> inner = this.get(index);
		while (index2 >= inner.size()) {
			inner.add(null);
        }
        inner.set(index2, element);
    }
}