package dataStructures;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MyStackTest {

    @Test
    void testPushAndPop() {
        MyStack stack = new MyStack(10);
        stack.push("A");
        stack.push("B");
        stack.push("C");

        assertEquals(3, stack.size());
        assertEquals("C", stack.pop());
        assertEquals("B", stack.pop());
        assertEquals("A", stack.pop());
        assertTrue(stack.empty());
    }

    @Test
    void testSize() {
        MyStack stack = new MyStack();
        assertEquals(0, stack.size());

        stack.push("X");
        stack.push("Y");
        assertEquals(2, stack.size());
    }

    @Test
    void testSplitStack() {
        MyStack stack = new MyStack();
        stack.push("1");
        stack.push("2");
        stack.push("3");
        stack.push("4");

        MyStack[] parts = stack.splitStack();
        assertEquals(2, parts[0].size());
        assertEquals(2, parts[1].size());

        // First half: bottom elements
        assertEquals("1", parts[0].stack[0]);
        assertEquals("2", parts[0].stack[1]);

        // Second half: top elements
        assertEquals("3", parts[1].stack[0]);
        assertEquals("4", parts[1].stack[1]);
    }

    @Test
    void testCombineStack() {
        MyStack stack1 = new MyStack();
        stack1.push("A");
        stack1.push("B");

        MyStack stack2 = new MyStack();
        stack2.push("C");
        stack2.push("D");

        MyStack combined = stack1.combineStack(stack2);
        assertEquals(4, combined.size());

        assertEquals("A", combined.stack[0]);
        assertEquals("B", combined.stack[1]);
        assertEquals("C", combined.stack[2]);
        assertEquals("D", combined.stack[3]);
    }

    @Test
    void testEmpty() {
        MyStack stack = new MyStack();
        assertTrue(stack.empty());
        stack.push("Z");
        assertFalse(stack.empty());
    }
}
