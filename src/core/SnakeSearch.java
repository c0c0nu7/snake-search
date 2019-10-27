package core;

import java.awt.Point;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SnakeSearch {
	private static String searchStringInternal;

	public static int search(String filename, String searchString) {
		if (searchString.isEmpty()) {
			throw new IllegalArgumentException("Empty search string is not allowed");
		}

		searchStringInternal = searchString;

		char[][] gridArray;
		int wordCount = 0;

		gridArray = getCharArrayFromFile(filename);

		for (int row = 0; row < gridArray.length; row++) {
			for (int col = 0; col < gridArray[row].length; col++) {
				ArrayList<Point> visitedPoints = new ArrayList<>();
				wordCount += snakeSearch(gridArray, 0, new Point(row, col), 0, visitedPoints);
			}
		}

		return wordCount;
	}

	private static int snakeSearch(char[][] array, int searchStringIndex, Point currentPoint, int wordCount,
			ArrayList<Point> visitedPoints) {
		if (isOutOfBound(currentPoint, array)) {
			return wordCount;
		}

		if (isAlreadyVisited(visitedPoints, currentPoint)) {
			return wordCount;
		}

		if (isMatch(array, searchStringIndex, currentPoint)) {
			if (searchStringIndex == searchStringInternal.length() - 1) {
				return wordCount += 1;
			}

			visitedPoints.add(currentPoint);

			searchStringIndex++;
			Point startPoint = currentPoint;

			wordCount = snakeSearch(array, searchStringIndex, new Point(startPoint.x - 1, startPoint.y - 1), wordCount,
					visitedPoints);
			wordCount = snakeSearch(array, searchStringIndex, new Point(startPoint.x - 1, startPoint.y), wordCount,
					visitedPoints);
			wordCount = snakeSearch(array, searchStringIndex, new Point(startPoint.x - 1, startPoint.y + 1), wordCount,
					visitedPoints);
			wordCount = snakeSearch(array, searchStringIndex, new Point(startPoint.x, startPoint.y - 1), wordCount,
					visitedPoints);
			wordCount = snakeSearch(array, searchStringIndex, new Point(startPoint.x, startPoint.y + 1), wordCount,
					visitedPoints);
			wordCount = snakeSearch(array, searchStringIndex, new Point(startPoint.x + 1, startPoint.y - 1), wordCount,
					visitedPoints);
			wordCount = snakeSearch(array, searchStringIndex, new Point(startPoint.x + 1, startPoint.y), wordCount,
					visitedPoints);
			wordCount = snakeSearch(array, searchStringIndex, new Point(startPoint.x + 1, startPoint.y + 1), wordCount,
					visitedPoints);

			visitedPoints.remove(currentPoint);
		}

		return wordCount;
	}

	private static boolean isMatch(char[][] array, int searchStringIndex, Point currentPoint) {
		return array[currentPoint.x][currentPoint.y] == searchStringInternal.charAt(searchStringIndex);
	}

	private static boolean isOutOfBound(Point p, char[][] array) {
		return p.x < 0 || p.y < 0 || p.x > array.length - 1 || p.y > array[p.x].length - 1;
	}

	private static boolean isAlreadyVisited(ArrayList<Point> visitedPoints, Point currentPoint) {
		return visitedPoints.stream().anyMatch(p -> p.x == currentPoint.x && p.y == currentPoint.y);
	}

	private static Stream<String> getLinesFromFile(String filename) {
		Stream<String> streamOfLines = null;

		try {
			streamOfLines = Files.lines(Paths.get(filename));
		} catch (IOException e) {
			e.printStackTrace();
		}

		return streamOfLines;
	}

	private static char[][] getCharArrayFromFile(String filename) {
		List<String> lines = getLinesFromFile(filename).collect(Collectors.toList());
		return convertLinesToCharArray(lines);
	}
	
	private static char[][] convertLinesToCharArray(List<String> lines) {
		char[][] charArray = new char[lines.size()][lines.get(0).toCharArray().length];

		for (int i = 0; i < lines.size(); i++) {
			charArray[i] = lines.get(i).toCharArray();
		}

		return charArray;
	}

}
