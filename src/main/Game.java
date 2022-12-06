package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Game {
    private static int M;
    private static int N;
    private String LOST = "LOST";
    private String DONE = "done";

    public void startApp() {
        List<String> output = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.println("Game setup - enter input:");
            String instructionString = scanner.nextLine();
            setupGrid(instructionString);

            System.out.println("Game setup - enter rovers:");
            while(scanner.hasNext() && !instructionString.equals(DONE)) {
                instructionString = scanner.nextLine();
                String[] instruction = Arrays.stream(instructionString.split("[\\s(),]")).filter(x -> !x.equals("")).toArray(String[]::new);
                if(instruction.length == 4 ){
                    moveRovers(instruction, output);
                } else if (instructionString.equals(DONE)){
                    break;
                } else {
                    System.out.println("Please enter a grid size and at least one rover instruction");
                }
            }
            printSummary(output);
        } catch(Exception ex) {
            System.out.println("Error occurred, main.java.Game Over");
        }
        scanner.close();
    }

    private void setupGrid(String gridInput){
        String[] gridSize = gridInput.split(" ");

        try {
            if (gridSize.length == 2) {
                M = Integer.parseInt(gridSize[0]);
                N = Integer.parseInt(gridSize[1]);
            } else {
                throw new IllegalArgumentException("Invalid grid size specification");
            }
        } catch(NumberFormatException ex){
            System.out.println("Please enter valid integers for grid size");
        }
    }

    private void moveRovers(String[] instructionString, List<String> output){
        Rover rover = new Rover(Integer.parseInt(instructionString[0]), Integer.parseInt(instructionString[1]), Orientation.valueOf(instructionString[2]));

        calculatePosition(instructionString[3], rover);
        output.add(printOutput(rover));
    }

    private void calculatePosition(String moves, Rover r){
        int i = 0;
        while (i < moves.length() && !r.getStatus().equals(LOST)){
            moveRover(moves.charAt(i), r);
            i++;
        }
    }

    private void moveRover(char move, Rover r){
        if (move == 'F') {
            moveForward(r);
        } else {
            r.setOrientation(rotate(r.getOrientation(), move));
        }
    }

    private void moveForward(Rover r){
        if (r.getOrientation() == Orientation.E && checkBounds(M, r.getWE() + 1, r)) {
            r.setWE(r.getWE() + 1);
        } else if(r.getOrientation() == Orientation.W && checkBounds(M,r.getWE() - 1, r)){
            r.setWE(r.getWE() - 1);
        } else if(r.getOrientation() == Orientation.S && checkBounds(N,r.getNS() - 1, r)){
            r.setNS(r.getNS() - 1);
        } else if(r.getOrientation() == Orientation.N && checkBounds(N,r.getNS() + 1, r)){
            r.setNS(r.getNS() + 1);
        }
    }

    private boolean checkBounds(int max, int move, Rover r){
        if (0 < move && move <= max){
            return true;
        } else {
            r.status = LOST;
            return false;
        }
    }


    private Orientation rotate(Orientation current, char move){
        if(current == Orientation.E && move == 'L'){
            return Orientation.N;
        } else if(current == Orientation.E && move == 'R'){
            return Orientation.S;
        } else if(current == Orientation.S && move == 'L'){
            return Orientation.E;
        } else if(current == Orientation.S && move == 'R'){
            return Orientation.W;
        } else if(current == Orientation.W && move == 'L'){
            return Orientation.S;
        } else if(current == Orientation.W && move == 'R'){
            return Orientation.N;
        } else if(current == Orientation.N && move == 'L'){
            return Orientation.W;
        } else if(current == Orientation.N && move == 'R'){
            return Orientation.E;
        } else {
            throw new IllegalArgumentException(String.format("Did not recognise move, %c", move));
        }
    }

    private void printSummary(List<String> output){
        for(String s : output){
            System.out.println(s);
        }
    }

    private String printOutput(Rover r){
        return String.format("(%x, %x, %s) %s", r.getWE(), r.getNS(), r.getOrientation(), r.getStatus());
    }
}