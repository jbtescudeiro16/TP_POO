import java.io.IOException;
import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.Collections;
import java.util.Comparator;
import java.util.stream.Collectors;
public class Menus {

     private Scanner ler ;


    public Menus() {
        this.ler = new Scanner(System.in);
    }

    public void  menu() {

        System.out.println();
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.println("|###############################################################################################################################################|");
        System.out.println("|#################################################                   #############################################           ###################|");
        System.out.println("|#################################################  Trabalho de POO  ############################################# 2021-2022 ###################|");
        System.out.println("|#################################################                   #############################################           ###################|");
        System.out.println("|###############################################################################################################################################|");
        System.out.println("|###############################################################################################################################################|");
        System.out.println("|###############################################################################################################################################|");
        System.out.println("|###############################################################################################################################################|");
        System.out.println("|#####                                                   #######################################################################################|");
        System.out.println("|#####  TRABALHO ELABORADO POR :                         #######################################################################################|");
        System.out.println("|#####  Jo??o Bernardo Teixeira Escudeiro (a96075) MIEI   #######################################################################################|");
        System.out.println("|#####  Hugo dos Santos Martins (a95125) MIEI            #######################################################################################|");
        System.out.println("|#####  Diogo Rafael Rodrigues Aires (a91685) LCC        #######################################################################################|");
        System.out.println("|#####                                                   #######################################################################################|");
        System.out.println("|###############################################################################################################################################|");
        System.out.println("|###############################################################################################################################################|");
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------");
        Principal col = null;
        System.out.println();


        try {
            col = Principal.loadFile();
        }catch (IOException ext2){
            System.out.println("Erro a abrir o ficheiro local ");
        }catch (ClassNotFoundException exit){
            System.out.println("Erro a ler do ficheiro local");
        }

        if (col==null) {
            Parser par = new Parser();
            col=par.parse();
            System.out.println("Dados Carregados do ficheiro Fornecido pelo professor!");

        }
        else {
            System.out.println("Dados carregados do ficheiro local , j?? alterado!");
        }
        // System.out.println(col.toString());

        int aux = 0;

        while (aux != -1) {
            System.out.println();
            System.out.println();
            System.out.println("---------------------------------");
            System.out.println("|Insira o  que pretende realizar|");
            System.out.println("|1->Opera????es com aparelhos     |");
            System.out.println("|2->Opera????es com casas         |");
            System.out.println("|3->Opera????es com Fornecedores  |");
            System.out.println("|4->Opera????es de Simula????o      |");
            System.out.println("|5->Estat??ticas                 |");
            System.out.println("|-1-> Sair                      |");
            System.out.println("---------------------------------");
            aux = this.ler.nextInt();
            this.ler.nextLine();

            int optn = 0;
            if (aux == 1) {
                System.out.println("-------------------------------------------------------");
                System.out.println("|Insira a op??ao que pretende realizar                 |");
                System.out.println("|1->Criar um aparelho                                 |");
                System.out.println("|2->Remover um aparelho                               |");
                System.out.println("|3->Ligar /Desligar um aparelho                       |");
                System.out.println("|4->Mostrar lista de aparelhos de uma casa            |");
                System.out.println("|5->Ligar/Desligar todos os aparelhos de uma casa     |");
                System.out.println("|6->Ligar/Desligar todos os aparelhos de uma Divsis??o |");
                System.out.println("|7->Saber se um aparelho existe numa Divsis??o         |");
                System.out.println("|Qualquer outra tecla pra voltar ao menu inicial      |");
                System.out.println("-------------------------------------------------------");


                optn = this.ler.nextInt();
                this.ler.nextLine();

                switch (optn) {
                    case 1: {
                        System.out.println("----------------------------------------------");
                        System.out.println("|Insira qual ?? o aparelho que pretende criar |");
                        System.out.println("|1->SmartCamera                              |");
                        System.out.println("|2->SmartBulb                                |");
                        System.out.println("|3->SmartSpeaker                             |");
                        System.out.println("----------------------------------------------");
                        int one = this.ler.nextInt();
                        this.ler.nextLine();
                        createdevice(one, col);
                        break;
                    }
                    case 2: {
                        System.out.println("Insira o Nif do propriet??rio da casa  que pretende remover o aparelho");
                        System.out.println("Os propriet??rios que existem s??o : " + col.getHouses().keySet());
                        String n = this.ler.nextLine();
                        if (col.existsHouse(n)) {
                            System.out.println("OS aparelhos nesta casa s??o :" + col.getHouses().get(n).getDevices());
                            System.out.println("Insira o ID do aparelho");
                            int ind = this.ler.nextInt();
                            this.ler.nextLine();
                            try {
                                col.deleteDev(ind, n);
                                System.out.println("Aprelho removido");
                            } catch (NaoExisteDeviceException e) {
                                System.out.println(e.getMessage());
                            }


                        }
                        else System.out.println("Casa inv??lida");
                        break;
                    }
                    case 3: {
                        System.out.println("Insira o Nif do propriet??rio da casa  que pretende ligar/desligar o aparelho");
                        System.out.println("Os propriet??rios que existem s??o : " + col.getHouses().keySet());
                        String n = this.ler.nextLine();
                        if (col.existsHouse(n)) {
                            System.out.println("OS aparelhos nesta casa s??o :" + col.getHouses().get(n).getDevices());
                            System.out.println("Insira o ID do aparelho");
                            int ind = this.ler.nextInt();
                            this.ler.nextLine();

                            if (col.getHouses().get(n).existsDevice(ind)) {


                                System.out.println("Se pretender desligar o aparelho insira false");
                                System.out.println("Se pretender ligar o aparelho insira true");
                                boolean prp = this.ler.nextBoolean();
                                this.ler.nextLine();
                                col.setDev(ind, n, prp);
                                if (prp==true) System.out.println("Aparelho Ligado!");
                                else System.out.println("Aparelho desligado!");
                            }
                            else System.out.println("Aparelho inv??lido!");
                        } else System.out.println("Casa inv??lida");
                        break;
                    }
                    case 4: {
                        System.out.println("Insira o Nif do propriet??rio da casa  que pretende verificar a lista de aparelhos");
                        System.out.println("Os propriet??rios que existem s??o : " + col.getHouses().keySet());
                        String n = this.ler.nextLine();
                        if (col.existsHouse(n)) {
                            System.out.println("Os aparelhos nesta casa s??o :" + col.getHouses().get(n).getDevices());
                        }
                        else System.out.println("Casa inv??lida");
                        break;
                    }
                    case 5: {
                        System.out.println("Insira o Nif do propriet??rio da casa  que pretende ligar/desligar todos os aparelho");
                        System.out.println("Os propriet??rios que existem s??o : " + col.getHouses().keySet());
                        String n = this.ler.nextLine();
                        if (col.existsHouse(n)) {
                            System.out.println("Insira se pretende ligar ou desligar todos os aparelhos:");
                            System.out.println("Ligar : true");
                            System.out.println("Desligar : false");
                            boolean a = this.ler.nextBoolean();
                            if (a==true) {
                                    col.setallOn(n);
                                    System.out.println("Aparelhos Ligados");
                            }
                            else{
                                col.setallOff(n);

                                System.out.println("Aparelhos desligados!");
                            }
                        }
                        else System.out.println("Casa inv??lida");
                        break;
                    }
                    case 6: {
                        System.out.println("Insira o Nif do propriet??rio da casa  que pretende ligar/desligar todos os aparelhos de uma divis??o");
                        System.out.println("Os propriet??rios que existem s??o : " + col.getHouses().keySet());
                        String n = this.ler.nextLine();
                        if (col.existsHouse(n)) {
                            System.out.println("As divis??es que existem s??o : "+ col.getHouses().get(n).getRoom().keySet());
                            System.out.println("Insira a divis??o:");
                            String div=this.ler.nextLine();
                            if (col.getHouses().get(n).existsRoom(div)) {
                                System.out.println("Insira se prentende ligar ou desligar todos os aparelhos:");
                                System.out.println("Ligar : True");
                                System.out.println("Desligar : False");
                                boolean a = this.ler.nextBoolean();
                                this.ler.nextLine();
                                if (a == true) {
                                    //col.getHouses().get(n).setAllOndiv(div);
                                    col.setOnDiv(n,div);
                                    System.out.println("Aparelhos Ligados");
                                } else {
                                    col.setOffDiv(n,div);
                                    System.out.println("Aparelhos desligados!");
                                }
                            }
                            else System.out.println("Divis??o inv??lida");
                        }
                        else System.out.println("Casa inv??lida");
                        break;
                    }
                    case 7: {
                        System.out.println("Insira o Nif do propriet??rio da casa  que pretende saber ");
                        System.out.println("Os propriet??rios que existem s??o : " + col.getHouses().keySet());
                        String n = this.ler.nextLine();
                        if (col.existsHouse(n)) {
                            System.out.println("As divis??es que existem s??o : "+ col.getHouses().get(n).getRoom().keySet());
                            System.out.println("Insira a divis??o:");
                            String div=this.ler.nextLine();
                            if (col.getHouses().get(n).existsRoom(div)) {
                                System.out.println("Insira o id do aparelho que pretende saber se est?? em : " +div);
                                System.out.println("Insira o id do aparelhos");
                                int id=ler.nextInt();
                                System.out.println("O aparelho existe : "+ col.exDevROOM(n,id,div));
                            }
                            else System.out.println("Divis??o inv??lida");
                        } else System.out.println("Casa inv??lida");
                        break;
                    }

                    default:
                        break;
                }
            }

            if (aux == 2) {
                System.out.println("--------------------------------------------------");
                System.out.println("|Insira a op??ao que pretende realizar            |");
                System.out.println("|1->Criar uma casa                               |");
                System.out.println("|2->Remover uma casa                             |");
                System.out.println("|3->Imprimir a cole????o de casas                  |");
                System.out.println("|4->Criar uma nova divis??o numa casa             |");
                System.out.println("|5->Remover uma divis??o                          |");
                System.out.println("|6->Calcular o cosnsumo por dia de uma casa      |");
                System.out.println("|7->Imprimir a cole????o de propriet??rios          |");
                System.out.println("|8->Calcular o consumo num dado n??mero de dias   |");
                System.out.println("|9->Imprimir o n??mero total de casas             |");
                System.out.println("|Qualquer outra tecla pra voltar ao menu inicial |");
                System.out.println("--------------------------------------------------");


                optn = this.ler.nextInt();
                this.ler.nextLine();

                switch (optn) {
                    case 1: {
                        createHouse(col);
                        break;
                    }
                    case 2: {
                        deleteHouse(col);
                        break;
                    }
                    case 3: {
                        col.imprimeHouse();
                        break;
                    }
                    case 4: {
                            creatediv(col);
                        break;
                    }
                    case 5: {
                        removeDiv(col);
                        break;
                    }
                    case 6: {
                        calculate(col);
                        break;
                    }
                    case 7: {
                        System.out.println("Propriet??rios: "+(col.getHouses().keySet()));
                        break;
                    }
                    case 8: {
                        calculatebyDays(col);
                        break;
                    }
                    case 9:{
                        System.out.println( col.counthouses());
                        break;
                    }

                }
            }
            if (aux == 3) {
                System.out.println("--------------------------------------------------");
                System.out.println("|Insira a op??ao que pretende realizar            |");
                System.out.println("|1->Criar um Fonecedor                           |");
                System.out.println("|2->Alterar o fornecedor de uma casa             |");
                System.out.println("|3->Imprimir a lista de fornecedores             |");
                System.out.println("|4->Alterar  o pre??o da energia de um fornecedor |");
                System.out.println("|5->Alterar  o imposto de um fornecedor          |");
                System.out.println("|Qualquer outra tecla pra voltar ao menu inicial |");
                System.out.println("--------------------------------------------------");

                optn = this.ler.nextInt();
                this.ler.nextLine();
                switch (optn) {
                    case 1: {
                        System.out.println("Fornecedores j?? existentes:"+col.getForn().keySet());
                        System.out.println("Insira o nome do fornecedor ");
                        String forn2 = this.ler.nextLine();
                        if (col.existsForn(forn2)) System.out.println("J?? existe um fornecedor com esse nome");
                        else {
                            createForn(col,forn2);
                        }
                        break;
                    }
                    case 2: {
                        changeForn(col);
                        break;
                    }
                    case 3:
                    {
                        System.out.println("Fornecedores: "+col.getForn().toString());
                        break;
                    }
                    case 4 :
                    {
                        System.out.println("Fornecedores j?? existentes:"+col.getForn().keySet());
                        System.out.println("Insira o nome do fornecedor ");
                        String forn2 = this.ler.nextLine();
                        if (col.existsForn(forn2)) {


                            System.out.println("Insira o novo valor para o pre??o da energia :");
                            double auxili = this.ler.nextDouble();
                            this.ler.nextLine();
                            col.setEnPrice(forn2, auxili);
                            System.out.println("Pre??o alterado!");
                        }
                        else System.out.println("Fornecedor iv??lido");
                        break;
                    }
                    case 5: {
                        System.out.println("Fornecedores j?? existentes:" + col.getForn().keySet());
                        System.out.println("Insira o nome do fornecedor ");
                        String forn2 = this.ler.nextLine();
                        if (col.existsForn(forn2)) {
                            System.out.println("Insira o novo valor para o imposto");
                            double auxili = this.ler.nextDouble();
                            this.ler.nextLine();
                            col.setImp(forn2, auxili);
                            System.out.println("Imposto alterado!");
                        }
                        else System.out.println("Fornecedor iv??lido");
                        break;

                    }
                }
            }
            if (aux==4){
                System.out.println("--------------------------------------------------------------------------");
                System.out.println("|Insira a op??ao que pretende realizar                                    |");
                System.out.println("|1->Imprimir a data atual                                                |");
                System.out.println("|2->Avan??ar a data um n??mero de dias  gerando as respetivas faturas      |");
                System.out.println("--------------------------------------------------------------------------");
                optn = this.ler.nextInt();
                this.ler.nextLine();
                switch (optn) {
                    case 1: {
                        col.imprimedata();
                        break;
                    }
                    case 2: {
                        System.out.println("Insira o n??mero de dias que pretende avan??ar:");
                        int days = this.ler.nextInt();
                        this.ler.nextLine();

                        //advgenerate(col,days);
                        col.advgenerate(col,days);
                        break;
                    }
                }
            }
            if (aux==5) {
                System.out.println("-----------------------------------------------------------------");
                System.out.println("|Insira o n??mero correspondente ?? op????o que pretende realizar:  |");
                System.out.println("|1->Qual a casa que mais gastou naquele per??odo                 |");
                System.out.println("|2->Qual o comercializador com maior volume de factura????o       |");
                System.out.println("|3-> Listar as facturas emitidas por um comercializador         |");
                System.out.println("|4->Ordenar os maiores consumidores de energia num dado per??odoD |");
                System.out.println("|5->Qual ?? a casa com mais divis??es?                            |");
                System.out.println("|6->Qual ?? a casa com mais aparelhos?                           |");
                System.out.println("|7->Qual ?? o Fornecedor com mais casas associadas?              |");
                System.out.println("-----------------------------------------------------------------");
            int decide=this.ler.nextInt();
            this.ler.nextLine();
            switch(decide){
                case 1:{
                    calculatemostspend(col);
                    break;
                }
                case 2:{
                    fornecedormorerich(col);
                    break;
                }
                case 3:{
                    listinvoice(col);
                    break;
                }
                case 4:{
                    ordenate(col);
                    break;
                }
                case 5:{
                    col.imprimemore();
                    break;
                }
                case 6:{
                    col.imprimemoredevice();
                    break;
                }
                case 7:
                    col.imprimemoreforn();

                    break;
            }

            }
            }
    try {
        col.savefile();
        //col.savenumb();
        }
    catch (IOException exe)
    {
        System.out.println("Erro a guardas dados!");
        }
    }




    public  void creatediv(Principal col){
        System.out.println("Insira o nif do propriet??rio da casa a que pretende adicionar uma divis??o");
        System.out.println("Propiet??rios poss??veis: "+col.getHouses().keySet());
        String nif = this.ler.nextLine();
       // System.out.println(nif);
        if (col.existsHouse(nif)){
            System.out.println("Insira o nome da divis??o que quer criar");
            String div =this.ler.nextLine();
            if(col.existsRoom(div,nif)){
                System.out.println("J?? existe uma divis??o com esse nome");
            }
            else {
                col.createDiv(nif,div);
                System.out.println("Divis??o adicionada");
            }
        }
        else System.out.println("Casa inv??lida!");


    }

    public void removeDiv(Principal col) {

        System.out.println("Insira o nif do propriet??rio da casa que pretende remover uma divis??o");
        System.out.println("Propiet??rios poss??veis: "+col.getHouses().keySet());
        String nif = this.ler.nextLine();
        if (col.existsHouse(nif)){
            System.out.println("As divis??es que existem nesta casa s??o :"+col.getHouses().get(nif).getRoom().keySet());
            System.out.println("Insira o nome da divis??o que pretende remover");
            String div = this.ler.nextLine();

            if (col.existsRoom(div,nif)) {
                col.removediv(div, nif);
                System.out.println("Casa removida!");
            }
            else System.out.println("N??o existe essa divis??o!");

        }
        else System.out.println("Casa Inv??lida");
    }
        public  void createdevice( int numb, Principal col){
            if (numb == 1) {
                System.out.println("Indique se a SmartCamera est?? ligada, true or false");
                boolean on = this.ler.nextBoolean();
                this.ler.nextLine();
                System.out.println("Insira a resolu????o separada por qualquer um dos seguintes caracteres : 'x' , 'X' , '-'   {EXEMPLO: (1080X190) } ");
                String res = this.ler.nextLine();
                System.out.println("Insira o tamanho do ficheiro (em mb)");
                int size = this.ler.nextInt();
                this.ler.nextLine();
                System.out.println("Insira o Consumo di??rio em kwH");
                double consumpt = this.ler.nextDouble();
                this.ler.nextLine();


                SmartCamera a = new SmartCamera(on, res, size,consumpt);

                System.out.println("Pretende adicionar a uma divis??o ? ");
                System.out.println("true = sim , false = n??o ");
                boolean po = this.ler.nextBoolean();
                this.ler.nextLine();

                if (po == false) {
                    addtohouse(a, col);
                } else {
                    addtohousediv(a, col);
                }

            } else if (numb == 2) {
                System.out.println("Indique se a SmartBulb est?? ligada, true or false");
                boolean on = this.ler.nextBoolean();
                this.ler.nextLine();
                System.out.println("Insira a tonalidade :");
                System.out.println("0->COLD");
                System.out.println("1->NEUTRAL");
                System.out.println("2->WARM");
                int ton = this.ler.nextInt();
                this.ler.nextLine();
                System.out.println("Insira o tamanho em cm");
                int size = this.ler.nextInt();
                this.ler.nextLine();
                System.out.println("Insira o Consumo Di??rio em kwH");
                double consumpt = this.ler.nextDouble();
                this.ler.nextLine();

                SmartBulb a = new SmartBulb(on, ton, size,consumpt);
                System.out.println("Pretende adicionar a uma divis??o ? ");
                System.out.println("true =sim , false = n??o ");
                boolean aux = this.ler.nextBoolean();
                this.ler.nextLine();

                if (aux == false) {
                    addtohouse(a, col);
                } else {
                    addtohousediv(a, col);

                }


            } else if (numb == 3) {
                System.out.println("Indique se a SmartSpeaker est?? ligada, true or false");
                boolean on = this.ler.nextBoolean();
                this.ler.nextLine();
                System.out.println("Indique o volume: (MAX =100)");
                int volume = this.ler.nextInt();
                this.ler.nextLine();
                if (volume >100){
                    do {
                        System.out.println("Valor muito grande!Insira um novo valor");
                         volume = this.ler.nextInt();
                        this.ler.nextLine();
                    }while (volume>100);
                }
                System.out.println("Insira a marca da coluna");
                String brand = this.ler.nextLine();
                System.out.println("Insira o canal");
                String channel = this.ler.nextLine();

                System.out.println("Insira o consumo di??rio em kwH");
                double consumpt = this.ler.nextDouble();
                this.ler.nextLine();


                SmartSpeaker a = new SmartSpeaker(volume, brand, channel, on,consumpt);
                System.out.println("Pretende adicionar a uma divis??o ? ");
                System.out.println("true =sim , false = n??o ");
                boolean aux = this.ler.nextBoolean();
                this.ler.nextLine();

                if (aux == false) {
                    addtohouse(a, col);
                } else {
                    addtohousediv(a, col);

                }
            } else System.out.println("N??mero inv??lido");
        }


    public  void createHouse(Principal col){

        System.out.println("Insira o nif do propriet??rio:");
        String nif=this.ler.nextLine();
        if (col.existsHouse(nif)){
            System.out.println("Nif j?? utilizado");
        }

        else {
            System.out.println("Insira o nome do propriet??rio");
            String name=this.ler.nextLine();
            System.out.println("Insira o nome do fornecedor que pretende associar a esta casa: ");

            System.out.println("Fornecedores existentes: " + col.getForn().keySet());
            String forne =this.ler.nextLine();
            if (col.getForn().containsKey(forne)){
                House c = new House(name,nif);
                c.setForn_name(forne);
                try {
                    col.add_House(c.getNif(), c);
                    col.addFornP(forne,c);
                }
                catch (JaExisteCasaException e){
                    System.out.println(e.getMessage());
                }
                System.out.println("Opera????o concu??da!");
            }
            else {
                System.out.println("Fornecedor n??o existe !Ser?? criado um fornecedor com o mesmo nome");
                createForn(col,forne);
                House c = new House(name,nif);
                c.setForn_name(forne);
                try {
                    col.add_House(c.getNif(), c);
                    col.addFornP(forne,c);
                }
                catch (JaExisteCasaException e){
                    System.out.println(e.getMessage());
                }

                System.out.println("Opera????o concu??da!");
            }
        }
        }

    public  void deleteHouse(Principal col) {
        System.out.println("Insira o nif do propriet??rio:");
        System.out.println("Propriet??rios poss??veis"+col.getHouses().keySet());
        String nif=this.ler.nextLine();

        if (col.existsHouse(nif)){
            col.remHouseForn(col.getHouses().get(nif).getForn(),nif);
            col.remHouse(nif);
            System.out.println("Casa removida!");
        }
        else System.out.println("NIF inv??lido");
    }

    public  void changeForn(Principal col) {
        System.out.println("Insira o nif do proprietario da casa que pretende alterar o fornecedor");
        System.out.println("Propriet??rios poss??veis: "+col.getHouses().keySet());
        String nif=this.ler.nextLine();
        if (col.existsHouse(nif)){
            System.out.println("Insira o nome do fornecedor");
            System.out.println("Fornecedores existentes: "+col.getForn().keySet());
            String fo = this.ler.nextLine();
            if (col.existsForn(fo)) {
                String last_forn = col.getHouses().get(nif).getForn();
                col.setForn(fo,nif);
                col.addFornP(fo,col.getHouses().get(nif));
                col.remHouseForn(last_forn,nif);
                System.out.println("Fornecedor mudado!");

            }
            else {
                String last_forn = col.getHouses().get(nif).getForn();
                System.out.println("Fornecedor n??o existe !Ser?? criado um fornecedor com o mesmo nome ");
                createForn(col,fo);
                col.setForn(fo,nif);

                col.addFornP(fo,col.getHouses().get(nif));

                col.remHouseForn(last_forn,nif);
                System.out.println("Fornecedor mudado!");
            }
        }
        else System.out.println("Nif inv??lido");
    }




    public  void createForn(Principal collections,String fo){
        System.out.println("Insira o pre??o da energia (euros)");
        double energy = this.ler.nextDouble();
        this.ler.nextLine();
        System.out.println("Insira o valor do imposto (0-100) (percentagem)");
        double imposto=this.ler.nextDouble();
        this.ler.nextLine();

        Fornecedor a = new Fornecedor(fo,energy,imposto);
        collections.add_Forn(a.getName(),a);
        System.out.println("Fornecedor criado!");
        //System.out.println(collections.toString());

    }

    public  void addtohouse(SmartDevice a ,Principal col) {
        System.out.println("Insira o nif correspondente ao propriet??rio da casa :");
        System.out.println("Casas dispon??veis "+col.getHouses().keySet());
        String nif =this.ler.nextLine();
        if (col.existsHouse(nif)){
            col.adddevCasa(a,nif);
            System.out.println("Aparelho adicionado !");
        }
        else System.out.println("ERROR! N??o existe nenhuma casa com esse nif associado!");

    }

    public  void addtohousediv(SmartDevice a ,Principal col)
    {
        System.out.println("Insira o nif correspondente ao propriet??rio da casa :");
        System.out.println("Casas dispon??veis "+col.getHouses().keySet());
        String nif = this.ler.nextLine();
        if (col.getHouses().get(nif).getRoom().keySet().size()==0) System.out.println("N??o existem divis??es nesta casa");
        else  System.out.println("Divis??es  nesta casa"+ col.rooms(nif));
        System.out.println("--------------------------------------");
        System.out.println("Insira a divis??o :");

        String div = this.ler.nextLine();

        if (col.existsHouse(nif)) {
            if (col.existsRoom(div,nif)) {

                col.adddevCasaRoom(a,nif,div);
                System.out.println("Aparelho adicionado !");
            }
            else{
                System.out.println("Essa divis??o n??o existe !");
                System.out.println("Ir?? ser criada uma divis??o com o seguinte nome : "+ div);
                col.createDiv(nif,div);
                col.adddevCasaRoom(a,nif,div);

                System.out.println("Aparelho adicionado !");
            }

           // System.out.println(col.toString());
        }
        else System.out.println("ERROR! N??o existe nenhuma casa com esse nif associado!");
    }

    public void calculate(Principal col){
        System.out.println("Insira o nif correspondente ao propriet??rio da casa :");
        System.out.println("Propriet??rios poss??veis: "+col.getHouses().keySet());
        String nif = this.ler.nextLine();
        if (col.existsHouse(nif)) {
            System.out.println("Consumo di??rio total: "+col.getHouses().get(nif).calculatetotalspentDay());
            }
            else{
            System.out.println("Casa Inv??lida!");
            }
    }

    public void calculatebyDays(Principal col){
        System.out.println("Insira o nif correspondente ao propriet??rio da casa :");
        System.out.println("Propriet??rios poss??veis: "+col.getHouses().keySet());
        String nif = this.ler.nextLine();
        if (col.existsHouse(nif)) {
            System.out.println("Insira o n??mero de dias !");
            int days=this.ler.nextInt();
            this.ler.nextLine();

            System.out.println("Consumo di??rio nos "+days+ " dias : "+(col.getHouses().get(nif).calculatetotalspentDay()*days));
        }
        else{
            System.out.println("N??o existe essa casa");
        }
    }



    public void calculatemostspend(Principal col) {

        System.out.println("Insira o per??odo que pretende saber :");
        int dias = this.ler.nextInt();
        this.ler.nextLine();


        col.calculate(dias);


    }


    public void fornecedormorerich(Principal col)
    {
        String a = col.counttotalprofit();
        System.out.println("O fornecedor que mais fatura por dia ?? : "+ a);
        System.out.println("O lucro total foi : "+col.getForn().get(a).gettotalProfit());
    }

    public void listinvoice(Principal col){
        System.out.println("As empresas que existem s??o : " +col.getForn().keySet());
        System.out.println("Insira o nome do fornecedor");
        String fornecedor=this.ler.nextLine();

        col.imprimefaturas(fornecedor);

    }




    public void ordenate(Principal col)
    {
        List<House> imprimir = col.ordena();
        System.out.println("Insira o n??mero de casas que pretende saber!");
        int numero_impr=this.ler.nextInt();
        this.ler.nextLine();

        for (int i= imprimir.size()-1;i>=0 && numero_impr>0;i--, numero_impr--)
        {

            System.out.println("----------------------------------------");
            System.out.println("Nif do propriet??rio: " + imprimir.get(i).getNif());
            System.out.println("Nome do propriet??rio: " + imprimir.get(i).getOwner());
            System.out.println("Gasto: " + imprimir.get(i).calculatetotalspentDay());
            System.out.println("----------------------------------------");
        }
    }



}




