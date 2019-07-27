package fr.labbeh.merluchassur.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import org.bukkit.block.Chest;

import fr.labbeh.merluchassur.Assure;
import fr.labbeh.merluchassur.MerluchAssur;

/**
 * @author labbeh
 * @description gestion des lectures/écritures dans les fichiers de sauvegarde et de configuration du plugin
 * */

public class FileUtilities
{
	private MerluchAssur ctrl;
	//private TextFileData ctrl;
	
	public FileUtilities( MerluchAssur ctrl )
	{
		this.ctrl = ctrl;
	}
	
	public void saveAssureStat(String playerName)
	{
		Assure assure = this.ctrl.getAssure(playerName);
		if(assure == null) return;
		
		int nbMorts = assure.getNbMorts();
		Chest chest = assure.getChest();
		
		Double posX = null;
		Double posY = null;
		Double posZ = null;
		String worldName = null;
		
		if(chest != null) {
		
			// position du coffre
			posX = chest.getLocation().getX();
			posY = chest.getLocation().getY();
			posZ = chest.getLocation().getZ();
		
			// nom du monde ou se situe le coffre
			worldName = chest.getLocation().getWorld().getName();
		}
		
		String toFile = toString(worldName, playerName, nbMorts, posX, posY, posZ);
		
		try {
			FileWriter  fw = new FileWriter(TextFileData.DATAS_PATH +"/assureInfo"+ playerName +".dat");
			PrintWriter pw = new PrintWriter(fw);
			
			pw.println(toFile);
			
			pw.close();
			fw.close();
		}
		catch (IOException e) {e.printStackTrace();}
	}
	
	public void readAssureStat()
	{
		// CONSTANTES
		final String FILES_NAME_BEGIN = "assureInfo";
		final int 	 END_INDEX		  = FILES_NAME_BEGIN.length();
		final File 	 DATAS_DIR 		  = new File(TextFileData.DATAS_PATH);
		
		// VARIABLES
		File[] dataFiles = DATAS_DIR.listFiles();
		String fileName;
		
		for(int cpt=0; cpt<dataFiles.length; cpt++)
		{
			fileName = dataFiles[cpt].getName();
			
			// trie des fichiers dans le dossier des datas du plugin au cas ou on aurait ï¿½ y mettre d'autres types
			// de fichiers ou si l'administrateur du serveur met des fichiers dedans
			if(fileName.length() >= END_INDEX && fileName.substring(0, END_INDEX).equals(FILES_NAME_BEGIN)) {
				try {
					Scanner  sc   = new Scanner(dataFiles[cpt]);
					String   line = sc.nextLine();
					sc.close();
					
					ArrayList<String> infos	= new ArrayList<>();
					
					sc = new Scanner(line);
					sc.useDelimiter(";");
					
					while(sc.hasNext()) infos.add(sc.next());

					sc.close();
					
					/* LECTURE DES INFOS FICHIERS DE SAUVEGARDE */
					String worldName  = infos.get(0);
					String playerName = infos.get(1);
					
					if(!worldName.equals("null")) {
						int nbMorts = Integer.parseInt(infos.get(2));
					
						double posX = Double.parseDouble(infos.get(3));
						double posY = Double.parseDouble(infos.get(4));
						double posZ = Double.parseDouble(infos.get(5));
					
						ctrl.setAssure(worldName, playerName, nbMorts, posX, posY, posZ);
					}
					else ctrl.setAssure(playerName);
				}
				catch (FileNotFoundException e) {e.printStackTrace();}
			}
		}
	}
	
	private static String toString(String worldName, String playerName, int nbMorts, Double posX, Double posY, Double posZ)
	{
		final String DELIM = ";";
		StringBuilder sb = new StringBuilder();
		
		if(worldName != null) sb.append(worldName);
		else sb.append("null");
		sb.append(DELIM);
		
		sb.append(playerName);
		sb.append(DELIM);
		
		sb.append(nbMorts);
		sb.append(DELIM);
		
		if(posX != null) sb.append(posX);
		sb.append(DELIM);
		
		if(posY != null) sb.append(posY);
		sb.append(DELIM);
		
		if(posZ != null) sb.append(posZ);
		
		return sb.toString();
	}
}
