package dataStructures;

import java.util.Scanner;

public class MyStack extends ArrayStack {

	public MyStack() {
		super();
	}

	public MyStack(int capacity) {
		super(capacity);
	}

	public int size() {
		return top + 1;
	}

	public void inputStack() {
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		System.out.print("Хэдэн элемент оруулах вэ?: ");
		int n = sc.nextInt();
		sc.nextLine();
		for (int i = 0; i < n; i++) {
			System.out.print((i + 1) + "-р элемент: ");
			push(sc.nextLine());
		}
	}

	public void printStack() {
		if (empty()) {
			System.out.println("Стек хоосон байна.");
			return;
		}
		System.out.print("Стек (top → bottom): ");
		for (int i = top; i >= 0; i--) {
			System.out.print(stack[i] + " ");
		}
		System.out.println();
	}
	
	public MyStack[] splitStack() {
		int mid = size() / 2;
		MyStack first = new MyStack(mid);
		MyStack second = new MyStack(size() - mid);
		for (int i = 0; i < mid; i++)
			first.push(stack[i]);
		for (int i = mid; i <= top; i++)
			second.push(stack[i]);

		return new MyStack[] { first, second };
	}

	public MyStack combineStack(MyStack other) {
		MyStack result = new MyStack(size() + other.size());
		for (int i = 0; i <= top; i++)
			result.push(stack[i]);
		for (int i = 0; i <= other.top; i++)
			result.push(other.stack[i]);
		return result;
	}

	public static void main(String[] args) {
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		MyStack stack1 = new MyStack();

		while (true) {
			System.out.println("\n=== MyStack Menu ===");
			System.out.println("1. Input stack");
			System.out.println("2. Print stack");
			System.out.println("3. Show size");
			System.out.println("4. Split stack");
			System.out.println("5. Combine with new stack");
			System.out.println("6. Exit");
			System.out.print("Choose: ");
			int choice = sc.nextInt();
			sc.nextLine();

			switch (choice) {
			case 1 -> stack1.inputStack();
			case 2 -> stack1.printStack();
			case 3 -> System.out.println("Stack size = " + stack1.size());
			case 4 -> {
				MyStack[] parts = stack1.splitStack();
				System.out.print("First half: ");
				parts[0].printStack();
				System.out.print("Second half: ");
				parts[1].printStack();
			}
			case 5 -> {
				System.out.println("Enter elements for second stack:");
				MyStack stack2 = new MyStack();
				stack2.inputStack();
				MyStack combined = stack1.combineStack(stack2);
				System.out.print("Combined stack: ");
				combined.printStack();
			}
			case 6 -> {
				System.out.println("Exiting...");
				return;
			}
			default -> System.out.println("Invalid choice, try again.");
			}
		}
	}
}
