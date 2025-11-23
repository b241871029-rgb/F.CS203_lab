package dataStructures;

import java.io.*;
import java.util.*;

class Car {
	private String licensePlate;

	public Car(String licensePlate) {
		this.licensePlate = licensePlate;
	}

	public String getLicensePlate() {
		return licensePlate;
	}

	@Override
	public String toString() {
		return licensePlate;
	}
}

class ArrayStack implements Stack {
	private Object[] elements;
	private int size;
	private static final int CAPACITY = 10;

	public ArrayStack() {
		elements = new Object[CAPACITY];
		size = 0;
	}

	@Override
	public boolean empty() {
		return size == 0;
	}

	@Override
	public Object peek() {
		if (empty())
			return null;
		return elements[size - 1];
	}

	@Override
	public void push(Object theObject) {
		if (size >= CAPACITY)
			return;
		elements[size++] = theObject;
	}

	@Override
	public Object pop() {
		if (empty())
			return null;
		return elements[--size];
	}

	public int getSize() {
		return size;
	}

	public int getCapacity() {
		return CAPACITY;
	}

	public Object getAt(int index) {
		if (index < 0 || index >= size)
			return null;
		return elements[index];
	}
}

public class CarParking {
	private ArrayStack garage;
	private ArrayStack temp;

	public CarParking() {
		garage = new ArrayStack();
		temp = new ArrayStack();
	}

	public List<String> input(String filename) {
		List<String> operations = new ArrayList<>();
		try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
			String line;
			while ((line = reader.readLine()) != null) {
				line = line.trim();
				if (!line.isEmpty()) {
					operations.add(line);
				}
			}
		} catch (IOException e) {
			System.out.println("Error reading file: " + e.getMessage());
		}
		return operations;
	}

	public void process(List<String> operations) {
		for (String operation : operations) {
			String[] parts = operation.split("\\s+");
			if (parts.length >= 2) {
				String direction = parts[0];
				String licensePlate = parts[1];

				if (direction.equals("A")) {
					handleArrival(licensePlate);
				} else if (direction.equals("D")) {
					handleDeparture(licensePlate);
				}
			}
		}
	}

	private void handleArrival(String licensePlate) {
		if (garage.getSize() < garage.getCapacity()) {
			Car newCar = new Car(licensePlate);
			garage.push(newCar);
			output("Arrival " + licensePlate + " -> There is room.");
		} else {
			output("Arrival " + licensePlate + " -> Garage full, this car cannot enter.");
		}
	}

	private void handleDeparture(String licensePlate) {
		Car targetCar = findCar(licensePlate);

		if (targetCar == null) {
			output("Departure " + licensePlate + " -> This car not in the garage.");
			return;
		}

		int carsMoved = 0;

		while (!garage.empty()) {
			Car currentCar = (Car) garage.pop();
			carsMoved++;

			if (currentCar.getLicensePlate().equals(licensePlate)) {
				output("Departure " + licensePlate + " -> " + (carsMoved - 1) + (carsMoved - 1 == 1 ? " car" : " cars")
						+ " moved out.");

				while (!temp.empty()) {
					garage.push(temp.pop());
				}
				return;
			}

			temp.push(currentCar);
		}
	}

	private Car findCar(String licensePlate) {
		for (int i = 0; i < garage.getSize(); i++) {
			Car car = (Car) garage.getAt(i);
			if (car != null && car.getLicensePlate().equals(licensePlate)) {
				return car;
			}
		}
		return null;
	}

	private void output(String message) {
		System.out.println(message);
	}

	public static void main(String[] args) {
		CarParking parkingSystem = new CarParking();

		List<String> operations = parkingSystem.input("cars.txt");

		parkingSystem.process(operations);
	}
}
