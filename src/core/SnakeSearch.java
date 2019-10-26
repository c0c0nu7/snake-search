package core;

import java.awt.Point;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class SnakeSearch {
	private static String searchStringInternal;
	
	
	public static int search(String filename, String searchString) {
		searchStringInternal = searchString;
		
		char[][] gridArray;
        int wordCount = 0;

        gridArray = getCharArrayFromFile(filename);

        for (int row = 0; row < gridArray.length; row++) {
            for (int col = 0; col < gridArray[row].length; col++) {
                ArrayList<Point> visitedPoints = new ArrayList<>();
                wordCount += snakeSearch(gridArray, 0, row, col, 0, visitedPoints);
            }
        }
        
        return wordCount;
	}
	
	private static int snakeSearch(char[][] array,
            int searchStringIndex,
            int row,
            int col,
            int wordCount,
            ArrayList<Point> visitedPoints) {
        if (isOutOfBound(row, col, array)) {
            return wordCount;
        }

        if (isAlreadyVisited(visitedPoints, row, col)) {
            return wordCount;
        }

        if (array[row][col] == searchStringInternal.charAt(searchStringIndex)) {
            Point currentPoint = new Point(row, col);

            if (searchStringIndex == searchStringInternal.length() - 1) {
                return wordCount += 1;
            }

            visitedPoints.add(currentPoint);

            searchStringIndex++;
            int startX = row;
            int startY = col;

            wordCount = snakeSearch(array, searchStringIndex, startX - 1, startY - 1, wordCount, visitedPoints);
            wordCount = snakeSearch(array, searchStringIndex, startX - 1, startY, wordCount, visitedPoints);
            wordCount = snakeSearch(array, searchStringIndex, startX - 1, startY + 1, wordCount, visitedPoints);
            wordCount = snakeSearch(array, searchStringIndex, startX, startY - 1, wordCount, visitedPoints);
            wordCount = snakeSearch(array, searchStringIndex, startX, startY + 1, wordCount, visitedPoints);
            wordCount = snakeSearch(array, searchStringIndex, startX + 1, startY - 1, wordCount, visitedPoints);
            wordCount = snakeSearch(array, searchStringIndex, startX + 1, startY, wordCount, visitedPoints);
            wordCount = snakeSearch(array, searchStringIndex, startX + 1, startY + 1, wordCount, visitedPoints);

            visitedPoints.remove(currentPoint);
        }

        return wordCount;
    }

    private static boolean isOutOfBound(int row, int col, char[][] array) {
        return row < 0 || col < 0 || row > array.length - 1 || col > array[row].length - 1;
    }

    private static boolean isAlreadyVisited(ArrayList<Point> visitedPoints, int row, int col) {
        return visitedPoints.stream().anyMatch(p -> p.x == row && p.y == col);
    }

    private static char[][] getCharArrayFromFile(String filename) {
        char[][] charArray = new char[0][0];

        try (Stream<String> stream = Files.lines(Paths.get(filename))) {
            List<String> lines = new ArrayList<>();
            stream.forEach(lines::add);

            charArray = new char[lines.size()][lines.get(0).toCharArray().length];

            for (int i = 0; i < lines.size(); i++) {
                charArray[i] = lines.get(i).toCharArray();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return charArray;
    }
	
	
}