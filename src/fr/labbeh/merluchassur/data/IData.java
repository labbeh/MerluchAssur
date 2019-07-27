/**
 * 
 */
package fr.labbeh.merluchassur.data;

import java.util.Collection;
import java.util.Set;

import fr.labbeh.merluchassur.Assure;

/**
 * @author labbeh
 * Cette interface devra �tre impl�ment�e par toute classe servant � lire ou �crire les donn�es
 * sur disque. En effet, il est aussi bien possible de sauvegarder les donn�es des joueurs
 * dans un fichier texte personnalis�, un fichier de s�rialisation, un fichier xml
 * ou encore par l'acc�s � une base SQL si le serveur en est �quip�.
 * L'interface permettra de rendre transparent le mode de gestion des donn�es sans que
 * le reste du plugin soit affect�
 */
public interface IData {
	/**
	 * M�thode lanc�e dans la classe principale dans le onEnable()
	 * pour pr�parer le terrain...
	 * */
	public void init();
	
	/**
	 * Permet d'ajouter un nouvel assur�
	 * @param assure instance de l'assur� � ajouter
	 * */
	public void addAssure(Assure assure);
	
	/**
	 * Permet de r�cup�r� un assur� � partir de son nom
	 * @param playerName nom du joueur assur�
	 * */
	public Assure getAssure(String playerName);
	
	/**
	 * Retourne une Collection de l'ensemble des Assures
	 * @return Collection de l'ensemble des assur�s
	 * */
	public Collection<Assure> getAssures();
	
	/**
	 * Retourne l'ensemble des noms des joueurs assur�s
	 * @return Set<String> contenant le nom des joueurs assur�s
	 * */
	public Set<String> getNames();
	
	/**
	 * Sauvegarde les modifications sur disque pour un assur� pr�cis
	 * @param playerName nom de l'assur�
	 * */
	public void save(String playerName);
	
	/**
	 * Forcer la sauvegarde sur disque si tout ce qui est en m�moire
	 * n'est pas sauvegarder �  chaque modification
	 * */
	public void saveAll();
}
