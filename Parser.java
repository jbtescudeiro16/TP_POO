import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Parser {


    public static Principal  parse() {
        Principal col = new Principal();
        List<String> linhas = lerFicheiro("Logs.txt");
        String[] linhaPartida;
        String divisao = null;
        House casaMaisRecente = null;
        for (String linha : linhas) {
            linhaPartida = linha.split(":", 2);
            switch (linhaPartida[0]) {
                case "Casa":
                    casaMaisRecente = parseCasa(linhaPartida[1]);
                    try {
                        col.add_House(casaMaisRecente.getNif(),casaMaisRecente);
                    }
                    catch (JaExisteCasaException e){
                        System.out.println(e.getMessage());
                    }

                    col.getForn().get(casaMaisRecente.getForn()).addHouse(casaMaisRecente.getNif(),casaMaisRecente);
                   col.addFornP(casaMaisRecente.getForn(),casaMaisRecente);
                    break;
                case "Divisao":
                    if (casaMaisRecente == null) System.out.println("Linha inválida.");
                    divisao = linhaPartida[1];
                    casaMaisRecente.addRoom(divisao);
                    break;
                case "SmartBulb":
                    if (divisao == null){

                        SmartBulb sd = parseSmartBulb(linhaPartida[1]);
                        col.adddevCasa(sd,casaMaisRecente.getNif());
                        //col.getHouses().get(casaMaisRecente.getNif()).addDevHouse(sd.getID(),sd);
                    }
                    else {
                        SmartBulb sd = parseSmartBulb(linhaPartida[1]);
                        col.adddevCasaRoom(sd,casaMaisRecente.getNif(),divisao);
                       // col.getHouses().get(casaMaisRecente.getNif()).addDevToRoom(sd.getID(),divisao,sd);
                         }
                    break;
                case "SmartSpeaker":
                    if (divisao == null){
                        SmartSpeaker sd = parseSmartSpeaker(linhaPartida[1]);
                        col.adddevCasa(sd,casaMaisRecente.getNif());
                       // col.getHouses().get(casaMaisRecente.getNif()).addDevHouse(sd.getID(),sd);
                    }
                    else {
                        SmartSpeaker sd = parseSmartSpeaker(linhaPartida[1]);
                       // col.getHouses().get(casaMaisRecente.getNif()).addDevToRoom(sd.getID(),divisao,sd);
                        col.adddevCasaRoom(sd,casaMaisRecente.getNif(),divisao);
                    }
                    break;
                case "SmartCamera":
                    if (divisao == null){
                        SmartCamera sd = parseSmartCamera(linhaPartida[1]);
                        col.adddevCasa(sd,casaMaisRecente.getNif());
                       // col.getHouses().get(casaMaisRecente.getNif()).addDevToRoom(sd.getID(),divisao,sd);
                    }
                    else {
                        SmartCamera sd = parseSmartCamera(linhaPartida[1]);
                      //  col.getHouses().get(casaMaisRecente.getNif()).addDevToRoom(sd.getID(),divisao,sd);
                        col.adddevCasaRoom(sd,casaMaisRecente.getNif(),divisao);
                    }
                    break;
                case "Fornecedor":
                    Fornecedor a = parseForn(linhaPartida[1]);
                    col.add_Forn(a.getName(),a);
                    break;

                default:
                    System.out.println("Linha inválida.");
                    break;
            }
        }
        System.out.println("done!");
        return col ;
    }

    public static List<String> lerFicheiro(String nomeFich) {
        List<String> lines;
        try {
            lines = Files.readAllLines(Paths.get(nomeFich), StandardCharsets.UTF_8);
        } catch (IOException exc) {
            lines = new ArrayList<>();
        }
        return lines;
    }

    public  static House parseCasa(String input) {
        String[] campos = input.split(",");
        String nome = campos[0];
        String nif = campos[1];
        String forn = campos[2];
        House a = new House(nome, nif);
        a.setForn_name(forn);
        return a;
    }

  public static SmartBulb  parseSmartBulb(String input){
      String[] campos = input.split(",");
      String tone =campos[0];
      int tonto=1;
      if (tone.equals("Warm")){
        tonto=2;
      }
      else if (tone.equals("Cold")){
    tonto=0;
      }
      else if (tone.equals("Neutral")){
    tonto=1;
      }
      int size = Integer.parseInt(campos[1]);
      double daily = Double.parseDouble(campos[2]);

      Random random = new Random();
      boolean b = random.nextBoolean();
      return new SmartBulb(b,tonto,size,daily);
  }


    public  static SmartSpeaker  parseSmartSpeaker(String input){
        String[] campos = input.split(",");
        int volume=Integer.parseInt(campos[0]);
        String channel = campos[1];
        String brand = campos[2];
        double daily=Double.parseDouble(campos[3]);

        Random random = new Random();
        boolean b = random.nextBoolean();
        return new SmartSpeaker(volume,brand,channel,b,daily);
    }

    public  static SmartCamera  parseSmartCamera(String input){
        String[] campos = input.split(",");

        String res = campos[0];
        int file=Integer.parseInt(campos[1]);
        double daily=Double.parseDouble(campos[2]);

        Random random = new Random();
        boolean b = random.nextBoolean();
        return new SmartCamera(b,res,file,daily);
    }

    public static Fornecedor parseForn(String input){
        String[] campos = input.split(",");
        return new Fornecedor(campos[0],2.0,23);
    }

}


