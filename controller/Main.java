package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import model.FourPictures;
import view.GameWindow;

public class Main {

	public static void main(String[] args)throws IOException {
		try
		{
			ArrayList<FourPictures>round = new ArrayList<FourPictures>();
			URL rounds = new URL("http://www.inf.kcl.ac.uk/staff/andrew/fourpictures/levels.php");
			BufferedReader in = new BufferedReader(new InputStreamReader(rounds.openStream()));

			String inputLine;
			int counter = 0;
			String[] info = new String[6];
			while ((inputLine = in.readLine()) != null){
				info[counter] = inputLine;
				++counter;
				if(counter == 6){
					counter = 0;
					String[] paths = {info[2], info[3], info[4], info[5]};
					FourPictures fp = new FourPictures(paths, info[0], info[1]);
					round.add(fp);
				}
			}
			in.close();

			GameWindow gw = new GameWindow(round);
			gw.setVisible(true);
		}
		catch(IOException ioe){
			JOptionPane.showMessageDialog(null, "Images not found", "ERROR", JOptionPane.OK_OPTION);
		}
	}

}
