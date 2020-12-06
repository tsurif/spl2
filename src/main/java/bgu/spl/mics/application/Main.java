package bgu.spl.mics.application;

import bgu.spl.mics.*;
import bgu.spl.mics.application.passiveObjects.Attack;
import bgu.spl.mics.application.passiveObjects.Diary;
import bgu.spl.mics.application.passiveObjects.Ewok;
import bgu.spl.mics.application.passiveObjects.Ewoks;
import bgu.spl.mics.application.services.*;
import com.google.gson.Gson;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

/** This is the Main class of the application. You should parse the input file,
 * create the different components of the application, and run the system.
 * In the end, you should output a JSON.
 */
public class Main {
	public static void main(String[] args) {
		Gson gson = new Gson();
		Input input=null;
		try (Reader reader = new FileReader(args[0]))
		{
			input = gson.fromJson(reader, Input.class);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		assert input != null;
		MicroService Leia=new LeiaMicroservice(input.getAttacks());
//        Attack[] att = new Attack[input.getAttacks().length];
//        for (int i = 0 ; i < att.length; i ++){
//            List<Integer> l = new LinkedList<Integer>();
//            att[i] = new Attack(l,200);
//        }
//        MicroService Leia2=new LeiaMicroservice(att, "leia2");
		MicroService C3PO=new C3POMicroservice();
		MicroService Han=new HanSoloMicroservice();
		MicroService R2D2=new R2D2Microservice(input.getR2D2());
		MicroService Lando=new LandoMicroservice(input.getLando());
		Ewoks.getInstance().initEwoks(input.getEwoks());

		Thread LeiaT=new Thread(Leia);
		//Thread LeiaT2 = new Thread(Leia2);
		Thread C3POT=new Thread(C3PO);
		Thread HanT=new Thread(Han);
		Thread R2D2T=new Thread(R2D2);
		Thread LandoT=new Thread(Lando);
		//MessageBusImpl.getInstance();

		C3POT.start();
		HanT.start();
		R2D2T.start();
		LeiaT.start();
		//LeiaT2.start();
		LandoT.start();

		try {
			C3POT.join();
			HanT.join();
			R2D2T.join();
			LeiaT.join();
			//LeiaT2.join();
			LandoT.join();
		}catch (InterruptedException ex){}

		Output output=new Output(Diary.getInstance());
		try (Writer writer = new FileWriter(args[1]))
		{
			gson.toJson(output,writer);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
