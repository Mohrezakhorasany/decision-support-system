package hu.ak_akademia.app;

import java.util.Scanner;

import hu.ak_akademia.menu.Menu;

public class App {

	public static void main(String[] args) {
		new Menu(new Scanner(System.in)).run();
	}

}
