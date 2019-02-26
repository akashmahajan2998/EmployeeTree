//package intro;
import java.util.*;
import java.lang.Math; 
import java.io.*;

// class - Node of Binary Search Tree
class Node_BST {
	String name;
	Node_BST parent;
	Node_BST left;
	Node_BST right;	
	Node_Emp_Tree pointer;
}

// class - Binary Search Tree
class BST {

	Node_BST newNode(String name) {
		Node_BST n = new Node_BST();
		n.name = name;
		n.left = null;
		n.right = null;
		return n;
	}
	
	void Delete(Node_BST n) {
		if (n.left == null && n.right == null) {
			if (n.parent.right == n) {
				n.parent.right = null;
			}
			else {
				n.parent.left = null;
			}			
		}
		else if (n.right == null) {
			if (n.parent.right == n) {
				n.left.parent = n.parent;
				n.parent.right = n.left;
			}
			else {
				n.left.parent = n.parent;
				n.parent.left = n.left;
			}			
		}
		else if (n.left == null) {
			if (n.parent.right == n) {
				n.right.parent = n.parent;
				n.parent.right = n.right;
			}
			else {
				n.right.parent = n.parent;
				n.parent.left = n.right;
			}
		}
		else {
			Node_BST m = n.right;
			while (m.left != null) {		
				m = m.left;
			}	
			n.name = m.name;
			n.pointer = m.pointer;	
			Delete(m);
		}
	}
	
	Node_BST Insert(Node_BST n, String name) {
		if (n == null) {
			return newNode(name);
			}
		int res = n.name.compareTo(name);
		Node_BST m;
		if(res<0) {
			if (n.right == null) {
				n.right = newNode(name);
				n.right.parent = n;
				return n.right;
			}			
			m = Insert(n.right, name);
		}
		else {
			if (n.left == null) {
				n.left = newNode(name);
				n.left.parent = n;
				return n.left;
			}
			m = Insert(n.left, name);
		}
		return m;
	}
	
	public Node_BST Search(Node_BST n, String name) {
		int res = n.name.compareTo(name);
		if(res==0 ) {
			return n;
			}
		else if(res<0) {
			return Search(n.right, name);
			}
		else {
			return Search(n.left, name);
		}
	}
	
//  USED IN CHECKING 
//	
//	public void inorder(Node_BST n) {
//		if (n == null) 
//            return; 
//  
//		 inorder(n.left); 
//        System.out.println(n.name + " ");
//        inorder(n.right); 
//	}
	
	// Called in PrintEmployees()
	public void PreTraverse(Node_BST n,int i) {
		if (n == null) 
            return; 
		if (n.pointer.level==i) {
			System.out.println(n.name + " ");
		}
        PreTraverse(n.left,i); 
        PreTraverse(n.right,i); 
	}
}	
	
// class of Employee Tree Node
class Node_Emp_Tree {
	 int level = 0;
	 Node_Emp_Tree ParentNode;
	 String EmployeeName;
	 Node_Emp_Tree child;
	 Node_Emp_Tree next;
	
	 // Every Node has maximum 1 child, all other children are pointed by a next pointer
	void SetChild(Node_Emp_Tree A) {
		this.child=A;
		return;
	}
	
	void SetNext(Node_Emp_Tree A) {
		this.next=A;
		return;
	}
	
	String GetName() {
		return this.EmployeeName;
	}
	
	Node_Emp_Tree GetChild(){
		return this.child;
	}
	
	Node_Emp_Tree GetNext(){
		return this.next;
	}
}

//class - Employee Tree 
public class EmployeeTree {
	// root2 is root node of Emp Tree
	Node_Emp_Tree root2 = new Node_Emp_Tree();
	int max = 1;
	// root1 is root node of BST
	Node_BST root1 = new Node_BST();
	// The BST on which operations will be done
	BST temp1 = new BST();
	
	
	public void AddCEO(String s) {
		root1.name = s;
		root2.EmployeeName = s;
		root1.pointer = root2;
		root2.level = 1;
	}
	
	public void AddEmployee(String S, String Sp){
		Node_BST temp = temp1.Search(root1, Sp);
		Node_Emp_Tree t = temp.pointer;
		Node_BST p = temp1.Insert(root1, S);
		// A new Node of Emp Tree is made 
		Node_Emp_Tree q = new Node_Emp_Tree();
		q.EmployeeName = S;
		p.pointer = q;

		if (t.GetChild() == null) {
			q.ParentNode = t;
			t.SetChild(q);
		}
		else {
			Node_Emp_Tree z ;
			z = t.GetChild();
			q.ParentNode = t;
			q.next = z;
			t.SetChild(q);
		}
		q.level = q.ParentNode.level + 1;
		max = Math.max(max, q.level);
		
	}
	
	public void DeleteEmployee(String S, String Sp) {
		Node_BST temp = temp1.Search(root1, S);
		Node_Emp_Tree t1 = temp.pointer;
		
		Node_BST temp2 = temp1.Search(root1, Sp);
		Node_Emp_Tree t2 = temp2.pointer;
	
		// if t1 is child of its parent node
		if (t1.ParentNode.child == t1) {
			if (t1.GetNext() != null) {
				t1.ParentNode.child = t1.GetNext();
			}
			else {
				t1.ParentNode.child = null;
			}
		}
		else {
			Node_Emp_Tree w = t1.ParentNode.child;
			while (w.GetNext() != t1) {
				w = w.next;
			}
			if (t1.GetNext() == null) {
				w.next = null;
			}
			else {
				w.next = t1.GetNext();
	            t1.next = null;
			}
		}
		if (t2.child == null) {
			if (t1.child!=null) { // if t1 has child then do
				t1.child.ParentNode = t2;
				t2.child = t1.child;
				Node_Emp_Tree z = t2.GetChild();
				while(z.GetNext() != null) {
					z = z.GetNext();
					z.ParentNode = t2;
			}
			}
		}
		else { // if t2 has a child then add t1's children at next nodes
			Node_Emp_Tree z = t2.GetChild();
			while(z.GetNext() != null) {
				z = z.GetNext();
			}
			if (t1.child != null) {
				Node_Emp_Tree x = t1.child; 
				z.next = x;
				t1.child.ParentNode = t2;
				while(x.next != null) {
					x =  x.next;
					x.ParentNode = t2;
				}
			}			
		}
		// Delete from Binary Tree
		temp1.Delete(temp);
	}
	
	public void LowestCommonBoss( String S, String Sp) {
		Node_BST temp = temp1.Search(root1, S);
		Node_Emp_Tree t1 = temp.pointer;
		Node_BST temp2 = temp1.Search(root1, Sp);
		Node_Emp_Tree t2 = temp2.pointer;
		while(t1.ParentNode != null) {
			t2 = temp2.pointer;
			while(t2.ParentNode != null) {
				if (t1.ParentNode.GetName() == t2.ParentNode.GetName()) {
					System.out.println(t1.ParentNode.GetName());
					return;
				}
				else {
					t2 = t2.ParentNode;
				}
			}
			t1 = t1.ParentNode;
		}
	}
	
	public void PrintEmployees() {
		System.out.println(root1.name);
		int i = 2;
		while (i<= max) {
			temp1.PreTraverse(root1, i);
			i=i+1;
		}
	}	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//System.out.println("main starts");
		EmployeeTree Tree = new EmployeeTree(); //"/Users/akash/Desktop/4Tree.txt"
		try (BufferedReader b = new BufferedReader(new FileReader(args[0]))){
			String line1 = b.readLine();
			int n = Integer.parseInt(line1);
			int i = 1;
			String[] boss = b.readLine().split(" ");	 
			Tree.AddCEO(boss[1]);
			Tree.AddEmployee(boss[0],boss[1]);
//			Tree.PrintEmployees();
			while (i<= n-2) {
				boss = b.readLine().split(" ");
				Tree.AddEmployee(boss[0],boss[1]);
				i=i+1;
			}
			String line2 = b.readLine();
			n = Integer.parseInt(line2);
			//System.out.println(n);
			i = 1;
			while (i<=n) {
				boss = b.readLine().split(" ");
				// System.out.println("abcd"+i);
//				Tree.PrintEmployees();
				if (Integer.parseInt(boss[0]) == 0) {
					Tree.AddEmployee(boss[1], boss[2]);
					//System.out.println(1111);
				}
				else if (Integer.parseInt(boss[0]) == 1) {
					Tree.DeleteEmployee(boss[1], boss[2]);
					//System.out.println("deleted");
				}
				else if (Integer.parseInt(boss[0]) == 3) {
					Tree.PrintEmployees();
				}
			    else {
			    	//System.out.println("lllppp");	
					Tree.LowestCommonBoss(boss[1], boss[2]);
				}			
				i=i+1;
			}
		}
		catch (IOException e) {
			return;
		}
		catch (NullPointerException f) {
			System.out.println("Node not found");
			return;
		}
		catch (NoSuchElementException g) {
			return;
		}
	}
}
