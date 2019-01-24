package fr.labbeh.merluchassur.files;

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
		
		if(chest == null) return;
		
		// position du coffre
		double posX = chest.getLocation().getX();
		double posY = chest.getLocation().getY();
		double posZ = chest.getLocation().getZ();
		
		// nom du monde ou se situe le coffre
		String worldName = chest.getLocation().getWorld().getName();
		
		String toFile = FileUtilities.toString(worldName, playerName, nbMorts, posX, posY, posZ);
		
		try
		{
			FileWriter  fw = new FileWriter(MerluchAssur.DATAS_PATH +"/assureInfo"+ playerName +".dat");
			PrintWriter pw = new PrintWriter(fw);
			
			pw.println(toFile);
			
			pw.close();
			fw.close();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void readAssureStat()
	{
		// CONSTANTES
		final String FILES_NAME_BEGIN = "assureInfo"											   ;
		final int 	 END_INDEX		  = FILES_NAME_BEGIN.length()			   ;
		final File 	 DATAS_DIR 		  = new File(MerluchAssur.DATAS_PATH);
		
		// VARIABLES
		File[] dataFiles = DATAS_DIR.listFiles();
		String fileName;
		
		for(int cpt=0; cpt<dataFiles.length; cpt++)
		{
			fileName = dataFiles[cpt].getName();
			
			// trie des fichiers dans le dossier des datas du plugin au cas ou on aurait � y mettre d'autres types
			// de fichiers ou si l'administrateur du serveur met des fichiers dedans
			if(fileName.length() >= END_INDEX && fileName.substring(0, END_INDEX).equals(FILES_NAME_BEGIN))
			{
				try
				{
					Scanner  sc   = new Scanner(dataFiles[cpt]);
					String   	line = sc.nextLine();
					sc.close();
					
					ArrayList<String> infos	= new ArrayList<>();
					
					sc = new Scanner(line);
					sc.useDelimiter(";");
					
					while(sc.hasNext()) infos.add(sc.next());

					sc.close();
					
					/* LECTURE DES INFOS FICHIERS DE SAUVEGARDE */
					String worldName  = infos.get(0);
					String playerName = infos.get(1);
					
					int nbMorts = Integer.parseInt(infos.get(2));
					
					double posX = Double.parseDouble(infos.get(3));
					double posY = Double.parseDouble(infos.get(4));
					double posZ = Double.parseDouble(infos.get(5));
					
					this.ctrl.setAssure(worldName, playerName, nbMorts, posX, posY, posZ);
				}
				catch (FileNotFoundException e) {e.printStackTrace();}
			}
		}
	}
	
	private static String toString(String worldName, String playerName, int nbMorts, double posX, double posY, double posZ)
	{
		final String DELIM = ";";
		StringBuilder sb = new StringBuilder();
		
		sb.append(worldName);
		sb.append(DELIM);
		
		sb.append(playerName);
		sb.append(DELIM);
		
		sb.append(nbMorts);
		sb.append(DELIM);
		
		sb.append(posX);
		sb.append(DELIM);
		sb.append(posY);
		sb.append(DELIM);
		sb.append(posZ);
		
		return sb.toString();
	}
}
