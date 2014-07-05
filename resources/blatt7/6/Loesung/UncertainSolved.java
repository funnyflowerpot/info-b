public class UncertainSolved {
	static String a = "A";
	private String b = "B";

	/*
	 * Was fuer eine Art von Klasse ist das hier?
	 */
	/*
	 * Antwort: statische innere Klasse, nested top-level class
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
	 * Was fuer eine Art von Klasse ist das hier?
	 */
	/*
	 * Antwort: Elementklasse, member class
	 */
	class B {

		final static String c = "C";

		void hi() {
			/*
			 * a und b explizit aufrufen
			 */
			System.out.print("member:  " + UncertainSolved.this.a
					+ UncertainSolved.this.b);
			System.out.println(c);

		}
	}

	void hi() {
		final String c = "C";
		String d = "D";
		/*
		 * Was fuer eine Art von Klasse ist das hier?
		 */
		/*
		 * Antwort: lokale Klasse, local class
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
		 * Was fuer eine Art von Klasse ist das hier?
		 */
		/*
		 * Antwort: Anonyme innere Klasse bzw. anonymus inner class. So aehnlich
		 * wie wenn eine separate Klasse Anonym extends B existiert.
		 */
		Object o = new B() {

			/*
			 * Gibt es Konstruktoren?
			 */
			{
				/*
				 * Antwort: Nein, nur Instanz-Initialisierer wie diesen hier
				 */
			}

			void hi() {
				System.out.println("anonym: " + a + b + c);
				/*
				 * Warum ist das hier nicht moeglich?
				 */
				/*
				 * Antwort: d ist nicht final.
				 */
				// System.err.println(d);

			}
		};

		/*
		 * fuehre hi() in der Klasse A aus
		 */
		new A().hi();
		/*
		 * uehre hi() in der Klasse C aus
		 */
		new C().hi();
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
		new UncertainSolved.A().hi();

		System.out.println("Zweiter Methodenaufruf");
		/*
		 * fuehre hi() in der Klasse B aus
		 */
		new UncertainSolved().new B().hi();

		System.out.println("Dritter Methodenaufruf");
		/*
		 * fuehre Top-Level-Methode hi() aus
		 */
		new UncertainSolved().hi();
	}
}
