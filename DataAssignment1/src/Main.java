import java.io.*;

class Node {
    //Enes Geldi
    public Node next;
    public Node prev;
    public String countryName;
    public long population;
    public String capitalCity;
    public String largestCity;
    public String officalLanguage;
    public String currency;


    public Node(String countryName, long population, String capitalCity, String largestCity, String officalLanguage, String currency) {
        this.next = null;
        this.prev = null;
        this.countryName = countryName;
        this.population = population;
        this.capitalCity = capitalCity;
        this.largestCity = largestCity;
        this.officalLanguage = officalLanguage;
        this.currency = currency;

    }

    @Override
    public String toString() {
        return countryName + " " + population + " " + capitalCity + " " + largestCity + " " + officalLanguage + " " + currency;
    }
}

class DoublyLinkedList {
    //Enes Geldi
    public Node head;
    public Node tail;

    public DoublyLinkedList() {
        this.head = null;
        this.tail = null;
    }

    void print_all() {
        Node current = head;
        if (current == null) {
            System.out.println("CountryList is Empty...");
            return;
        }
        while (current != null) {
            System.out.println(current);
            current = current.next;
        }
        System.out.println();
    }

    void addingInput(String countryName, long population, String capitalCity, String largestCity, String officalLanguage, String currency) {
        Node newNode = new Node(countryName, population, capitalCity, largestCity, officalLanguage, currency);
        Node current = tail;
        if (head == null) {
            head = newNode;
            tail = newNode;
            head.prev = null;
            tail.next = null;


        } else {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;

        }



    }

    void addingCountryBegin(String countryName, long population, String capitalCity, String largestCity, String officalLanguage, String currency) {
        Node newNode = new Node(countryName, population, capitalCity, largestCity, officalLanguage, currency);
        if (head == null) {
            head = newNode;
            tail = newNode;
            head.prev = null;
            tail.next = null;


        } else {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;

        }
        System.out.println("A new country has been added.");

    }

    public void insertLast(String countryName, long population, String capitalCity, String largestCity, String officalLanguage, String currency) {
        Node newNode = new Node(countryName, population, capitalCity, largestCity, officalLanguage, currency);
        if (tail == null) {
            newNode.next = head;
            head = newNode;
            tail = newNode;
        }else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
            tail.next = null;
        }
    }

    public void delete(String countryName) {
        Node current = head;

        if (head == null) {
            System.out.println("The List is empty...");
        }

        while (current != null) {
            if (current.countryName.equalsIgnoreCase(countryName)) {
                if (current == head) {
                    head = head.next;
                    head.prev = null;
                    break;
                } else if (current != head && current != tail) {
                    current.prev.next = current.next;
                    current.next.prev = current.prev;
                    break;
                } else {
                    tail = current.prev;
                    tail.next = null;
                    break;
                }
            }
            current = current.next;
        }
        System.out.println("Removes the country " + current.countryName + " from the list");
    }

    void findCountry(String country_Name) {
        Node current = head;
        while (!current.countryName.toLowerCase().equals(country_Name.toLowerCase())) {
            current = current.next;

        }
        if (current == null) {
            System.out.println("This country is not exist.");
        } else {
            System.out.println(current);
        }
    }


    void queryPopulation(long population) {
        Node current = head;
        while (current != null) {
            if (current.population > population) {
                System.out.println(current);
            }
            current = current.next;
        }

    }

    void queryCapitalCity(String capitalCity) {
        Node current = head;
        while (current != null) {
            if (current.capitalCity.compareTo(capitalCity) < 0) {
                System.out.println(current);
            }
            current = current.next;

        }
    }

    void queryCountry(String countryName) {
        Node current = head;
        while (current != null) {
            if (current.countryName.compareTo(countryName) > 0) {
                System.out.println(current);
            }
            current = current.next;

        }
    }

    void queryCurrency(String currency) {
        Node current = head;
        while (current != null) {
            if (current.currency.compareTo(currency) < 0) {
                System.out.println(current);
            }
            current = current.next;

        }
    }

    void queryLargestCity(String largestCity) {
        Node current = head;
        while (current != null) {
            if (current.largestCity.equalsIgnoreCase(largestCity)) {
                break;
            }
            current = current.next;
        }
        System.out.println(current);

    }


}


public class Main {
    //Enes Geldi
    public static void main(String[] args) {

        DoublyLinkedList countryList = new DoublyLinkedList();
        try {
            File inputFile = new File("input.txt");
            if (!inputFile.exists()) {
                inputFile.createNewFile();
                String input = """
                        TURKIYE 85.000.000 Ankara Istanbul Turkish TRY
                        GERMANY 84.000.000 Berlin Berlin German EUR
                        USA 335.000.000 Washington NewYork English USD
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

            while ((satir = objReader.readLine()) != null) {
                System.out.println(satir);
                String[] arrOfStr = satir.split(" ");
                String countryName = arrOfStr[0];
                String population = arrOfStr[1];
                String capitalCity = arrOfStr[2];
                String largestCity = arrOfStr[3];
                String languageOffical = arrOfStr[4];
                String currency = arrOfStr[5];
                population = population.replace(".", "");
                long pop = Long.parseLong(population);

                countryList.insertLast(countryName, pop, capitalCity, largestCity, languageOffical, currency);


            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            File queryFile = new File("query.txt");
            if (!queryFile.exists()) {
                queryFile.createNewFile();
                String query = """
                        Query population > 50.000.000
                        Query country > HUNGARY
                        Query capital_city < Canakkale
                        Query largest_city = Istanbul
                        Query country = USA
                        Add BULGARIA 6.500.000 Sofia Sofia Bulgarian LEV
                        Delete USA
                        Query print_all
                        Query currency < EUR
                        Query print_all""";
                FileWriter fileWriter = new FileWriter(queryFile, false);
                try (BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
                    bufferedWriter.write(query);
                    bufferedWriter.close();
                }
            }
            String satir;
            BufferedReader objReader;
            objReader = new BufferedReader(new FileReader(queryFile));

            while ((satir = objReader.readLine()) != null) {
                

                if (satir.startsWith("Query")) {
                    if (satir.startsWith("Query population")) {

                        String newSatir = satir.substring(satir.indexOf(">") + 2);
                        newSatir = newSatir.replace(".", "");

                        countryList.queryPopulation(Long.parseLong(newSatir));
                        //countryList.queryPopulation(Long.parseLong(satir.replaceAll(".","").substring(satir.indexOf(">")+2)));
                    } else if (satir.startsWith("Query country")) {
                        if (satir.startsWith("Query country >")) {
                            String newSatir = satir.substring(satir.indexOf(">") + 2);
                            countryList.queryCountry(newSatir);
                        } else if (satir.startsWith("Query country =")) {
                            String newSatir = satir.substring(satir.indexOf("=") + 2);
                            countryList.findCountry(newSatir);
                        }
                    } else if (satir.startsWith("Query capital_city")) {
                        String newSatir = satir.substring(satir.indexOf("<") + 2);
                        countryList.queryCapitalCity(newSatir);
                    } else if (satir.startsWith("Query largest_city")) {
                        String newSatir = satir.substring(satir.indexOf("=") + 2);
                        countryList.queryLargestCity(newSatir);

                    } else if (satir.startsWith("Query print_all")) {
                        countryList.print_all();
                    } else if (satir.startsWith("Query currency")) {
                        String newSatir = satir.substring(satir.indexOf("<") + 2);
                        countryList.queryCurrency(newSatir);
                    }

                } else if (satir.startsWith("Add")) {
                    String[] arrOfStr = satir.split(" ");
                    String countryName = arrOfStr[1];
                    String population = arrOfStr[2];
                    String capitalCity = arrOfStr[3];
                    String largestCity = arrOfStr[4];
                    String languageOffical = arrOfStr[5];
                    String currency = arrOfStr[6];
                    population = population.replace(".", "");
                    long pop = Long.parseLong(population);

                    countryList.addingCountryBegin(countryName, pop, capitalCity, largestCity, languageOffical, currency);


                }else if (satir.startsWith("Delete")) {
                    String newSatir = satir.substring(satir.indexOf(" ") + 1);
                    countryList.delete(newSatir);

                }

            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }



    }

}