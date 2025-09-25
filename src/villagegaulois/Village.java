package villagegaulois;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	private Marche marche;

	public Village(String nom, int nbVillageoisMaximum, int nbEtal) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
		marche = new Marche(nbEtal);
	}
	
	private static class Marche {
		private Etal[] etals;
		
		public Marche(int nbEtal) {
			etals = new Etal[nbEtal];
			for (int i = 0; i < nbEtal; i++) {
				etals[i] = new Etal();
			}
		}
		
		private void  utiliserEtal(int indiceEtal, Gaulois vendeur,
				String produit, int nbProduit) {
			Etal install = this.etals[indiceEtal];
			install.occuperEtal(vendeur, produit, nbProduit);
		}
		
		private int trouverEtalLibre() {
			int find = -1;
			for (int i = 0; i < etals.length && find == -1; i++) {
				if(!this.etals[i].isEtalOccupe()) {
					find = i;
				}
			}
			return find;
		}
		
		private Etal[] trouverEtals(String produit) {
			int nbEtal = 0;
			
			for (int i = 0; i < etals.length; i++) {
				if(etals[i].contientProduit(produit)) {
					nbEtal++;
				}
			}
			Etal[] find = new Etal[nbEtal];
			int pos = 0;
			for (int i = 0; i < etals.length; i++) {
				if(etals[i].contientProduit(produit)) {
					find[pos] = etals[i];
					pos ++;
				}
			}
			return find;
		}
		
		private Etal trouverVendeur(Gaulois gaulois) {
			Etal find = null;
			for (int i = 0; i < etals.length && find == null; i++) {
				if(etals[i].getVendeur() == gaulois) {
					find = etals[i];
				}
			}
			return find;
		}
		
		private void afficherMarche() {
			int vide = 0;
			for (int i = 0; i < etals.length; i++) {
				if(etals[i].isEtalOccupe()) {
					System.out.println(etals[i].afficherEtal());
				}else {
					vide ++;
				}
			}
			System.out.println("Il reste " + vide + " " + "etals non utilises dans le marche");
		}
	}

	public String getNom() {
		return nom;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}

	public void ajouterHabitant(Gaulois gaulois) {
		if (nbVillageois < villageois.length) {
			villageois[nbVillageois] = gaulois;
			nbVillageois++;
		}
	}

	public Gaulois trouverHabitant(String nomGaulois) {
		if (nomGaulois.equals(chef.getNom())) {
			return chef;
		}
		for (int i = 0; i < nbVillageois; i++) {
			Gaulois gaulois = villageois[i];
			if (gaulois.getNom().equals(nomGaulois)) {
				return gaulois;
			}
		}
		return null;
	}

	public String afficherVillageois() {
		StringBuilder chaine = new StringBuilder();
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef "
					+ chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom()
					+ " vivent les légendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}
	public String installerVendeur(Gaulois vendeur, String produit,int nbProduit) {
		int libre = marche.trouverEtalLibre();
		StringBuilder chaine = new StringBuilder();
		chaine.append(vendeur.getNom() + " cherche un endroit pour vendre " + nbProduit + " " + produit + ".\n");
		if(libre != -1) {
			marche.utiliserEtal(libre, vendeur, produit, nbProduit);
			chaine.append("Le vendeur " + vendeur.getNom() + " vend des " + produit + " a l'etal n° " + libre + ".\n");
		}else {
			chaine.append("il n'y a plus de place.\n");
		}
		return chaine.toString();
	}
	
	public String rechercherVendeursProduit(String produit) {
		Etal[] vendeurs = marche.trouverEtals(produit);
		StringBuilder chaine = new StringBuilder();
		if(vendeurs.length == 1) {
			chaine.append("Seul le vendeur " + vendeurs[0].getVendeur().getNom() + " propose des " + produit +  " au marche.\n");
		}else if (vendeurs.length > 1) {
			chaine.append("Les vendeurs qui proposent des fleurs sont : \n");
			for (int i = 0; i < vendeurs.length; i++) {
				chaine.append("- " + vendeurs[i].getVendeur().getNom() + "\n");
			}
		}
		return chaine.toString();
	}
	
	public Etal rechercherEtal(Gaulois vendeur) {
		return marche.trouverVendeur(vendeur);
	}
	
	public String partirVendeur(Gaulois vendeur) {
		Etal depart = rechercherEtal(vendeur);
		return depart.libererEtal();
		
	}
	
	public String afficherMarche() {
		marche.afficherMarche();
		return null;
	}


}