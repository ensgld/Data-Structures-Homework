import java.io.*;


class Link {
    public char data;
    public Link next;

    public Link(char data) {
        this.data = data;
    }

    public void dispylayLink() {
        System.out.println("Data:" + data);
    }

}

class MyStack {
    public Link head;


    public MyStack() {
        head = null;
    }

    public boolean isEmpty() {
        if (head == null) {
            return true;
        }
        return false;
    }

    public void push(char data) {
        Link newNode = new Link(data);
        newNode.next = head;
        head = newNode;
    }

    public char pop() {
        if (isEmpty()) {
            System.out.println("Stack boş");
            return '\0';
        } else {
            Link gecerli = head;
            head = head.next;
            return gecerli.data;
        }
    }

    public char top() {
        if (isEmpty()) {
            System.out.println("Stack boş");
        }
        return head.data;
    }

    public int operatorCompare(char character) {
        if (character == '+' || character == '-') {
            return 1;
        } else if (character == '*' || character == '/') {
            return 2;
        } else {
            return -1;
        }
    }
}

public class Main {

    public static void main(String[] args) {
        MyStack operandStack = new MyStack();
        MyStack operatorStack = new MyStack();
        try {
            File inputFile = new File("infix.txt");
            if (!inputFile.exists()) {
                inputFile.createNewFile();
                String input = """
                        4+9*10
                        1-3/6+100-25
                        4 * - 2
                        4 - 1 / 10 * 2
                        100 2 *
                        """;
                FileWriter fileWriter1 = new FileWriter(inputFile, false);
                try (BufferedWriter bufferedWriter1 = new BufferedWriter(fileWriter1)) {
                    bufferedWriter1.write(input);
                    bufferedWriter1.close();
                }
            }
            String satir;
            BufferedReader objReader;
            objReader = new BufferedReader(new FileReader(inputFile));
            String postfix="";
            while ((satir = objReader.readLine()) != null) {
                for (int i = 0; i < satir.length(); i++) {
                    if (Character.isDigit(satir.charAt(i))) {
                        do {
                            postfix += satir.charAt(i);
                            i++;
                        } while (i < satir.length() && Character.isDigit(satir.charAt(i)));
                        i--; // Rakamdan sonraki karakteri ele almak için i'yi azalt
                        postfix += " ";
                    } else if (satir.charAt(i) == '/' || satir.charAt(i) == '*' || satir.charAt(i) == '-' || satir.charAt(i) == '+') {
                        while (!operatorStack.isEmpty() && operatorStack.operatorCompare(satir.charAt(i)) <= operatorStack.operatorCompare(operatorStack.top())) {
                            postfix += operatorStack.pop();
                            postfix += " ";
                        }
                        operatorStack.push(satir.charAt(i));
                    }
                }
                while (!operatorStack.isEmpty()) {
                    postfix += operatorStack.pop();
                    postfix += " ";
                }
            }



        } catch (IOException e) {
            throw new RuntimeException(e);

        }
    }
}