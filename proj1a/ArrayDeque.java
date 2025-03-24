public class ArrayDeque<T> {
    private T[] items;
    private int size;
    private int nextFirst;  // 指向下一个 addFirst 的位置
    private int nextLast;   // 指向下一个 addLast 的位置
    private static final int INIT_CAPACITY = 8;

    // 创建一个空数组双端队列
    public ArrayDeque() {
        items = (T[]) new Object[INIT_CAPACITY];
        size = 0;
        nextFirst = 0;
        nextLast = 1;
    }

    // 创建深层副本
    public ArrayDeque(ArrayDeque<T> other) {  // 使用泛型类型参数
        this();
        for (int i = 0; i < other.size(); i++) {
            addLast(other.get(i));
        }
    }

    // 在双端队列的前面添加一个元素
    public void addFirst(T item) {
        if (size == items.length) {
            resize(items.length * 2);
        }
        items[nextFirst] = item;
        nextFirst = (nextFirst - 1 + items.length) % items.length;
        size++;
    }

    // 在双端队列的后面添加一个元素
    public void addLast(T item) {
        if (size == items.length) {
            resize(items.length * 2);
        }
        items[nextLast] = item;
        nextLast = (nextLast + 1) % items.length;
        size++;
    }

    // 移除并返回双端队列前面的元素
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        if (items.length >= 16 && size < items.length / 4) {
            resize(items.length / 2);
        }
        nextFirst = (nextFirst + 1) % items.length;
        T item = items[nextFirst];
        items[nextFirst] = null;
        size--;
        return item;
    }

    // 移除并返回双端队列后面的元素
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        if (items.length >= 16 && size < items.length / 4) {
            resize(items.length / 2);
        }
        nextLast = (nextLast - 1 + items.length) % items.length;
        T item = items[nextLast];
        items[nextLast] = null;
        size--;
        return item;
    }

    // 返回双端队列中指定索引的元素
    public T get(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        return items[(nextFirst + 1 + index) % items.length];
    }

    // 返回双端队列的大小
    public int size() {
        return size;
    }

    // 判断双端队列是否为空
    public boolean isEmpty() {
        return size == 0;
    }

    // 调整数组大小
    private void resize(int capacity) {
        T[] newItems = (T[]) new Object[capacity];
        for (int i = 0; i < size; i++) {
            newItems[i] = get(i);
        }
        items = newItems;
        nextFirst = capacity - 1;
        nextLast = size;
    }

    // 打印双端队列中的所有元素
    public void printDeque() {
        for (int i = 0; i < size; i++) {
            System.out.print(get(i) + " ");
        }
        System.out.println();
    }
}