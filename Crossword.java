import java.util.Scanner;

import javax.sql.RowSet;

import java.io.*;

public class Crossword {

    public static void main(String[] args) throws IOException {
        DictInterface Dict = new MyDictionary();
        int boardSize;
        int row = 0;

        // See what prof says about using DIct8.txt
        File file = new File(args[0]);
        Scanner scan = new Scanner(file);

        // ADd words to dictionary,
        while (scan.hasNext()) {
            Dict.add(scan.nextLine());

        }

        // cloeses the scanner so the file can be used again
        scan.close();

        // Read crossword
        File crossFile = new File(args[1]);
        scan = new Scanner(crossFile);
        boardSize = scan.nextInt();
        char[][] board = new char[boardSize][boardSize];

        // Clear
        scan.nextLine();
        while (scan.hasNext()) {
            String boardRead = scan.next();

            for (int collumn = 0; collumn < boardSize; collumn++) {
                board[row][collumn] = boardRead.charAt(collumn);

            }
            row++;

        }

        System.out.println(solveBoard(board, 0, 0, Dict));
        printBoard(board);

      

    }

    public static void printBoard(char[][] board){
        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board.length; j++){
                System.out.print(board[i][j]);
            }
            System.out.println("");
        }
    }


    public static boolean solveBoard(char[][] board, int row, int collumn, DictInterface dict) {
        if (collumn >= board.length) {
            return solveBoard(board, row + 1, 0, dict);
        }

        // compoletemly solved board
        if (row >= board.length) {
            return true;
        }

        if (board[row][collumn] != '+') {
            return solveBoard(board, row, collumn + 1, dict);
        }

        //printBoard(board);

        for (char letter = 'a'; letter <= 'z'; letter++) {
            board[row][collumn] = letter;
            StringBuilder rowString = new StringBuilder();
            StringBuilder colString = new StringBuilder();

            int lowestRow = row;
            int rightMostCol = collumn;

            // checking for letter(s) before row
            for (int i = collumn; i >= 0 && board[row][i] != '-'; i--) {
                rowString.insert(0, board[row][i]);
            }

            // checking for letter(s) after row
            for (int i = collumn + 1; i < board.length && board[row][i] != '-' && board[row][i] != '+'; i++) {
                rowString.append(board[row][i]);
                rightMostCol = i;
            }

            // checking for letter(s) before col
            for (int i = row; i >= 0 && board[i][collumn] != '-'; i--) {
                colString.insert(0, board[i][collumn]);
            }

            // checking for letter(s) after col
            for (int i = row + 1; i < board.length && board[i][collumn] != '-' && board[i][collumn] != '+'; i++) {
                colString.append(board[i][collumn]);
                lowestRow = i;
            }

            // Search prefix, next session string builder type beat
            int statusRow = dict.searchPrefix(rowString);
            int statusCol = dict.searchPrefix(colString);

            // System.out.println(rowString);
            // System.out.println(colString);

            //checking to see if either is invalid
            if (statusRow == 0 || statusCol == 0) {
                board[row][collumn] = '+';
                continue;
            }

            // is this the last character we filled in
            if (rightMostCol == board.length - 1 || board[row][rightMostCol + 1] == '-') {
                // if we only have a prefix
                if (statusRow == 1) {
                    board[row][collumn] = '+';
                    continue;
                    //
                }

            } else {
                // where looking at a prefix now
                if (statusRow == 2) {
                    board[row][collumn] = '+';
                    continue;
                }
            }

            // is this the last character we filled in
            if (lowestRow == board.length - 1 || board[lowestRow + 1][collumn] == '-') {
                // if we only have a prefix
                if (statusCol == 1) {
                    board[row][collumn] = '+';
                    continue;
                    //
                }

            } else {
                // where looking at a prefix now
                if (statusCol == 2) {
                    board[row][collumn] = '+';
                    continue;
                }
            }

            if(solveBoard(board, row, collumn + 1, dict)){
                return true;
            }else{
                board[row][collumn] = '+';
            }

        }
        return false;
    }

    // public boolean findNextWord(char[][] board){
    // boolean foundNextWord = false;

    // //to find a possible row
    // for(int i = 0; i < board.length; i++){
    // int plusFound = 0;
    // int spaceFound = 0;

    // //to find spaces of size 2 or greater
    // for(int j = 0; j < board.length; j++){
    // //determine if we found a minus
    // if(board[i][j] == '-'){
    // //check if its a possible space
    // if(plusFound > 0 && spaceFound >= 2){
    // foundNextWord = true;
    // break;
    // }
    // }

    // }
    // }
    // }

}