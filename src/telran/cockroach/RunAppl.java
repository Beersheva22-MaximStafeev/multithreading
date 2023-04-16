package telran.cockroach;

import telran.view.StandardInputOutput;

public class RunAppl {
	public static void main(String[] args) {
		Controller cnt = new Controller(new CockroachImpl(2, 6));
		cnt.getMenu().perform(new StandardInputOutput());
	}
}
