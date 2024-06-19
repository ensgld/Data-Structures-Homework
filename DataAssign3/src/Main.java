import java.io.*;

import java.util.*;


public class Main {
    public static class Node implements Comparable<Node> {
        String firstName, lastName, emailAddress, phoneNumber;
        Node right;
        Node left;

        public Node(String firstName, String lastName, String phoneNumber, String emailAddress) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.emailAddress = emailAddress;
            this.phoneNumber = phoneNumber;
            this.right = null;
            this.left = null;
        }

        @Override
        public String toString() {
            return
                    firstName + " " + lastName + " " + phoneNumber + " " + emailAddress;
        }

        @Override
        public int compareTo(Node o) {
            int lastNameComp = this.lastName.compareTo(o.lastName);

            return lastNameComp;

        }
    }


    public static class BinaryTreeAnalyzer {
        Node root;

        private ArrayList<ArrayList<Node>> levels;

        public BinaryTreeAnalyzer() {
            root = null;
            this.levels = new ArrayList<>();
            traverseTree();
        }

        private void traverseTree() {
            if (root == null) return;
            Queue<Node> queue = new LinkedList<>();
            queue.add(root);
            while (!queue.isEmpty()) {
                ArrayList<Node> currentLevel = new ArrayList<>();
                int size = queue.size();
                for (int i = 0; i < size; i++) {
                    Node currentNode = queue.poll();
                    currentLevel.add(currentNode);
                    if (currentNode.left != null) {
                        queue.add(currentNode.left);
                    }
                    if (currentNode.right != null) {
                        queue.add(currentNode.right);
                    }
                    levels.add(currentLevel);
                }
            }
        }

        public void inOrder() {
            traverseInOrder(root);
        }

        public void traverseInOrder(Node root) {
            if (root == null) {
                return;
            }
            traverseInOrder(root.left);
            System.out.println(root.toString());
            traverseInOrder(root.right);
        }

        public void postOrder() {
            traversePostOrder(root);
        }

        public void traversePostOrder(Node root) {
            if (root == null) {
                return;
            }
            traversePostOrder(root.right);
            System.out.println(root.toString());
            traversePostOrder(root.left);

        }

        public void preOrder() {
            traversePreOrder(root);
        }

        public void traversePreOrder(Node root) {
            if (root == null) {
                return;
            }
            System.out.println(root.toString());
            traversePreOrder(root.left);
            traversePreOrder(root.right);
        }

        private Node searchNode(Node root, String lastName) {
            if (root == null || root.lastName.equals(lastName)) {
                return root;
            }
            if (root.lastName.compareTo(lastName) < 0) {
                return searchNode(root.left, lastName);
            }
            return searchNode(root.right, lastName);
        }

        public void search(String lastName) {
            Node foundNode = searchNode(root, lastName);
            if (foundNode == null) {
                System.out.println("This user not exist...");
            } else {
                System.out.println("-" + foundNode.toString());
            }
        }

        private Node deletedNodeRec(Node root, String lastName) {
            if (root == null) {
                return null;
            }
            int comparison = root.lastName.compareToIgnoreCase(lastName);
            if (comparison > 0) {
                root.left = deletedNodeRec(root.left, lastName);
            } else if (comparison < 0) {
                root.right = deletedNodeRec(root.right, lastName);
            } else {
                // Eğer silinmek istenen düğüm root düğüm ise
                if (root == this.root) {
                    // Root düğümünün yerine, root'un sağ alt ağacındaki en küçük değerli düğüm yerleştirilir
                    this.root = minValueNode(root.right);
                    // Eski root'un sağ alt ağacından silinir
                    root.right = deleteMinValueNode(root.right);
                } else {
                    // Diğer durumlarda, silinmek istenen düğümün yerine, sağ alt ağacındaki en küçük değerli düğüm yerleştirilir
                    root.lastName = minValueNode(root.right).lastName;
                    // Eski düğüm silinir
                    root.right = deleteMinValueNode(root.right);
                }
            }
            return root;
        }

        private Node minValueNode(Node node) {
            Node current = node;
            // En küçük değerli düğüm sol alt ağacın en alt düğümüdür
            while (current.left != null) {
                current = current.left;
            }
            return current;
        }

        private Node deleteMinValueNode(Node node) {
            // Silinecek düğüm sol alt ağacın en alt düğümüdür
            // Bu düğümün yerine, silinecek düğümün sağ alt ağacındaki en küçük değerli düğüm geçerlidir
            if (node.left == null) {
                return node.right;
            }
            node.left = deleteMinValueNode(node.left);
            return node;
        }


        public void deletedNode(String lastName) {
            root = deletedNodeRec(root, lastName); // root düğümünü güncelle
            if (root == null) {
                System.out.println("This user does not exist...");
            } else {
                System.out.println("Contact deleted successfully!");
            }
        }


    }

    public static void main(String[] args) {
        BinaryTreeAnalyzer binaryTreeAnalyzer = new BinaryTreeAnalyzer();

        try {
            File contactsFile = new File("contacts.csv");
            if (!contactsFile.exists()) {
                contactsFile.createNewFile();
                String input = """
                        John,Doe,1234567890,john.doe@example.com
                        Jane,Smith,0987654321,jane.smith@example.com
                        Alice,Johnson,1122334455,alice.johnson@example.com
                        Bob,Williams,2233445566,bob.williams@example.com                        
                        """;
                FileWriter fileWriter1 = new FileWriter(contactsFile, false);
                try (BufferedWriter bufferedWriter1 = new BufferedWriter(fileWriter1)) {
                    bufferedWriter1.write(input);
                    bufferedWriter1.close();

                }
            }
            String line;
            BufferedReader objReader;
            objReader = new BufferedReader(new FileReader(contactsFile));
            ArrayList<Node> contactsList = new ArrayList<>();

            while ((line = objReader.readLine()) != null) {
                String[] contact = line.split(",");
                String firstName = contact[0];
                String lastName = contact[1];
                String phoneNumber = contact[2];
                String emailAddress = contact[3];
                contactsList.add(new Node(firstName, lastName, phoneNumber, emailAddress));

            }
            Collections.sort(contactsList);
            binaryTreeAnalyzer.root = contactsList.get(0);
            binaryTreeAnalyzer.root.left = contactsList.get(1);
            binaryTreeAnalyzer.root.right = contactsList.get(2);
            binaryTreeAnalyzer.root.left.left = contactsList.get(3);

            Scanner scanner = new Scanner(System.in);
            System.out.println("""
                    Welcome to the Contact Management System!
                    Reading contact details from file...
                                        
                    Contact details loaded successfully!
                    """);

            int choice;

            do {
                System.out.println("""
                        Please select an option:
                        1. Display contacts (Preorder)
                        2. Display contacts (Inorder)
                        3. Display contacts (Postorder)
                        4. Search for a contact
                        5. Delete a contact
                        6. Exit
                         """);


                System.out.print("Your choice:");
                choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        System.out.println("Displaying contacts (Preorder):");
                        binaryTreeAnalyzer.preOrder();
                        break;
                    case 2:
                        System.out.println("Displaying contacts (Inorder):");
                        binaryTreeAnalyzer.inOrder();
                        break;
                    case 3:
                        System.out.println("Displaying contacts (Postorder):");
                        binaryTreeAnalyzer.postOrder();
                        break;
                    case 4:
                        System.out.println("Enter the last name of the contact you want to search for:");
                        String searchInput = scanner.nextLine();
                        binaryTreeAnalyzer.search(searchInput);
                        break;
                    case 5:
                        System.out.println("Enter the last name of the contact you want to delete:");
                        String deleteInput = scanner.nextLine();
                        binaryTreeAnalyzer.deletedNode(deleteInput);
                        break;
                    case 6:
                        System.out.println("Exiting and saving contact details to file...");
                        break;
                    default:
                        System.out.println("Invalid choice! Please enter a number between 1 and 6.");
                }
            } while (choice != 6);


        } catch (IOException e) {
            throw new RuntimeException();
        }


    }
}