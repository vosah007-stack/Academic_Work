package main;

import java.util.ArrayList;
import java.util.List;

public class BST {
    public class Node {
        Node left;
        Node right;
        Node parent;
        String value;
        public Node(String v, Node p) {
            value = v;
            left = null;
            right = null;
            parent = p;
        }
        public Node(String v, Node l, Node r, Node p) {
            value = v;
            left = l;
            right = r;
            parent = p;
        }
        public boolean add(String v) {
            if (v.equals(this.value)) {
                return false;
            }
            if (v.compareTo(this.value) > 0) {
                if (this.right == null) {
                    this.right = new Node(v, this);
                    return true;
                }
                return this.right.add(v);
            } else {
                if (this.left == null) {
                    this.left = new Node(v, this);
                    return true;
                }
                return this.left.add(v);
            }
        }

        public void successorLeft() {
            Node tempNode = this.left;
            if (tempNode.left == null && tempNode.right == null) {
                this.value = tempNode.value;
                tempNode.parent.left = null;
            } else if (tempNode.right == null) {
                this.value = tempNode.value;
                tempNode.successorLeft();
            } else {
                while (tempNode.right != null) {
                    tempNode = tempNode.right;
                }
                this.value = tempNode.value;
                if (tempNode.left != null) {
                    tempNode.successorLeft();
                } else {
                    tempNode.parent.right = null;
                }
            }
        }

        public void successorRight() {
            Node tempNode = this.right;
            if (tempNode.left == null && tempNode.right == null) {
                this.value = tempNode.value;
                tempNode.parent.right = null;
            } else if (tempNode.left == null) {
                this.value = tempNode.value;
                tempNode.successorRight();
            } else {
                while (tempNode.left != null) {
                    tempNode = tempNode.left;
                }
                this.value = tempNode.value;
                if (tempNode.right != null) {
                    tempNode.successorRight();
                } else {
                    tempNode.parent.left = null;
                }
            }
        }
        public boolean contains(String v) {
            if (v.equals(this.value)) {
                return true;
            } else if (v.compareTo(this.value) > 0) {
                if (this.right == null) {
                    return false;
                }
                return this.right.contains(v);
            } else {
                if (this.left == null) {
                    return false;
                }
                return this.left.contains(v);
            }
        }
    }
    Node root;
    int size;
    public BST() {
        size = 0;
    }
    public void add(String v) {
        if (size == 0) {
            root = new Node(v, null);
            size = 1;
        } else {
            if (root.add(v)) {
                size++;
            }
        }
    }
    public List<String> ordered() {
        List<String> returnList = new ArrayList<>();
        orderedHelper(returnList, root);
        return returnList;
    }
    private void orderedHelper(List<String> rL, Node n) {
        if (n != null) {
            if (n.left != null) {
                orderedHelper(rL, n.left);
            }
            rL.add(n.value);
            if (n.right != null) {
                orderedHelper(rL, n.right);
            }
        }
    }
    public int size() {
        return size;
    }
    public boolean contains(String v) {
        if (size == 0) {
            return false;
        }
        return root.contains(v);
    }

    public void remove(String v) {
        if (size > 0) {
            if (v.equals(root.value) && size == 1) {
                root = null;
                size--;
            } else {
                Node tempNode = root;
                while (tempNode.left != null || tempNode.right != null) {
                    if (v.equals(tempNode.value)) {
                        if (tempNode.left != null) {
                            tempNode.successorLeft();
                            size--;
                            return;
                        } else {
                            tempNode.successorRight();
                            size--;
                            return;
                        }
                    } else if (v.compareTo(tempNode.value) > 0) {
                        if (tempNode.right == null) {
                            return;
                        }
                        tempNode = tempNode.right;
                    } else {
                        if (tempNode.left == null) {
                            return;
                        }
                        tempNode = tempNode.left;
                    }
                }
                if (v.equals(tempNode.value)) {
                    if (tempNode.parent.left != null) {
                        tempNode.parent.left = null;
                    } else {
                        tempNode.parent.right = null;
                    }
                    size--;
                }
            }
        }
    }
}
