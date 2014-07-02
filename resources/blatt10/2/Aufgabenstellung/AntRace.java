package antRace;


public class AntRace implements AntFields {

	public static void main(String[] args) {

		AntField field = new AntField(FIELD4);

		Ant ant = new Ant(field, 2, 4, 1);

		new Thread(ant).start();

		System.out.println(field.toString());
	}
}
