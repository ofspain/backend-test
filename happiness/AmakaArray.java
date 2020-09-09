/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amakaarray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author femi
 */
public class AmakaArray {

    private int[] raw_a;
    private int[] raw_b;

    private static final int ARRAY_LENGHT = 2;

    public AmakaArray() {
        raw_a = initialize_array(raw_a);
        raw_b = initialize_array(raw_b);
        System.out.println("B VALUE "+raw_b[0] +" : "+raw_b[1]);
    }

    private int[] initialize_array(int[] array) {
        array = new int[ARRAY_LENGHT];
        for (int i = 0; i < array.length; i++) {
            //array[i] = 3;
            array[i] = ThreadLocalRandom.current().nextInt(1, 30000);
            //System.out.println(array[i]);
        }
        return array;
    }

    private int encodeInteger(int x, int n) {
        //int sc = n;
        //System.out.println("raw = "+ n);
        n = n << (1 << (1 << (1 << 1)));
        //System.out.println("final = "+ n);

        x = x | n;

        return x;
    }

    private int[] encodeArray() {
        int[] encoded_array = new int[ARRAY_LENGHT];
        for (int i = 0; i < encoded_array.length; i++) {
            encoded_array[i] = encodeInteger(raw_a[i], raw_b[i]);
           // System.out.println(encoded_array[i]);
        }

        return encoded_array;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        AmakaArray amak = new AmakaArray();
        int[] encoded_array = amak.encodeArray();
       // amak.decodeArray(encoded_array);
        System.out.println(amak.decodeArray(encoded_array));
    }

    private Map<Integer, Set<Integer>> decodeArray(int[] encoded_array) {
        Map<Integer, Set<Integer>> possible_solutions_map = new HashMap<>();
        for (int codedA_codedB : encoded_array) {
            String bit_codedA_codedB = Integer.toBinaryString(codedA_codedB);
            //System.out.println(bit_codedA_codedB);
            for (int possible_raw_x = 1; possible_raw_x <= 29999; possible_raw_x++) {
                Set<Integer> possible_solution_for_x = new HashSet<>();
                String bit_possible_raw_x = Integer.toBinaryString(possible_raw_x);
                List<String> possible_codded_n_bit = find_all_possible_coded_n_bit(bit_possible_raw_x, bit_codedA_codedB);
                //System.out.println(possible_codded_n_bit);
              //  System.exit(0);
                for (String p_c_n_b : possible_codded_n_bit) {
                    int raw_n = decode_n_bit(p_c_n_b);
                    // System.out.println(p_c_n_b);
                    if (raw_n >= 1 && raw_n < 29999) {
                        possible_solution_for_x.add(raw_n);
                    }
                }
                if (!possible_solution_for_x.isEmpty()) {
                    possible_solutions_map.put(possible_raw_x, possible_solution_for_x);
                }
            }
        }
        return possible_solutions_map;
    }

    private int decode_n_bit(String p_c_n_b) {
        int raw_coded = Integer.parseInt(p_c_n_b, 2);

        int divisor = (int) Math.pow(2, 16);
        //System.out.println(raw_coded/divisor);
        return raw_coded / divisor;

    }

    private List<String> find_all_possible_coded_n_bit(String bit_possible_raw_x, String bit_codedA_codedB) {

        String[] paddedString = paddedString(bit_possible_raw_x, bit_codedA_codedB);
        Map<Integer, List<String>> possible_bit_at_i = new HashMap<>();
        String padded_or_not_codedA_codedB = paddedString[0];
        String padded_or_not_raw_x = paddedString[1];

        //System.out.println(bit_codedA_codedB);
     //   System.out.println(padded_or_not_codedA_codedB);
       // System.out.println(padded_or_not_raw_x);
        //System.out.println("<<><><><><<<<<><<");
        int leng_charaters = padded_or_not_raw_x.length();
        List<String> all_solution_for_this_raw_x = new ArrayList<>();

        for (int i = 0; i < padded_or_not_codedA_codedB.length(); i++) {
            List<String> possible_i = new ArrayList<>();

           // System.out.println("OPERAND "+padded_or_not_codedA_codedB.charAt(i) +"|"+padded_or_not_raw_x.charAt(i));
            //System.exit(0);

            //Then both operands are always zero
            if (padded_or_not_codedA_codedB.charAt(i) == '0') {
                possible_i.add("0");
            } else {

                if (padded_or_not_raw_x.charAt(i) == '1') {//first operand =1

                    //The other operand can be 0 or 1
                    possible_i.add("0");
                    possible_i.add("1");
                } else { //the first operand = 0
                    //The second operand is definitely 1
                    possible_i.add("1");
                }
            }
            possible_bit_at_i.put(i, possible_i);
        }

        // System.out.println(possible_bit_at_i);
        int numb_cell_with_more_than_one_value = 0;
        for (Integer index : possible_bit_at_i.keySet()) {
            if (possible_bit_at_i.get(index).size() > 1) {
                numb_cell_with_more_than_one_value++;
            }
        }
       // System.out.println(numb_cell_with_more_than_one_value +" MMMM");
        List<char[]> solution_bank = new ArrayList<>();
        for (int i = 0; i < numb_cell_with_more_than_one_value; i++) {


            char[] new_char_solution = new char[leng_charaters];
            String soluted = "";
            for (Integer key : possible_bit_at_i.keySet()) {
                List<String> solutions = possible_bit_at_i.get(key);
                //System.out.println(key + ">>>" + solutions);
                new_char_solution[key] = solutions.get(0).charAt(0);
                soluted += solutions.get(0);
            }

            all_solution_for_this_raw_x.add(soluted);

            char[] new_char_solution2 = new char[leng_charaters];
            String soluted2 = "";

            for (Integer key : possible_bit_at_i.keySet()) {
                List<String> solutions = possible_bit_at_i.get(key);
                //System.out.println(key + ">>>" + solutions);
                if(solutions.size()>1){
                    new_char_solution2[key] = solutions.get(1).charAt(0);
                    soluted2 += solutions.get(1);
                   // System.out.println(key +" >>> "+solutions.get(1).charAt(0)+" >>> "+soluted);
                }else{
                    new_char_solution2[key] = 0;
                    soluted2 += "0";
                }

            }
            all_solution_for_this_raw_x.add(soluted2);

        }
        return all_solution_for_this_raw_x;

    }

    private String[] paddedString(String first, String second) {
        String result[] = new String[2];
        if (first.length() == second.length()) {
            result[0] = first;
            result[1] = second;
            return result;
        }
        if (second.length() > first.length()) {
            result = pad_arrayString(second, first);
        } else {
            result = pad_arrayString(first, second);
        }
        return result;

    }

    private String[] pad_arrayString(String longer, String shorter) {

        int normal_short_lenght = shorter.length();
        for (int pad = 0; pad < (longer.length() - normal_short_lenght); pad++) {
            shorter = "0" + shorter;
        }
        String[] result = new String[2];
        result[0] = longer;
        result[1] = shorter;

        return result;

    }

}
