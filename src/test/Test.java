package test;

import java.io.IOException;

import run.Start;

public class Test {

	public static void main(String[] args) {
		try {
			new Start().handleRequest(null, null, null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
