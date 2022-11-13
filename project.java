// JSON to CSV & CSV to JSON file convertor

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Scanner;

// j2c
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;

public class project {
    // csv --> json
    static void c2j() throws IOException {
        Scanner sc2 = new Scanner(System.in);
        System.out.print("Enter source file path : ");
        String source_file = sc2.nextLine();
        File file = new File(source_file);

        System.out.print("Enter Destination file path : ");
        String dest_file = sc2.nextLine();
        File f = new File(dest_file);
        BufferedWriter bw = new BufferedWriter(new FileWriter(f));
        Scanner text;
        try {
            text = new Scanner(file);

            int first_line_count = 0;
            int head_counter = 0;
            String[] head = new String[100];
            bw.write("[");
            while (text.hasNextLine()) {
                String line = text.nextLine();
                String[] temp1 = new String[100];
                temp1[0] = line;
                String[] temp2 = temp1[0].split(",");
                for (int i = 0; i < temp2.length; i++) {
                    if (first_line_count == 0) {
                        for (int j = 0; j < temp2.length; j++) {
                            head[j] = temp2[j];
                        }
                        first_line_count++;
                    }
                }
                if (head_counter != 0) {
                    bw.write("{\n");
                    for (int i = 0; i < temp2.length; i++)
                    {
                        bw.write("\t" + head[i] + " : " + temp2[i]);
                        if(i!=(temp2.length-1))
                        bw.write(",\n");

                    }
                    if(text.hasNextLine())
                        bw.write("\n},\n");
                    else    bw.write("\n}\n");
                }
                head_counter++;
            }
            bw.write("]\n");
            System.out.println("\n The conversion was successfull");
        } catch (FileNotFoundException e) {
            System.out.println("Conversion Failure");
        }
        // text.close();
        sc2.close();
        bw.close();
    }

    // json --> csv
    static void j2c() throws IOException 
    {
        Scanner sc2 = new Scanner(System.in);
        
        System.out.print("Enter source file path : ");
        String source_file = sc2.nextLine();
        System.out.print("Enter Destination file path : ");
        String dest_file = sc2.nextLine();
        File f = new File(dest_file);
        BufferedWriter bw = new BufferedWriter(new FileWriter(f));

        File file = new File(source_file);
        Scanner text;
        int number_of_rows = 0;
        try {
            text = new Scanner(file);
            Path filePath = Paths.get(source_file);
            String content = Files.readString(filePath, StandardCharsets.US_ASCII);

            for(int i = 0; i< content.length();i++)
            {
                if (content.charAt(i) == '{') {
                    number_of_rows++;
                }
            }
            content = content.replaceAll("[^a-zA-Z0-9\":,_]", " ");
            content = content.replaceAll("\\s+"," ");
            content = content.replaceAll(":",",");

            String[] arr = content.split(",");
            int array_length = arr.length; 
            // Header
            int j = 0;
            int no_of_column = ((array_length)/number_of_rows)/2;
            String[] header = new String[no_of_column];
            for(int i = 0;i<no_of_column*2 ; i++)
            {
                if((i%2)==0){
                    header[j] = arr[i];
                    j++;
                }
            }
            for(int i = 0 ; i < header.length ; i++)
            {
                bw.write(header[i]);
                if(i!=(header.length-1))
                {
                    bw.write(",");
                }
            }
            bw.write("\n");
            int k =0;
            for(int i = 0; i<array_length;i++)
            {
                if((i%2)!=0)
                { 
                    bw.write(arr[i]);    
                    k++;
                    if(k!=no_of_column)
                    {
                        bw.write(",");
                    }
                }
                if(k==no_of_column)
                {  
                    bw.write("\n");
                    k=0;
                }
            }
            System.out.println("\nConversion successfull!");
        } catch (FileNotFoundException e) {
            System.out.println("\nFile conversion unsuccessfull");
        }
        bw.close();
    }

    public static void main(String[] args) throws IOException {
        System.out.println("\n\n\nThe File Convertor : ");
        System.out.println("Operations:- ");
        System.out.println("1. CSV to JSON ");
        System.out.println("2. JSON to CSV ");
        System.out.print("Chose your option : ");
        Scanner sc = new Scanner(System.in);
        int choise = sc.nextInt();

        switch (choise) {
            case 1:
                c2j();
                break;
            case 2: j2c();
                    break;
            default:
                System.out.println("Invalid input");
                break;
        }
        sc.close();
    }
}
