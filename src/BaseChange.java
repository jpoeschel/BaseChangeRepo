import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Stack;
/**
 * BaseChange class, extends JFrame
 * @author Johnathan Poeschel
 * @version 1.0, 25 Nov 2019
 */
public class BaseChange extends JFrame { //extends JFrame to be able to call JFrame methods from driver class.
    /**
     * private String holds input in the number box
     */
    private String numberInput = "";
    /**
     * private String holds input in the currentBase box
     */
    private String currentBaseInput = "";
    /**
     * private String holds input in the desiredBase box
     */
    private String desiredBaseInput = "";
    /**
     * private final HashMap holds Strings of ascii values and Integers matching that number
     */
    private final HashMap<String, Integer> basesMap = new HashMap<String, Integer>();
    /**
     * private final hashMap holds Strings and Integers from 0-9, A-V as the strings and 2-32 as their corresponding int value
     */
    private final HashMap<String, Integer> asciiMap = new HashMap<String, Integer>();

    /**
     * BaseChange constructor creates JFrame with ActionListener listening for button to be pressed
     */
    public BaseChange(){
        super("Base Change Calculator"); //calls constructor of super class JFrame, creates JFrame with desired text
        //fill base map with strings and their int values.
        int num = 50;
        for(int i = 2; i < 10; i++){
            String b = Integer.toString(i);
            basesMap.put(b,num);
            num++;
        }
        int num1 = 65;
        for(int i = 10; i<33; i++){
            String b = Integer.toString(i);
            basesMap.put(b,num1);
            num1++;
        }
        //fill ascii map with strings 0-9,A-V
        for(int i = 0; i<10; i++){
            String b = Integer.toString(i);
            asciiMap.put(b,i);
        }
        for(int i = 10; i<33; i++){
            asciiMap.put((char)(55+i)+"",i);
        }
        JPanel panel = new JPanel();
        //button
        JButton convertBase = new JButton("     Convert Base     ");
        //inputs
        JTextField number = new JTextField();
        number.setPreferredSize(new Dimension(200,50));
        JLabel numberLabel = new JLabel("Number       ");

        JTextField currentBase = new JTextField();
        currentBase.setPreferredSize(new Dimension(200,50));
        JLabel currentBaseLabel = new JLabel("Current Base");

        JTextField desiredBase = new JTextField();
        desiredBase.setPreferredSize(new Dimension(200,50));
        JLabel desiredBaseLabel = new JLabel("Desired Base");

        //output
        JTextField output = new JTextField();
        output.setPreferredSize(new Dimension(200,50));
        JLabel outputLabel = new JLabel("Output         ");

        //button action listener, when button is clicked validate input from each textfield,
        // validate that the number and current base make sense,
        // convert to desired bade, display in output field.
        convertBase.addActionListener(new ActionListener() {
            /**
             * actionPerformed function used for convertBase button, when clicked methods get called to convert the number into a base and display it
             * @param e button being clicked event
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                numberInput = number.getText();
                currentBaseInput = currentBase.getText();
                desiredBaseInput = desiredBase.getText();
                //InputValidation
                //number input must be 0-9 or a-w
                numberInput = numberInput.toUpperCase();
                if(!numberInput.matches("^[a-vA-V0-9]+$")){
                    number.setText("Invalid Number");
                }
                if(numberInput.matches("[WXYZ]+")){
                    number.setText("Invalid Number");
                }
                String empty1 = "";
                String empty2 = "INVALID NUMBER";
                String empty3 = "PLEASE ENTER NUMBER";
                if(numberInput.compareTo(empty1)==0 || numberInput.compareTo(empty2)==0 || numberInput.compareTo(empty3)==0){
                    number.setText("Please Enter Number");
                }
                String empty = "";
                String help = "Invalid Base";
                String help1 = "Please Enter Base";
                if(!basesMap.containsKey(currentBaseInput)){//currentBase must be 2-32
                    if(currentBaseInput.compareTo(empty)== 0 || currentBaseInput.compareTo(help)==0 || currentBaseInput.compareTo(help1)==0){
                        currentBase.setText("Please Enter Base");
                    }
                    else{
                        currentBase.setText("Invalid Base");
                    }
                }
                //desiredBase must be 2-32
                if(!basesMap.containsKey(desiredBaseInput)){
                    if(desiredBaseInput.compareTo(empty)== 0 || desiredBaseInput.compareTo(help)==0 || desiredBaseInput.compareTo(help1)==0){
                        desiredBase.setText("Please Enter Base");
                    }
                    else{
                        desiredBase.setText("Invalid Base");
                    }
                }
                try {
                    if (checkBase(numberInput, currentBaseInput)) {
                        output.setText(convertBase(numberInput, currentBaseInput, desiredBaseInput));
                    } else {
                        number.setText("Invalid Number");
                        currentBase.setText("Invalid Base");
                    }
                }catch (Exception a){}
            }
        });

        panel.add(numberLabel);
        panel.add(number);
        panel.add(currentBaseLabel);
        panel.add(currentBase);
        panel.add(desiredBaseLabel);
        panel.add(desiredBase);
        panel.add(convertBase);
        panel.add(outputLabel);
        panel.add(output);
        add(panel);
    }

    /**
     * privtae checkBase function, makes sure that the number and the current base the user entered are acceptable
     * @param numberInput the number input
     * @param currentBaseInput the current base input
     * @return true or false if the base and number work or not
     */
    //function to check if the number entered and the current base entered are valid with each other
    private boolean checkBase(String numberInput, String currentBaseInput){
        for(int i = 0; i < numberInput.length(); i++)
        {   if (numberInput.charAt(i) >= basesMap.get(currentBaseInput)){
                return false;
            }
        }
        return true;
    }

    /**
     * private convertBase method, uses two helper functions to get the number converted to the desired base.
     * @param numberInput number input
     * @param currentBaseInput current base input
     * @param desiredBaseInput desired base input
     * @return returns converted number in desired base
     */
    private String convertBase(String numberInput, String currentBaseInput, String desiredBaseInput){
        if(currentBaseInput.compareTo(desiredBaseInput)==0){ //if current base is the desired base
            return numberInput; //return the original number
        }
        if(basesMap.get(currentBaseInput) != 65){ //if current base is not in decimal
            long numberConvertedDecimal = convertToDecimal(numberInput,currentBaseInput); //convert number to decimal
            if(basesMap.get(desiredBaseInput)==65){ //if desired base is decimal
                return Long.toString(numberConvertedDecimal); //return number converted into decimal
            }
            return decimalToBase(convertToDecimal(numberInput,currentBaseInput),desiredBaseInput); // convert decimal number to desired base
        }
        else{
            return decimalToBase(convertToDecimal(numberInput,currentBaseInput),desiredBaseInput); // convert decimal number to desired base
        }
    }

    /**
     * private convertToDecimal function called by convertBase function to first convert the number input to decimal if needed
     * @param numberInput the number input
     * @param currentBaseInput the current base it is in
     * @return long decimal number
     */
    private long convertToDecimal(String numberInput, String currentBaseInput){
        long sum = 0;
        int index = 0;
        int help = basesMap.get(currentBaseInput);
        String help1 = (char) help+"";
        int help2 = asciiMap.get(help1);
        int length = numberInput.length()-1;
        for(int i = length; i > -1; i--){
            sum += asciiMap.get(numberInput.charAt(index)+"") * Math.pow(help2,i);
            index++;
        }
        return sum;
    }

    /**
     * private decimalToBase function called by convertBase function to convert from decimal to desired base
     * @param numberConvertedDecimal the number converted into decimal
     * @param desiredBaseInput the desired base it should be converted to
     * @return String of the number converted to any base
     */
    private String decimalToBase(long numberConvertedDecimal, String desiredBaseInput){
        Stack<String> valueStack = new Stack<>();
        long num = numberConvertedDecimal;
        int baseAsAscii = basesMap.get(desiredBaseInput);
        int base = asciiMap.get((char)baseAsAscii+"");
        if(num<base){
            long help = num%base;
            for (String key : asciiMap.keySet()){
                if(asciiMap.get(key)==help){
                    return key;
                }
            }
        }
        while(num>=base){
            long help = num%base;
            for (String key : asciiMap.keySet()){
                if(asciiMap.get(key)==help){
                    valueStack.push(key);
                }
            }
            num = num/base;
        }
        long help = num%base;
        for (String key : asciiMap.keySet()){
            if(asciiMap.get(key)==help){
                valueStack.push(key);
            }
        }
        StringBuilder b = new StringBuilder();
        while(!valueStack.isEmpty()){
            b.append(valueStack.pop());
        }
        return b.toString();
    }
}
