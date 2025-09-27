package histoire;

import personnages.Gaulois;
import villagegaulois.Etal;

public class ScenarioCasdegrade {
	
	public static void main(String[] args) {
		Etal etal = new Etal();
		Gaulois n = new Gaulois("eric", 0);
		etal.acheterProduit(1, n);
		System.out.println("Fin du test");
		
	}
}
