package com.zechariabender.boardcalculator;

import java.io.*;

class BoardProvider {

    Board load(String key) {
        Board board = null;
        try {
            FileInputStream fileIn = new FileInputStream(new File(key));
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            board = (Board) objectIn.readObject();
            objectIn.close();
            fileIn.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return board;
    }
    void save(Board board, String key) {

        try {
            FileOutputStream fileOut = new FileOutputStream(new File(key));
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(board);
            objectOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
