package com.frame.example.structure;

public class MyLinkedList {
	
	Node head = null ; // 链表的头的引用
	
	public int length(){
		int length = 1 ;
		Node temp = head ;
		while(temp.next != null){
			length ++ ;
			temp = temp.next ;
		}
		return length ;
	}
	
	public void addNode(int data){
		Node newNode = new Node(data) ;
		if(head == null){
			head = newNode ;
			return ;
		}
		Node temp = head ;
		while(temp.next!=null){
			temp = temp.next ;
		}
		//添加node节点
		temp.next = newNode ; 
	}
	
	public Boolean deleteNode(int index){
		if(index < 1 || index > length()){
			return false ;
		}
		//删除链表第一个元素 第一个元素为0
		if(index == 1){
			head = head.next ;
			return true ;
		}
		int i= 1;
		Node preNode = head ; //前一个节点
		Node curNode = preNode.next ; //当前节点
		while(curNode != null){
			if(index == i){
				preNode.next = curNode.next ;
				return true ;
			}
			preNode = curNode ;
			curNode = curNode.next;
			i++ ;
		}
		return true ;
	}
	
	//反转链表
	public void reversedInteratively(){
		Node pReversedHead = head ; //定义反转后的头节点
		Node pNode = head ;  //当前操作节点
		Node pPrev = null ; // 前一个节点
		while(pNode != null){
			Node pNext = pNode.next ;
			if(pNext == null){ //没有下一个节点了，说明这个节点是正常链表的最后一个节点，即为反向节点的头
				pReversedHead = pNode ;
			}
			pNode.next = pPrev ;  //如果还有节点，当前节点指向前一个节点，实现反转
			pPrev = pNode ;
			pNode = pNext ;
		}
		this.head = pReversedHead ; //设置头节点
	}
	
	public Node findElem(int k){
		if(k < 1 || k > this.length()){
			return null ;
		}
		Node p1 = head ;
		Node p2 = head ;
		for (int i = 0; i < k ; i++)  //前移k-1步
			p1 = p1.next ;
		while(p1 != null){
			p1 = p1.next ;
			p2 = p2.next ;
		}
		return p2 ;
	}
	//递归反向输出单链表，每次先输出后一个节点的值，再输出节点本身，递归本身也是一个栈结构
	public void printListReversely(Node node){
		if(node != null){
			printListReversely(node.next);
			System.out.print(node.data +" ");
		}
	}
	
	class Node {
		Node next = null ;
		int data ;
		public Node(int data){
			this.data = data ;
		}
		@Override
		public String toString() {
			return "Node [next=" + next + ", data=" + data + "]";
		}
		
	}
	
	
	public static void main(String[] args) {
		MyLinkedList linked = new MyLinkedList() ;
		linked.addNode(1);
		linked.addNode(4);
		linked.addNode(9);
		linked.addNode(5);
		linked.addNode(6);
		linked.addNode(7);
		linked.addNode(8);
		linked.addNode(3);
		Node pNext = linked.head ;
		linked.printListReversely(pNext) ;
		System.out.println();
		while(pNext != null){
			System.out.print(pNext.data+" "); 
			pNext=pNext.next ;
		}
		linked.reversedInteratively();
		System.out.println();
		pNext = linked.head ;
		while(pNext != null){
			System.out.print(pNext.data+" "); 
			pNext=pNext.next ;
		}
//		System.out.println("linked="+linked.findElem(8).data);
	}

	@Override
	public String toString() {
		return "MyLinkedList [head=" + head + "]";
	}
	
}
