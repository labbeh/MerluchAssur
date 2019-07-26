package fr.labbeh.merluchassur.data;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import fr.labbeh.merluchassur.Assure;
import fr.labbeh.merluchassur.MerluchAssur;

public class TextFileData implements IData {
	/**
	 * HashMap d'assuré qui associe au nom du joueur son objet Assure qui lui est
	 * associé et charger à partir du fichier de sauvegarde
	 * */
	private Map<String, Assure> assures;
	
	/**
	 * Instance de FileUtilities pour la gestion de l'�criture et lecture dans les fichiers de configuration
	 * */
	private FileUtilities fu;

	public TextFileData(MerluchAssur ctrl) {
		this.assures = new HashMap<>();
		this.fu = new FileUtilities(ctrl);
	}
	
	/* METHODES IDATA */
	@Override
	public void init() {
		// pour le dossier qui contiendra les fichiers de configuration / serializations des assurances des joueurs
		if(this.prepareSerialization())
			System.out.println(MerluchAssur.PLUGIN_NAME +"Aucun dossier de fichiers de configuration, il vient d'être crééer");
		else
			System.out.println(MerluchAssur.PLUGIN_NAME +"dossier de fichiers de configuration détecté");
				
		fu.readAssureStat();

	}

	@Override
	public void addAssure(Assure assure) {
		this.assures.put(assure.getPlayerName(), assure);
		this.saveAll();

	}

	@Override
	public Assure getAssure(String playerName) {
		return assures.get(playerName);
	}
	
	@Override
	public Collection<Assure> getAssures(){
		return assures.values();
	}
	
	@Override
	public void save(String playerName) {
		fu.saveAssureStat(playerName);
	}

	@Override
	public void saveAll() {
		Collection<Assure> assures = this.assures.values();
		
		for(Assure assure: assures)
			this.save(assure.getPlayerName());
	}
	
	

	/* METHODES DE LA CLASSE */
	/**
	 * Crééer le dossier de sauvegarde des fichiers de données des assurés si il n'exsite pas
	 * @return vrai si le dossier n'exsite pas et qu'il vient d'être crééer
	 * */
	public boolean prepareSerialization() {
		File dataDirectory = new File(MerluchAssur.DATAS_PATH);
		return dataDirectory.mkdir();
	}
}
