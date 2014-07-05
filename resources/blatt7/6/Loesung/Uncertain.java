public class Uncertain {
	static String a = "A";
	private String b = "B";

	/*
	 * 3P Was fuer eine Art von Klasse ist das hier?
	 */
	static class A {

		static String c = "C";
		final String d = "D";

		void hi() {
			System.out.println("top-level: " + a + c + d);
			/*
			 * Warum ist das hier nicht moeglich?
			 */
			/*
			 * Antwort: b ist weder statisch noch final
			 */
			// System.err.println(b);
		}
	}

	/*
	 * 3P Was fuer eine Art von Klasse ist das hier?
	 */
	class B {

		final static String c = "C";

		void hi() {
			/*
			 * a und b explizit aufrufen
			 */
			System.out.print("member:  " + Uncertain.this.a + Uncertain.this.b);
			System.out.println(c);

		}
	}

	void hi() {
		final String c = "C";
		String d = "D";
		/*
		 * 3P Was fuer eine Art von Klasse ist das hier?
		 */
		class C {
			void hi() {
				System.out.println("lokal: " + a + b + c);
				/*
				 * Warum ist das hier nicht moeglich?
				 */
				/*
				 * Antwort: d ist nicht final.
				 */
				// System.err.println(d);
			}
		}
		/*
		 * 3P Was fuer eine Art von Klasse ist das hier?
		 */
		Object o = new B() {

			/*
			 * 2P Gibt es Konstruktoren?
			 */

			void hi() {
				System.out.println("anonym: " + a + b + c);
				/*
				 * 2P Warum ist das hier nicht moeglich?
				 */
				// System.err.println(d);

			}
		};

		/*
		 * fuehre hi() in der Klasse A aus
		 */
		new A().hi();
		/*
		 * 2P uehre hi() in der Klasse C aus
		 */

		/*
		 * fuehre hi() in der anonymen Klassse aus
		 */
		((B) o).hi();
		/*
		 * fuehre h() in der Klasse B aus
		 */
		new B().hi();
	}

	public static void main(String args[]) {
		System.out.println("Erster Methodenaufruf");
		/*
		 * fuehre hi() in der Klasse A aus
		 */
		new Uncertain.A().hi();

		System.out.println("Zweiter Methodenaufruf");
		/*
		 * 2P fuehre hi() in der Klasse B aus
		 */

		System.out.println("Dritter Methodenaufruf");
		/*
		 * fueher Top-Level-Methode hi() aus
		 */
		new Uncertain().hi();
	}
}
