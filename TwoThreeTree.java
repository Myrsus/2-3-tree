import java.util.ArrayList;
class TwoThreeTree {
    private Node root;

    // Класс ноды
    private class Node {
        int[] keys;
        Node[] children;

        public Node() {
            keys = new int[2];
            children = new Node[3];
        }
    }

    // Конструктор
    public TwoThreeTree() {
        root = null;
    }

    // Вставка
    public void insert(int key) {
        root = insert(root, key);
    }

    // Помощник для метода вставки
    private Node insert(Node node, int key) {
        if (node == null) {
            node = new Node();
            node.keys[0] = key;
        } else {
            if (node.children[0] == null) { //  node
                if (node.keys[1] == 0) { // 2-node
                    if (key < node.keys[0]) {
                        node.keys[1] = node.keys[0];
                        node.keys[0] = key;
                    } else {
                        node.keys[1] = key;
                    }
                } else { // 3-node, нужно деление
                    node = split(node, key);
                }
            } else { // внутренняя node
                if (key < node.keys[0]) {
                    node.children[0] = insert(node.children[0], key);
                } else if (node.keys[1] == 0 || key < node.keys[1]) {
                    node.children[1] = insert(node.children[1], key);
                } else {
                    node.children[2] = insert(node.children[2], key);
                }
            }
        }
        return node;
    }

    // Помощник для разделения ноды
    private Node split(Node node, int key) {
        Node newNode = new Node();
        Node left = new Node();
        Node right = new Node();

        // Добавление ключей в узлы
        if (key < node.keys[0]) {
            left.keys[0] = key;
            right.keys[0] = node.keys[1];
        } else if (node.keys[1] != 0 && key < node.keys[1]) {
            left.keys[0] = node.keys[0];
            right.keys[0] = node.keys[1];
            newNode.keys[0] = key;
        } else {
            left.keys[0] = node.keys[0];
            right.keys[0] = key;
        }

        // Присваивание детей
        left.children[0] = node.children[0];
        left.children[1] = node.children[1];
        right.children[0] = node.children[1];
        right.children[1] = node.children[2];

        newNode.children[0] = left;
        newNode.children[1] = right;
        return newNode;
    }


    // Метод для вывода дерева
    public void printTree() {
        printTree(root, "");
    }

    // Вспомогательный метод для вывода дерева
    private void printTree(Node node, String indent) {
        if (node == null) {
            return;
        }

        if (node.children[0] == null) { // leaf node
            for (int i = 0; i < node.keys.length && node.keys[i] != 0; i++) {
                System.out.println(indent + node.keys[i]);
            }
        } else { // internal node
            for (int i = 0; i < node.children.length && node.children[i] != null; i++) {
                if (i < node.keys.length && node.keys[i] != 0) {
                    System.out.println(indent + node.keys[i]);
                }
                printTree(node.children[i], indent + "  ");
            }
        }
    }

    // Метод для поиска ключа в дереве
    public boolean find(int key) {
        return find(root, key);
    }

    // Вспомогательный метод поиска ключа
    private boolean find(Node node, int key) {
        if (node == null) {
            return false; // Ключ не найден
        }
        if (key < node.keys[0]) {
            return find(node.children[0], key); // Идём в левое поддерево
        } else if (node.keys[0] == key) {
            return true; // Ключ найден
        } else if (node.keys[1] == 0) {
            return find(node.children[1], key); // Идём в правое поддерево (при 2-узле)
        } else if (key < node.keys[1]) {
            return find(node.children[1], key); // Идём в центральное поддерево (при 3-узле)
        } else if (node.keys[1] == key) {
            return true; // Ключ найден
        } else {
            return find(node.children[2], key); // Идём в правое поддерево (при 3-узле)
        }
    }

    // Метод удаления ключа из дерева, который перестраивает дерево
    public void delete(int key) {
        ArrayList<Integer> keysList = new ArrayList<>();
        // Получаем все ключи, кроме удаляемого
        getKeys(root, key, keysList);
        // Создаем новое пустое дерево
        root = null;
        // Перестраиваем дерево с оставшимися ключами
        for (int k : keysList) {
            insert(k);
        }
    }

    // Вспомогательный метод для получения всех ключей дерева кроме удаляемого
    private void getKeys(Node node, int keyToDelete, ArrayList<Integer> keysList) {
        if (node != null) {
            // Проверяем узел на наличие ключей, исключая удаляемый ключ
            for (int i = 0; i < node.keys.length; i++) {
                if (node.keys[i] != 0 && node.keys[i] != keyToDelete) {
                    keysList.add(node.keys[i]);
                }
            }
            // Рекурсивно обходим всех потомков
            for (int i = 0; i < node.children.length; i++) {
                getKeys(node.children[i], keyToDelete, keysList);
            }
        }
    }


}