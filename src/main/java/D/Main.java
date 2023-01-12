package D;

public class Main {
    public static float CalculByOperator(char Operator, float A, float B){
        switch (Operator) {
            case '+':{
                return A + B;
            }
            case '-':{
                return A - B;
            }
            case '*':{
                return A * B;
            }
            case '/':{
                return A / B;
            }
            default:{
                return -1;
            }
        }
    }

    public static char[][] push(char[][] ACopie, int NumerElements){
        char[][] NewTab = new char[NumerElements + 1][];
        for (int index = 0 ; index < NumerElements; index++){
            NewTab[index] = pop(ACopie[index], ACopie[index].length);
        }
        return NewTab;
    }

    public static char[][] push(char[][] ACopie, char[] NewElement, int NumerElements){
        char[][] NewTab = new char[NumerElements + 1][];
        for (int index = 0 ; index < NumerElements; index++){
            NewTab[index] = pop(ACopie[index], ACopie[index].length);
        }
        NewTab[NumerElements] = NewElement;
        return NewTab;
    }

    public static char[] pop(char[] TableauACopie, int Taille){
        char[] TableauCopie = new char[Taille];
        for(int index = 0 ; index < Taille ; index++){
            TableauCopie[index] = TableauACopie[index];
        }
        return TableauCopie;
    }

    public static char[] pop(char[] TableauACopie, int x, int y){
        if(y == x) y++;
        char[] TableauCopie = new char[y - x];
        for(int index = x ; index < y ; index++){
            TableauCopie[index - x] = TableauACopie[index];
        }
        return TableauCopie;
    }

    public static char[][] pop(char[][] TableauACopie, int x, int y){
        if(y == x) y++;
        char[][] TableauCopie = new char[y - x][];
        for(int index = x ; index < y ; index++){
            TableauCopie[index - x] = pop(TableauACopie[index], TableauACopie[index].length);
        }
        return TableauCopie;
    }

    public static char[][] Extract(String Chaine){
        int NumberElements = 0;
        int StartPosition = 0;
        char[][] NewElements = new char[0][0];
        for (int index = 0; index < Chaine.length(); index++){
            if(Chaine.charAt(index) == 32){
                NewElements = push(NewElements,NumberElements);
                if(StartPosition == index - 1) {
                    NewElements[NumberElements] = pop(Chaine.toCharArray(),index - 1,index - 1);
                }
                else {
                    NewElements[NumberElements] = pop(Chaine.toCharArray(), StartPosition, index);
                }
                StartPosition = index + 1;
                NumberElements++;
            }
            else if(index == Chaine.length() - 1){
                NewElements = push(NewElements,NumberElements);
                NewElements[NumberElements] = pop(Chaine.toCharArray(), index, index);
            }
        }
        return NewElements;
    }

    //Retourne la pile après le calcul.
    public static char[][] CalculAndReplace(char[][] TabElements, int Operator){
        float ValeurA = Float.parseFloat(new String(TabElements[Operator - 1]));
        float ValeurB = Float.parseFloat(new String(TabElements[Operator - 2]));
        if(ValeurA == 0 && TabElements[Operator][0] == '/') {   // gestion de l'exception de division par 0
            return null;
        }
        else {
            // On calcule la valeur et on la stoque dans la pile originale.
            TabElements[Operator - 2] = Float.toString(CalculByOperator(TabElements[Operator][0], ValeurB, ValeurA)).toCharArray();
        }
        //On récupère la pile jusqu'à l'élément avant l'opérateur
        char[][] NewTab = pop(TabElements, 0, Operator - 1);
        //On push les éléments restants de la pile sauf ceux que l'on souhaite éliminer
        for (int index = Operator + 1 ; index < TabElements.length ; index++){
            NewTab = push(NewTab, TabElements[index], NewTab.length);
        }
        return NewTab;
    }

    //Retourne la valeur finale du calcul
    public static float DruideCalcul(String Calcul){
        char[][] TableElement = Extract(Calcul);
        int index = 0;
        do {
            // On vérifie que l'élement de la pile soit un opérateur (il doit être seul et être un opérateur
            if (TableElement[index].length == 1 && (CalculByOperator(TableElement[index][0], 1.00F, 1.00F) != -1)) {
                TableElement = CalculAndReplace(TableElement, index); //Null si exception ou la nouvelle pile après opération
                index = index - 2;
            }
            index++;
        } while (TableElement != null && TableElement.length != 1); //S'arrête si null pour gérer les
        if(TableElement != null) {
            return Float.parseFloat(new String(TableElement[0]));
        }
        return 0;
    }

    public static void main(String[] args) {
    }
}