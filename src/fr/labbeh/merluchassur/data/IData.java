/**
 * 
 */
package fr.labbeh.merluchassur.data;

import java.util.Collection;
import java.util.Set;

import fr.labbeh.merluchassur.Assure;

/**
 * @author labbeh
 * Cette interface devra être implémentée par toute classe servant à lire ou écrire les données
 * sur disque. En effet, il est aussi bien possible de sauvegarder les données des joueurs
 * dans un fichier texte personnalisé, un fichier de sérialisation, un fichier xml
 * ou encore par l'accès à une base SQL si le serveur en est équipé.
 * L'interface permettra de rendre transparent le mode de gestion des données sans que
 * le reste du plugin soit affecté
 */
public interface IData {
	/**
	 * Méthode lancée dans la classe principale dans le onEnable()
	 * pour préparer le terrain...
	 * */
	public void init();
	
	/**
	 * Permet d'ajouter un nouvel assuré
	 * @param assure instance de l'assuré à ajouter
	 * */
	public void addAssure(Assure assure);
	
	/**
	 * Permet de récupéré un assuré à partir de son nom
	 * @param playerName nom du joueur assuré
	 * */
	public Assure getAssure(String playerName);
	
	/**
	 * Retourne une Collection de l'ensemble des Assures
	 * @return Collection de l'ensemble des assurés
	 * */
	public Collection<Assure> getAssures();
	
	/**
	 * Retourne l'ensemble des noms des joueurs assurés
	 * @return Set<String> contenant le nom des joueurs assurés
	 * */
	public Set<String> getNames();
	
	/**
	 * Sauvegarde les modifications sur disque pour un assuré précis
	 * @param playerName nom de l'assuré
	 * */
	public void save(String playerName);
	
	/**
	 * Forcer la sauvegarde sur disque si tout ce qui est en mémoire
	 * n'est pas sauvegarder à  chaque modification
	 * */
	public void saveAll();
}
