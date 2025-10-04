import Views.MenuPrincipal;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        MenuPrincipal menuPrincipal = new MenuPrincipal(sc);
        menuPrincipal.afficherMenuPrincipal();
    }
}