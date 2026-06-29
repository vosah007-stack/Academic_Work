public class RedBlackTree<T extends Comparable<T>> {

    /* Root of the tree. */
    RBTreeNode<T> root;

    static class RBTreeNode<T> {

        final T item;
        boolean isBlack;
        RBTreeNode<T> left;
        RBTreeNode<T> right;

        /**
         * Creates a RBTreeNode with item ITEM and color depending on ISBLACK
         * value.
         * @param isBlack
         * @param item
         */
        RBTreeNode(boolean isBlack, T item) {
            this(isBlack, item, null, null);
        }

        /**
         * Creates a RBTreeNode with item ITEM, color depending on ISBLACK
         * value, left child LEFT, and right child RIGHT.
         * @param isBlack
         * @param item
         * @param left
         * @param right
         */
        RBTreeNode(boolean isBlack, T item, RBTreeNode<T> left,
                   RBTreeNode<T> right) {
            this.isBlack = isBlack;
            this.item = item;
            this.left = left;
            this.right = right;
        }
    }

    /**
     * Creates an empty RedBlackTree.
     */
    public RedBlackTree() {
        root = null;
    }

    /**
     * Flips the color of node and its children. Assume that NODE has both left
     * and right children
     * @param node
     */
    void flipColors(RBTreeNode<T> node) {
        boolean isBlackParent = node.isBlack;
        boolean isBlackChild = node.left.isBlack;
        node.isBlack = isBlackChild;
        node.left.isBlack = isBlackParent;
        node.right.isBlack = isBlackParent;
    }

    /**
     * Rotates the given node to the right. Returns the new root node of
     * this subtree. For this implementation, make sure to swap the colors
     * of the new root and the old root!
     * @param node
     * @return
     */
    RBTreeNode<T> rotateRight(RBTreeNode<T> node) {
        RBTreeNode<T> initNodeLeft = node.left;
        node.left = null;
        RBTreeNode<T> initNode = node;
        node = initNodeLeft;
        RBTreeNode<T> initNodeRight = node.right;
        node.right = initNode;
        node.right.left = initNodeRight;
        boolean tempNodeIsBlack = node.isBlack;
        node.isBlack = node.right.isBlack;
        node.right.isBlack = tempNodeIsBlack;
        return node;
    }

    /**
     * Rotates the given node to the left. Returns the new root node of
     * this subtree. For this implementation, make sure to swap the colors
     * of the new root and the old root!
     * @param node
     * @return
     */
    RBTreeNode<T> rotateLeft(RBTreeNode<T> node) {
        RBTreeNode<T> initNodeRight = node.right;
        node.right = null;
        RBTreeNode<T> initNode = node;
        node = initNodeRight;
        RBTreeNode<T> initNodeLeft = node.left;
        node.left = initNode;
        node.left.right = initNodeLeft;
        boolean tempNodeIsBlack = node.isBlack;
        node.isBlack = node.left.isBlack;
        node.left.isBlack = tempNodeIsBlack;
        return node;
    }

    /**
     * Helper method that returns whether the given node is red. Null nodes (children or leaf
     * nodes) are automatically considered black.
     * @param node
     * @return
     */
    private boolean isRed(RBTreeNode<T> node) {
        return node != null && !node.isBlack;
    }

    /**
     * Inserts the item into the Red Black Tree. Colors the root of the tree black.
     * @param item
     */
    public void insert(T item) {
        root = insertHelper(root, item);
        root.isBlack = true;
    }

    /**
     * Helper method to insert the item into this Red Black Tree. Comments have been provided to help break
     * down the problem. For each case, consider the scenario needed to perform those operations.
     * Make sure to also review the other methods in this class!
     * @param node
     * @param item
     * @return
     */
    private RBTreeNode<T> insertHelper(RBTreeNode<T> node, T item) {
        if (node == null) {
            return new RBTreeNode<T>(true, item);
        } else {
            if (item.compareTo(node.item) < 0) {
                if (node.left == null) {
                    node.left = new RBTreeNode<T>(false, item);
                } else {
                    node.left = insertHelper(node.left, item);
                }
            } else {
                if (node.right == null) {
                    node.right = new RBTreeNode<T>(false, item);
                } else {
                    node.right = insertHelper(node.right, item);
                }
            }
            boolean isWrong = true;
            while (isWrong) {
                isWrong = false;
                if (isRed(node.left) && isRed(node.right)) {
                    flipColors(node);
                    isWrong = true;
                }
                if (isRed(node.right)) {
                    node = rotateLeft(node);
                    isWrong = true;
                }
                if (isRed(node.left) && isRed(node.left.left)) {
                    node = rotateRight(node);
                    isWrong = true;
                }
            }
            return node;
        }
    }


}
