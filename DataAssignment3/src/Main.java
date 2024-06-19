//Enes Geldi
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

        public void delete(String lastName) {
            root = deletedNodeRec(root, lastName);
            Node deletedNode = deletedNodeRec(root, lastName);
            if (deletedNode != null) {
                System.out.println("Contact deleted successfully!");

            } else {
                System.out.println("Contact not found!");
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
                Node temp = usageNode(root);
                if (temp == root.left) {
                    return root.left;
                } else if (temp == root.right) {
                    return root.right;
                } else {
                    if (root.left == null) {
                        return root.right;
                    } else if (root.right == null) {
                        return root.left;
                    } else {
                        Node current = minValueNode(root.right);
                        root.firstName = current.firstName;
                        root.lastName = current.lastName;
                        root.phoneNumber = current.phoneNumber;
                        root.emailAddress = current.emailAddress;
                        root.right = deletedNodeRec(root.right, current.lastName);
                    }
                }
            }

            return root;
        }

        private Node minValueNode(Node node) {
            Node current = node;
            while (current.left != null) {
                current = current.left;
            }
            return current;
        }

        private Node usageNode(Node node) {
            if (node.left == null) {
                Node temp = node.right;
                node = null;
                return temp;
            } else if (node.right == null) {
                Node temp = node.left;
                node = null;
                return temp;
            } else
                return null;
        }
        public void traverseInOrderAndWriteToFile(Node root, BufferedWriter bufferedWriter) throws IOException {
            if (root == null) {
                return;
            }
            traverseInOrderAndWriteToFile(root.left, bufferedWriter);
            bufferedWriter.write(root.toString() + "\n");
            traverseInOrderAndWriteToFile(root.right, bufferedWriter);
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
                        binaryTreeAnalyzer.delete(deleteInput);
                        break;
                    case 6:

                        System.out.println("Exiting and saving contact details to file...");
                        try {
                            File newContactsFile = new File("contacts_new.csv");
                            newContactsFile.createNewFile();

                            FileWriter fileWriter = new FileWriter(newContactsFile, true);
                            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                            binaryTreeAnalyzer.traverseInOrderAndWriteToFile(binaryTreeAnalyzer.root, bufferedWriter);
                            bufferedWriter.close();

                            File oldContactsFile = new File("contacts.csv");
                            oldContactsFile.delete();
                            newContactsFile.renameTo(oldContactsFile);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                }
            } while (choice != 6);

        } catch (IOException e) {
            throw new RuntimeException();
        }


    }
}